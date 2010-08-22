package nl.xebia.si.university.kitchen;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import nl.xebia.si.university.kitchen.domain.Ingredient;
import nl.xebia.si.university.kitchen.domain.Product;
import nl.xebia.si.university.kitchen.domain.Recipe;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.annotation.ReleaseStrategy;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageBuilder;
import org.springframework.integration.store.MessageGroup;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Jeroen van Erp
 */
public class MiseEnPlace implements org.springframework.integration.aggregator.ReleaseStrategy {

    private final ConcurrentMap<Recipe, RoomOnTable> table = new ConcurrentHashMap<Recipe, RoomOnTable>();

    @ServiceActivator
    public void makeRoomFor(Recipe recipe) {
        table.put(recipe, new RoomOnTable(recipe));
    }

    @Aggregator
    public Message<List<Product>> prepareMeal(List<Product> products) {
        for (Recipe r : table.keySet()) {
            if (r.isSatisfiedBy(products)) {
                table.remove(r);
                return MessageBuilder.withPayload(products).setHeader("recipe", r).build();
            }
        }
        throw new IllegalArgumentException("Could not find a recipe for the products: " + products);
    }

    @CorrelationStrategy
    public Recipe findRecipeMissingProduct(Product product) {
        for (Map.Entry<Recipe, RoomOnTable> recipeRoomOnTableEntry : table.entrySet()) {
            if (recipeRoomOnTableEntry.getValue().putOnTable(product)) {
                return recipeRoomOnTableEntry.getKey();
            }
        }
        throw new IllegalArgumentException("Could not find a recipe for product: " + product);
    }

    public boolean canRelease(MessageGroup group) {
        Recipe recipe = (Recipe) group.getGroupId();
        return table.get(recipe).missingIngredients().isEmpty();
    }

    private class RoomOnTable {
        private final Map<Ingredient, Boolean> ingredientsPresent = Maps.newHashMap();

        public RoomOnTable(Recipe recipe) {
            for (Ingredient i : recipe.getIngredients()) {
                ingredientsPresent.put(i, false);
            }
        }

        public Collection<Ingredient> missingIngredients() {
            return Collections2.filter(ingredientsPresent.keySet(), new Predicate<Ingredient>() {
                public boolean apply(Ingredient input) {
                    return !ingredientsPresent.get(input);
                }
            });
        }

        public boolean putOnTable(Product p) {
            for (Ingredient i : ingredientsPresent.keySet()) {
                if (!ingredientsPresent.get(i) && i.isSatisfiedBy(p)) {
                    ingredientsPresent.put(i, true);
                    return true;
                }
            }
            return false;
        }
    }
}

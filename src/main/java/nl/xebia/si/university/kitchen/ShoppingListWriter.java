package nl.xebia.si.university.kitchen;

import nl.xebia.si.university.kitchen.domain.Ingredient;
import nl.xebia.si.university.kitchen.domain.ShoppingList;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.annotation.ReleaseStrategy;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * I write out shoppinglists, whilst ensuring that on each shoppinglist only ingredients
 * of the same type are added, and we write at least 5 items on a single list.
 * 
 * @author Jeroen van Erp
 */
public class ShoppingListWriter {

    @Aggregator
    public ShoppingList sendShoppingList(List<Ingredient> ingredients) {
        ShoppingList list = new ShoppingList();
        for (Ingredient ingredient : ingredients) {
            list.addItem(ingredient);
        }

        return list;
    }

    @CorrelationStrategy
    public Ingredient.Type putOnCorrectList(Ingredient ingredient) {
        return ingredient.getType();
    }

    @ReleaseStrategy
    public boolean canGoToShop(List<Ingredient> ingredients) {
        return ingredients.size() > 5;
    }
}

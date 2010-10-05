package nl.xebia.si.university.kitchen;

import nl.xebia.si.university.kitchen.domain.Ingredient;
import nl.xebia.si.university.kitchen.domain.ShoppingList;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.store.MessageGroup;

import java.util.List;

/**
 * I write out shoppinglists, whilst ensuring that on each shoppinglist only ingredients
 * of the same type are added, and we write at least 5 items on a single list.
 * 
 * @author Jeroen van Erp
 */
public class ShoppingListWriter implements org.springframework.integration.aggregator.ReleaseStrategy {

    @Aggregator
    public ShoppingList sendShoppingList(List<Ingredient> ingredients) {
        ShoppingList list = new ShoppingList(ingredients.get(0).getType());
        for (Ingredient ingredient : ingredients) {
            list.addItem(ingredient);
        }

        return list;
    }

    @CorrelationStrategy
    public Ingredient.Type putOnCorrectList(Ingredient ingredient) {
        return ingredient.getType();
    }

	@Override
	public boolean canRelease(final MessageGroup group) {
		return group.isComplete();
	}
}

package nl.xebia.si.university.kitchen.shop;

import nl.xebia.si.university.kitchen.domain.GroceryBag;
import nl.xebia.si.university.kitchen.domain.Ingredient;
import nl.xebia.si.university.kitchen.domain.Meat;
import nl.xebia.si.university.kitchen.domain.ShoppingList;
import org.springframework.integration.annotation.Transformer;

import java.util.List;

/**
 */
public class Butcher {

	@Transformer
	public Meat sell(Ingredient ingredient) {
		return new Meat(ingredient.getName(), ingredient.getAmount());
	}

    @Transformer
    public GroceryBag<Meat> sell(ShoppingList shoppingList) {
        GroceryBag<Meat> groceryBag = new GroceryBag<Meat>();
        for (Ingredient ingredient : shoppingList.getItems()) {
            groceryBag.put(sell(ingredient));
        }

        return groceryBag;
    }
}

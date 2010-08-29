package nl.xebia.si.university.kitchen.domain;

import nl.xebia.si.university.kitchen.domain.Amount;
import nl.xebia.si.university.kitchen.domain.Ingredient;
import nl.xebia.si.university.kitchen.domain.Recipe;

/**
 */
public class RecipeObjectMother {

	public static Recipe friedEggRecipe() {
		Recipe recipe = new Recipe("fried egg");
		recipe.addIngredient(new Ingredient("egg", new Amount(1, Amount.Unit.PIECES), Ingredient.Type.Grocery));
		recipe.addIngredient(new Ingredient("butter", new Amount(20, Amount.Unit.GRAMS), Ingredient.Type.Grocery));
		return recipe;
	}

}

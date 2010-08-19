package nl.xebia.si.university.kitchen.domain;

import com.google.common.collect.Lists;

import java.util.List;

/**
 */
public class Meal {

	private List<MealIngredient> ingredients = Lists.newArrayList();

	private Recipe recipe;

	public Meal(final Recipe recipe) {
		this.recipe = recipe;
	}

	public void cook(MealIngredient ingredient) {
		ingredients.add(ingredient);
	}

	/**
	 * @return true when All Ingredients from the Recipe are in the Meal
	 */
	public boolean isDone() {
		return recipe.isSatisfiedBy(ingredients);
	}

	public Recipe getRecipe() {
		return recipe;
	}
}

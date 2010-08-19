package nl.xebia.si.university.kitchen.recipe.splitter;

import nl.xebia.si.university.kitchen.domain.Ingredient;
import nl.xebia.si.university.kitchen.domain.Recipe;
import org.springframework.integration.annotation.Splitter;

import java.util.List;

/**
 * I split a Recipe into its separate ingredients.
 */
public class RecipeSplitter {

	@Splitter
	public List<Ingredient> splitRecipe(Recipe recipe) {
		return recipe.getIngredients();
	}
}

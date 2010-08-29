package nl.xebia.si.university.kitchen;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import nl.xebia.si.university.kitchen.domain.Ingredient;
import nl.xebia.si.university.kitchen.domain.Product;
import nl.xebia.si.university.kitchen.domain.Recipe;

import java.util.Map;

/**
 */
public class IngredientGroup {
	private final Map<Ingredient, Boolean> ingredientsPresent = Maps.newHashMap();

	public IngredientGroup(final Recipe recipe) {
		for (Ingredient ingredient : recipe.getIngredients()) {
			ingredientsPresent.put(ingredient, false);
		}
	}

	public void placeProduct(Product p) {
		for (Ingredient ingredient : ingredientsPresent.keySet()) {
			if (ingredient.isSatisfiedBy(p)) {
				ingredientsPresent.put(ingredient, true);
			}
		}
	}

	public boolean isComplete() {
		return Collections2.filter(ingredientsPresent.values(), new Predicate<Boolean>() {
			public boolean apply(final Boolean input) {
				return !input;
			}
		}).isEmpty();
	}

	public boolean needsProduct(final Product p) {
		return Collections2.filter(ingredientsPresent.entrySet(), new Predicate<Map.Entry<Ingredient, Boolean>>() {
			public boolean apply(final Map.Entry<Ingredient, Boolean> input) {
				return !input.getValue() && input.getKey().isSatisfiedBy(p);
			}
		}).size() > 0;
	}
}

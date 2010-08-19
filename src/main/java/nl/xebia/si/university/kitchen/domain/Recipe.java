package nl.xebia.si.university.kitchen.domain;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.Collection;
import java.util.List;

/**
 * A Recipe consists of Ingredients and has a name.
 */
@XStreamAlias("recipe")
public class Recipe {

	@XStreamAsAttribute
	private String name;

	@XStreamImplicit
	@XStreamAlias("ingredients")
	private List<Ingredient> ingredients = Lists.newArrayList();

	public Recipe(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void addIngredient(Ingredient i) {
		ingredients.add(i);
	}

	public Boolean isSatisfiedBy(final List<Product> products) {
		Collection<Ingredient> unsatisfiedIngredients = Collections2.filter(ingredients, new Predicate<Ingredient>() {
			public boolean apply(Ingredient input) {
				for (Product mealIngredient : products) {
					if (input.isSatisfiedBy(mealIngredient)) {
						return false;
					}
				}
				return true;
			}
		});
		return unsatisfiedIngredients.isEmpty();
	}
}

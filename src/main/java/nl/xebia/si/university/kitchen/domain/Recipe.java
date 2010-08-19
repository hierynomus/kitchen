package nl.xebia.si.university.kitchen.domain;

import com.google.common.collect.Lists;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

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
}

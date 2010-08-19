package nl.xebia.si.university.kitchen.domain;

public class Grocery extends MealIngredient {

	public Grocery(final String name, final Amount amount) {
		super(name, amount);
	}

	@Override
	protected boolean hasType(Ingredient.Type type) {
		return type.equals(Ingredient.Type.Grocery);
	}
}

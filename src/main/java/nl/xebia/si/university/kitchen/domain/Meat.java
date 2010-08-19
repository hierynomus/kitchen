package nl.xebia.si.university.kitchen.domain;

public class Meat extends MealIngredient {

	public Meat(final String name, Amount amount) {
		super(name, amount);
	}

	@Override
	protected boolean hasType(Ingredient.Type type) {
		return type.equals(Ingredient.Type.Meat);
	}
}

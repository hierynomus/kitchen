package nl.xebia.si.university.kitchen.domain;

public class Vegetable extends Product {

	public Vegetable(final String name, final Amount amount) {
		super(name, amount);
	}

	@Override
	protected boolean hasType(Ingredient.Type type) {
		return type.equals(Ingredient.Type.Vegetable);
	}
}

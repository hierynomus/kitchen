package nl.xebia.si.university.kitchen.domain;

/**
 */
public abstract class MealIngredient {
	private final String name;
	private final Amount amount;

	public MealIngredient(final String name, final Amount amount) {
		this.name = name;
		this.amount = amount;
	}
}

package nl.xebia.si.university.kitchen;

import nl.xebia.si.university.kitchen.domain.Meal;
import nl.xebia.si.university.kitchen.domain.Recipe;

/**
 * I am in control of the kitchen.
 *
 * @author Iwein Fuld
 */
public class Cook {

	public Meal prepareMeal(Recipe recipe) {
		return new Meal(recipe);
	}

}

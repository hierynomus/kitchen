package nl.xebia.si.university.kitchen.shop;

import nl.xebia.si.university.kitchen.domain.Ingredient;
import nl.xebia.si.university.kitchen.domain.Vegetable;
import org.springframework.integration.annotation.Transformer;

/**
 */
public class GreenGrocer {

	@Transformer
	public Vegetable sell(Ingredient ingredient) {
		return new Vegetable(ingredient.getName(), ingredient.getAmount());
	}
}

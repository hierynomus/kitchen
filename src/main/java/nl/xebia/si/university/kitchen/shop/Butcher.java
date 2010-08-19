package nl.xebia.si.university.kitchen.shop;

import nl.xebia.si.university.kitchen.domain.Ingredient;
import nl.xebia.si.university.kitchen.domain.Meat;
import org.springframework.integration.annotation.Transformer;

/**
 */
public class Butcher {

	@Transformer
	public Meat sell(Ingredient ingredient) {
		return new Meat(ingredient.getName(), ingredient.getAmount());
	}
}

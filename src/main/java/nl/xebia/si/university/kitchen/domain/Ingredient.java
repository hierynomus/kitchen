package nl.xebia.si.university.kitchen.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import nl.xebia.si.university.kitchen.support.AmountConverter;

@XStreamAlias("ingredient")
public class Ingredient {

	public enum Type {
		Meat, Vegetable, Grocery
	}

	@XStreamAsAttribute
	private String name;

	@XStreamConverter(AmountConverter.class)
	private Amount amount;

	@XStreamAsAttribute
	private Type type;

	public Ingredient(final String name, final Amount amount, final Type type) {
		this.name = name;
		this.amount = amount;
		this.type = type;
	}

	public boolean isSatisfiedBy(Product product) {
		return product.satisfies(name, amount, type);
	}
}

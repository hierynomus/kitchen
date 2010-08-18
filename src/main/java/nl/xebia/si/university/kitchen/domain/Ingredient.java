package nl.xebia.si.university.kitchen.domain;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public abstract class Ingredient {

	@XStreamAsAttribute
	private String name;
	
	private Amount amount;
	
	public Ingredient(final String name) {
		this.name = name;
	}
}

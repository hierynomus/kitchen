package nl.xebia.si.university.kitchen.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("grocery")
public class Grocery extends Ingredient {

	public Grocery(final String name) {
		super(name);
	}
}

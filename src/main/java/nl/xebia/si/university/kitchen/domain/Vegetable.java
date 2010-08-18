package nl.xebia.si.university.kitchen.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("vegetable")
public class Vegetable extends Ingredient {

	public Vegetable(final String name) {
		super(name);
	}
}

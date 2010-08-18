package nl.xebia.si.university.kitchen.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("meat")
public class Meat extends Ingredient {

	public Meat(final String name) {
		super(name);
	}
}

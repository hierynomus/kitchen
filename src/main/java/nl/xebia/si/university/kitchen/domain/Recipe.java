package nl.xebia.si.university.kitchen.domain;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * A Recipe consists of Ingredients and has a name.
 */
public class Recipe {

	private String name;

	private Map<Ingredient, Amount> ingredients = Maps.newHashMap();
}

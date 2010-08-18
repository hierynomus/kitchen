package com.xebia.si.university.kitchen.domain;

import com.thoughtworks.xstream.XStream;
import nl.xebia.si.university.kitchen.domain.Grocery;
import nl.xebia.si.university.kitchen.domain.Meat;
import nl.xebia.si.university.kitchen.domain.Recipe;
import nl.xebia.si.university.kitchen.domain.Vegetable;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 */
public class DomainTest {
	private XStream xstream;

	@Before
	public void init() {
		xstream = new XStream();
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(new Class[]{Recipe.class, Grocery.class, Meat.class, Vegetable.class});
	}

	@Test
	public void shouldHydrateXmlFile() throws IOException {
		final Object o = xstream.fromXML(new ClassPathResource("pilav.xml").getInputStream());
		assertThat(o, instanceOf(Recipe.class));
		Recipe r = (Recipe) o;
		assertThat(r.getIngredients().size(), is(6));
		System.out.println(r.getIngredients());
	}

	@Test
	public void shouldMarshallDomain() {
		Recipe r = new Recipe("Nasi");
		final Meat i = new Meat("Beef");
		r.addIngredient(i);
		r.addIngredient(new Vegetable("Red Sweet Pepper"));

		System.out.println(xstream.toXML(r));
	}
}

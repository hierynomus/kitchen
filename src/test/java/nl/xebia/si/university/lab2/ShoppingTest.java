package nl.xebia.si.university.lab2;

import nl.xebia.si.university.kitchen.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.core.MessageBuilder;
import org.springframework.integration.core.MessageChannel;
import org.springframework.integration.core.PollableChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static nl.xebia.si.university.kitchen.domain.Amount.Unit.GRAMS;
import static nl.xebia.si.university.kitchen.domain.Amount.Unit.PIECES;
import static nl.xebia.si.university.kitchen.domain.Ingredient.Type.Grocery;
import static nl.xebia.si.university.kitchen.domain.Ingredient.Type.Meat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 */
@ContextConfiguration(locations = {"/home-dinner-flow.xml", "/TEST-shopping.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ShoppingTest {
	@Autowired
	private MessageChannel recipes;

	@Autowired
	private PollableChannel test;

	@Test
	public void shouldConvertRecipeIntoIngredients() {
		Recipe recipe = RecipeObjectMother.steak();
		recipes.send(MessageBuilder.withPayload(recipe).build());
		receiveAndCheckProductMessage(new Ingredient("steak", new Amount(1, PIECES), Meat));
		receiveAndCheckProductMessage(new Ingredient("pepper", new Amount(2, GRAMS), Grocery));
		receiveAndCheckProductMessage(new Ingredient("salt", new Amount(2, GRAMS), Grocery));
	}

	private void receiveAndCheckProductMessage(final Ingredient ingredient) {
		final Message<Product> message = (Message<Product>) test.receive();
		assertThat("Message was null", message, is(notNullValue()));
		assertThat(message.getHeaders().get("recipe"), is(instanceOf(Recipe.class)));
		assertThat(ingredient.isSatisfiedBy(message.getPayload()), is(true));
	}

	private void receiveAndCheckIngredientMessage(final Grocery product) {
	}

}

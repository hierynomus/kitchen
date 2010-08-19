package nl.xebia.si.university;

import nl.xebia.si.university.kitchen.RecipeObjectMother;
import nl.xebia.si.university.kitchen.domain.Amount;
import nl.xebia.si.university.kitchen.domain.Grocery;
import nl.xebia.si.university.kitchen.domain.Ingredient;
import nl.xebia.si.university.kitchen.domain.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.core.MessageBuilder;
import org.springframework.integration.core.MessageChannel;
import org.springframework.integration.core.PollableChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 */
@ContextConfiguration(locations = {"/home-dinner-flow.xml", "/TEST-recipeSplitter.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class RecipeSplitterTest {
	@Autowired
	MessageChannel recipes;

	@Autowired
	PollableChannel test;

	@Test
	public void shouldSplitRecipe() {
		Recipe recipe = RecipeObjectMother.friedEggRecipe();
		recipes.send(MessageBuilder.withPayload(recipe).build());
		receiveAndCheckIngredientMessage(new Grocery("egg", new Amount(1, Amount.Unit.PIECES)));
		receiveAndCheckIngredientMessage(new Grocery("butter", new Amount(20, Amount.Unit.GRAMS)));
	}

	private void receiveAndCheckIngredientMessage(final Grocery product) {
		final Message<Ingredient> message = (Message<Ingredient>) test.receive(2000);
		assertThat("Message was null", message, is(notNullValue()));
		assertThat(message.getHeaders().get("recipe"), is(instanceOf(Recipe.class)));
		assertThat(message.getPayload().isSatisfiedBy(product), is(true));
	}

}

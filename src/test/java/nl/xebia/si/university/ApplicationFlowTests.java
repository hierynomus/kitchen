package nl.xebia.si.university;

import nl.xebia.si.university.kitchen.RecipeObjectMother;
import nl.xebia.si.university.kitchen.domain.Amount;
import nl.xebia.si.university.kitchen.domain.Grocery;
import nl.xebia.si.university.kitchen.domain.Meal;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Iwein Fuld
 */

@ContextConfiguration(locations = "/home-dinner-flow.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationFlowTests {

	private final Recipe recipe = RecipeObjectMother.friedEggRecipe();

	@Autowired
	MessageChannel recipes;

	@Autowired
	MessageChannel products;

	@Autowired
	PollableChannel meals;

	@Test
	public void shouldLoadContext() {
		// if this test fails spring cannot load the context for this test
	}

	@Test
	public void productsShouldResultInDoneMealOutput() {
		//fake the splitting of the recipe
		products.send(eggProductMessage());
		products.send(butterProductMessage());

		Meal meal = (Meal) meals.receive(2000).getPayload();
		assertThat(meal.isDone(), is(true));
	}

	@Test
	public void groceryBagsShouldResultInDoneMealOutput() {
		//fake the splitting of the recipe
		products.send(eggProductMessage());
		products.send(butterProductMessage());

		Meal meal = (Meal) meals.receive(2000).getPayload();
		assertThat(meal.isDone(), is(true));
	}

	private Message<?> butterProductMessage() {
		return MessageBuilder
				.withPayload(new Grocery("butter", new Amount(20, Amount.Unit.GRAMS)))
				.setHeader("recipe", recipe)
				.build();
	}

	private Message<?> eggProductMessage() {
		return MessageBuilder
				.withPayload(new Grocery("egg", new Amount(1, Amount.Unit.PIECES)))
				.setHeader("recipe", recipe)
				.build();
	}

	//== True end to end methods ==//

	@Test
//	@Ignore //until we're done
	public void recipeMessageShouldResultInDoneMeal() {
		recipes.send(friedEggRecipeMessage());

		Message<?> message = meals.receive(2000);
		assertThat("No message received within timout", message, is(notNullValue()));

		Meal meal = (Meal) message.getPayload();
		assertThat(meal.isDone(), is(true));
	}

	private Message<?> friedEggRecipeMessage() {
		return MessageBuilder.withPayload(recipe).build();
	}

}

package nl.xebia.si.university.lab3;

import nl.xebia.si.university.kitchen.domain.Meal;
import nl.xebia.si.university.kitchen.domain.Recipe;
import nl.xebia.si.university.kitchen.domain.RecipeObjectMother;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.PollableChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 */
@ContextConfiguration(locations = "/home-dinner-flow.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class KitchenTest {
	@Autowired
	private MessageChannel recipes;

	@Autowired
	private PollableChannel meals;

	@Test
	public void shouldPrepareMealFromRecipe() {
		Recipe r = RecipeObjectMother.steak();
		recipes.send(MessageBuilder.withPayload(r).build());

		final Message<Meal> message = (Message<Meal>) meals.receive(2000);
		final Meal meal = message.getPayload();
		assertThat(meal.getRecipe(), is(r));
		assertThat(meal.isDone(), is(true));
	}
}

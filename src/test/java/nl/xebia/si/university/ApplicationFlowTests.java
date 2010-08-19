package nl.xebia.si.university;

import nl.xebia.si.university.kitchen.domain.Meal;
import nl.xebia.si.university.kitchen.domain.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.PollableChannel;
import org.springframework.integration.core.Message;
import org.springframework.integration.core.MessageChannel;
import org.springframework.integration.message.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Iwein Fuld
 */

@ContextConfiguration(locations="/home-dinner-flow.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationFlowTests {

	@Autowired
	MessageChannel recipes;

	@Autowired
	PollableChannel meals;

	@Test
	public void shouldLoadContext() {
		// if this test fails spring cannot load the context for this test
	}

	@Test
	public void recipeMessageShouldResultInMealOutput() {
		recipes.send(recipeMessage());

		//in the end a meal should come out
		Message<?> mealMessage = meals.receive();
		assertThat(mealMessage, is(notNullValue()));
		assertThat(mealMessage.getPayload(), is(Meal.class));
	}

	private Message<?> recipeMessage() {
		return MessageBuilder.withPayload(new Recipe("foo")).build();
	}

}

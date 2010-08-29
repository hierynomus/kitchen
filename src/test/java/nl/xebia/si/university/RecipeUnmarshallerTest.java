package nl.xebia.si.university;

import nl.xebia.si.university.kitchen.domain.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.Message;
import org.springframework.integration.core.MessageBuilder;
import org.springframework.integration.core.MessageChannel;
import org.springframework.integration.core.PollableChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author Iwein Fuld
 */
@ContextConfiguration(locations = "/TEST-recipe-unmarshaller.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class RecipeUnmarshallerTest {

	@Autowired
	MessageChannel recipeFiles;

	@Autowired
	PollableChannel recipes;

	@Test
	public void shouldSendUnmarshalledRecipe() throws IOException {
		recipeFiles.send(MessageBuilder.withPayload(new ClassPathResource("pilav.xml").getFile()).build());
		Message<?> recipeMessage = recipes.receive(10000);

		assertNotNull("Failed to receive recipe message within timeout, did it get lost along the way?", recipeMessage);
		assertThat(recipeMessage.getPayload(), is(Recipe.class));
	}

}

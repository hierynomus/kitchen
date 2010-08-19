package nl.xebia.si.university.kitchen;

import nl.xebia.si.university.kitchen.domain.Recipe;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Iwein Fuld
 */
public class CookTest {

	private final Cook cook = new Cook();

	@Test
	public void shouldPrepareMeal() throws Exception {
		Recipe recipe = new Recipe("grub");
		assertThat( cook.prepareMeal(recipe).getRecipe(), is(recipe));
	}
}

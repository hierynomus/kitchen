package nl.xebia.si.university.kitchen;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import nl.xebia.si.university.kitchen.domain.Meal;
import nl.xebia.si.university.kitchen.domain.Product;
import nl.xebia.si.university.kitchen.domain.Recipe;
import org.springframework.integration.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * I am in control of the kitchen.
 *
 * @author Iwein Fuld
 */
public class Cook {

	public Meal prepareMeal(List<Message<Product>> products) {
		Recipe recipe = (Recipe) products.get(0).getHeaders().get("recipe");
		Meal meal = new Meal(recipe);
		for (Message<Product> message : products) {
			meal.cook(message.getPayload());
		}
		return meal;
	}

	public Object correlatingRecipeFor(Message<Product> message) {
		return message.getHeaders().get("recipe");
	}

	public boolean canCookMeal(List<Message<?>> products) {
		Recipe recipe = (Recipe) products.get(0).getHeaders().get("recipe");
		return recipe.isSatisfiedBy(productsFromMessages(products));
	}

	private ArrayList<Product> productsFromMessages(List<Message<?>> group) {
		return new ArrayList<Product>(
				Collections2.transform(group, new Function<Message<?>, Product>() {
					public Product apply(Message<?> from) {
						return (Product) from.getPayload();
					}
				}));
	}
}

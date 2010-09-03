package nl.xebia.si.university.kitchen;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import nl.xebia.si.university.kitchen.domain.Meal;
import nl.xebia.si.university.kitchen.domain.Product;
import nl.xebia.si.university.kitchen.domain.Recipe;
import org.springframework.integration.Message;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.store.MessageGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * I am in control of the kitchen.
 *
 * @author Iwein Fuld
 */
public class Cook implements ReleaseStrategy {
	private final ConcurrentMap<Recipe, Meal> misEnPlace = new ConcurrentHashMap<Recipe, Meal>();

	@Aggregator
	public Meal prepareMeal(List<Message<Product>> products) {
		Recipe recipe = (Recipe) products.get(0).getHeaders().get("recipe");
		Meal meal = getMealForRecipe(recipe);
		for (Message<Product> message : products) {
			meal.cook(message.getPayload());
		}
		return meal;
	}

	@CorrelationStrategy
	public Object correlatingRecipeFor(Message<Product> message) {
		return message.getHeaders().get("recipe");
	}

	private Meal getMealForRecipe(Recipe recipe) {
		Meal meal = misEnPlace.get(recipe);
		if (meal == null) {
			misEnPlace.putIfAbsent(recipe, new Meal(recipe));
			meal = misEnPlace.get(recipe);
		}
		return meal;
	}

	public boolean canRelease(MessageGroup group) {
		Recipe recipe = (Recipe) group.getGroupId();
		return recipe.isSatisfiedBy(productsFromMessages(group));
	}

	private ArrayList<Product> productsFromMessages(MessageGroup group) {
		return new ArrayList<Product>(
				Collections2.transform(group.getUnmarked(), new Function<Message<?>, Product>() {
					public Product apply(Message<?> from) {
						return (Product) from.getPayload();
					}
				}));
	}
}

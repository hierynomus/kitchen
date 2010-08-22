package nl.xebia.si.university.kitchen;

import nl.xebia.si.university.kitchen.domain.Meal;
import nl.xebia.si.university.kitchen.domain.Product;
import nl.xebia.si.university.kitchen.domain.Recipe;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.Payload;
import org.springframework.integration.annotation.Transformer;

import java.util.List;

/**
 * @author Jeroen van Erp
 */
public class Chef {

    @Transformer
    public Meal createMeal(@Header Recipe recipe, List<Product> products) {
        Meal meal = new Meal(recipe);
        for (Product product : products) {
            meal.cook(product);
        }
        return meal;
    }
}

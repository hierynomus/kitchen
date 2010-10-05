package nl.xebia.si.university.kitchen.domain;

import com.google.common.collect.Lists;

import java.util.List;

/**
 */
public class ShoppingList {

	private List<Ingredient> items = Lists.newArrayList();

    public void addItem(Ingredient ingredient) {
        items.add(ingredient);
    }

    public List<Ingredient> getItems() {
        return items;
    }
	@Override
	public String toString() {
		return "ShoppingList[" + type + "] with ingredients [" + items + "]";
	}
}

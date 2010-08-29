package nl.xebia.si.university.kitchen.domain;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 */
public class GroceryBag<T extends Product> {

	private Set<T> items = Sets.newHashSet();

	public void put(T item) {
        items.add(item);
    }

    public Set<T> unpack() {
        return items;
    }
}

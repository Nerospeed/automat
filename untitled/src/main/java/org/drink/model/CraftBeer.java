package org.drink.model;

import org.drink.interfaces.IDrink;

import java.util.List;

/**
 * @author Dennis Martens
 */
public class CraftBeer extends Drink implements IDrink {
    public CraftBeer(List<Integer> changeList, String message) {
        super(changeList, message);
    }
}

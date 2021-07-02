package org.drink.model;

import org.drink.interfaces.IDrink;

import java.util.List;

/**
 * @author Dennis Martens
 */
public class Water extends Drink implements IDrink {
    public Water(List<Integer> changeList, String message) {
        super(changeList, message);
    }
}

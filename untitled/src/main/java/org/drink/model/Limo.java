package org.drink.model;

import org.drink.interfaces.IDrink;

import java.util.List;

/**
 * @author Dennis Martens
 */
public class Limo extends Drink implements IDrink {
    public Limo(List<Integer> changeList, String message) {
        super(changeList, message);
    }
}

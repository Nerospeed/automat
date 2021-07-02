package org.drink.slot;

import org.drink.interfaces.IDrink;


public interface ISlotLogic {

    public boolean isDrinkAvailable(String drink);

    public void addDrink(IDrink.Drinks drink, int count);
}

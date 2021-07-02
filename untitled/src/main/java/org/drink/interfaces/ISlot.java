package org.drink.interfaces;

import org.drink.model.Coin;

/**
 * @author Dennis Martens
 */
public interface ISlot {
    public IDrink getDrink(IDrink.Drinks drink, int coins);

    public void addCoin(Coin coin);

    public void removeCoin(Coin coin, int removeCoinCount);

    public void addDrink(IDrink.Drinks drink, int count);
}

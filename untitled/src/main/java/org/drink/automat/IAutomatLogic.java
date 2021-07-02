package org.drink.automat;

import org.drink.exception.CoinException;
import org.drink.exception.SlotException;
import org.drink.interfaces.IDrink;

public interface IAutomatLogic {
    public enum Drinks {
        CRAFTBEER(300),
        LIMO(120),
        WATER(80);

        private int value;

        Drinks(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    public IDrink getDrink(IDrink.Drinks drink, int coins) throws CoinException, SlotException;
}

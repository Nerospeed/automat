package org.drink.model;

import org.drink.coin.ICoinLogic;

/**
 * @author Dennis Martens
 */
public class Coin {
    private int stock = 0;
    private int valueInCent = 0;

    public Coin(int stock, int valueInCent) {
        this.stock = stock;
        this.valueInCent = valueInCent;
    }

    public int getStock() {
        return stock;
    }

    public int getValueInCent() {
        return valueInCent;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setValueInCent(int valueInCent) {
        this.valueInCent = valueInCent;
    }
}

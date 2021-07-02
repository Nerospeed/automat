package org.drink.interfaces;

import java.util.List;

/**
 * @author Dennis Martens
 */
public interface IDrink {

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

    public List<Integer> getChangeList();
    public boolean isDrinkBought();
    public int getPrice();
    public void setPrice(int price);
    public void setDrinkBought(boolean drinkBought);
    public void setChangeList(List<Integer> changeList);
}

package org.drink.model;

import org.drink.interfaces.IDrink;

import java.util.List;

/**
 * @author Dennis Martens
 */
public class Drink implements IDrink {
    private List<Integer> changeList;
    private boolean isDrinkBought;
    private int price;

    public Drink(List<Integer> changeList, boolean isDrinkBought) {
        this.changeList = changeList;
        this.isDrinkBought = isDrinkBought;
    }

    @Override
    public List<Integer> getChangeList() {
        return this.changeList;
    }

    @Override
    public boolean isDrinkBought() {
        return isDrinkBought;
    }

    @Override
    public void setChangeList(List<Integer> changeList) {
        this.changeList = changeList;
    }

    @Override
    public void setDrinkBought(boolean drinkBought) {
        isDrinkBought = drinkBought;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        if (!changeList.isEmpty()) {
            int a = changeList.stream().mapToInt(Integer::intValue).sum();
            return String.format("Change Sum is: %s", a);
        }
        return "";
    }
}

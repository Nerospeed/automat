package org.drink.model;

import org.drink.interfaces.IDrink;

import java.util.List;

/**
 * @author Dennis Martens
 */
public class Drink implements IDrink {
    private List<Integer> changeList;
    private String message;

    public Drink(List<Integer> changeList, String message) {
        this.changeList = changeList;
        this.message = message;
    }

    public void setChangeList(List<Integer> changeList) {
        this.changeList = changeList;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public List<Integer> getChangeList() {
        return this.changeList;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        if (!changeList.isEmpty()) {
            int a = changeList.stream().mapToInt(Integer:: intValue).sum();
            return String.format("Change Sum is: %s", a);
        }
        return "";
    }
}

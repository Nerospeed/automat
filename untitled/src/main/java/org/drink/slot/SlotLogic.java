package org.drink.slot;

import org.drink.interfaces.IDrink;

import java.util.HashMap;
import java.util.Map;

public class SlotLogic implements ISlotLogic{
    private Map<String, Integer> drinkMap = new HashMap<>();
    public Map<String, Integer> getDrinkMap() {
        return drinkMap;
    }

    public SlotLogic(){
        initArraysForSlots();
    }

    @Override
    public boolean isDrinkAvailable(String drink) {
        var selectedSlot = drinkMap.get(drink);
        if (selectedSlot > 0) return true;
            return false;
    }

    /**
     * Add new drink(s) to the Map. The drinks will be totalities.
     *
     * @param drink selected drink
     * @param count selected count
     */
    @Override
    public void addDrink(IDrink.Drinks drink, int count) {
        drinkMap.put(drink.name(), drinkMap.get(drink.name()) + count);
    }

    /**
     * Decrement the drink from the Map
     *
     * @param drink selected drink
     */
    public void decrementStockOfDrink(IDrink.Drinks drink) {
        drinkMap.put(drink.name(), drinkMap.get(drink.name()) - 1);
    }

    /**
     * Check if the drink available
     *
     * @param drinks selected drink
     * @return true or false
     */
    private boolean isDrinkAvailable(IDrink.Drinks drinks) {
        if (drinkMap.get(drinks.name()) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method init a Map with drinks from the enum Drinks with a count of 10 items per slot
     */
    private void initArraysForSlots() {
        for (IDrink.Drinks drink : IDrink.Drinks.values()) {
            drinkMap.put(drink.name(), 10);
        }
    }
}

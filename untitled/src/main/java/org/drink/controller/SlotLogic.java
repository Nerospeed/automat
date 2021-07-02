package org.drink.controller;

import org.drink.interfaces.ICoin;
import org.drink.interfaces.IDrink;
import org.drink.interfaces.ISlot;
import org.drink.model.*;

import java.util.*;

/**
 * @author Dennis Martens
 */
public class SlotLogic implements ISlot {

    private Map<String, Integer> drinkMap = new HashMap<>();
    private Map<Integer, Coin> coinObjMap = new HashMap<>();
    private static int countOfDrinkItems = 10;

    public SlotLogic() {
        initArraysForDrinks();
        initArraysForCoins();
    }

    public Map<String, Integer> getDrinkMap() {
        return drinkMap;
    }

    public Map<Integer, Coin> getCoinObjMap() {
        return coinObjMap;
    }

    /**
     * The method get a drink back, based of the Interface {@link IDrink} and the Enum {@link IDrink.Drinks}
     *
     * @param drink Enum of the drink, see {@link IDrink.Drinks}
     * @param coins The sum of given coins
     * @return Based of the selection from the Interface {@link IDrink} and the Enum {@link IDrink.Drinks}
     * will be return this method a object representation from {@link IDrink}
     */
    @Override
    public IDrink getDrink(IDrink.Drinks drink, int coins) {
        AutomatSlotLogic automatSlotLogic = null;
        switch (drink) {
            case LIMO:
                automatSlotLogic = automatSlotLogic(drink, coins);
                return new Limo(automatSlotLogic.getChangeList(),automatSlotLogic.getMessage());
            case WATER:
                automatSlotLogic = automatSlotLogic(drink, coins);
                return new Water(automatSlotLogic.getChangeList(),automatSlotLogic.getMessage());
            case CRAFTBEER:
                automatSlotLogic = automatSlotLogic(drink, coins);
                return new CraftBeer(automatSlotLogic.getChangeList(),automatSlotLogic.getMessage());
            default:
                return null;
        }
    }

    /**
     * Add a coin to the Map
     *
     * @param coin Kind of coin to add this into the Map
     */
    @Override
    public void addCoin(Coin coin) {
        addCoinInSlot(coin.getValueInCent(), coin.getStock());
    }

    /**
     * This method removed the number of coins from the Map
     *
     * @param coin            which will be removed
     * @param removeCoinCount number of removed coins
     */
    @Override
    public void removeCoin(Coin coin, int removeCoinCount) {
        removeCoinInSlot(coin.getValueInCent(), removeCoinCount);
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
    private void decrementStockOfDrink(IDrink.Drinks drink) {
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
     * This method starts the coin change and checked the units of the used slot.
     *
     * @param drink selected drink
     * @param coins inserted coins
     * @return A list of the change coins
     */
    private AutomatSlotLogic automatSlotLogic(IDrink.Drinks drink, int coins) {
        if (isCoinSumHigherAsItemCost(drink.getValue(), coins)) {
            if (isDrinkAvailable(drink)) {
                List<Integer> changeList = returnChange(drink.getValue(), coins);
                if (changeList.isEmpty()) {
                    return getAutomatSlotLogic("No change coins available");
                } else if (changeList.size() >= 1) {
                    decrementStockOfDrink(drink);
                    return getAutomatSlotLogic(String.format("Have fun with your %s", drink.name()),changeList);
                }
            } else {
                return getAutomatSlotLogic(String.format("Sorry, the Slot for %s is empty ", drink.name()));
            }
        } else {
            return getAutomatSlotLogic(String.format("Sorry, to get %1$s, please insert %2$.2f€ more",drink.name(),((drink.getValue() - coins) / 100.0)));
        }
        return new AutomatSlotLogic();
    }

    /**
     * Get the {@link AutomatSlotLogic} object with a message
     *
     * @param s String for the message
     * @return {@link AutomatSlotLogic}
     */
    private AutomatSlotLogic getAutomatSlotLogic(String s){
        AutomatSlotLogic automatSlotLogic = new AutomatSlotLogic();
        automatSlotLogic.setMessage(s);
        return automatSlotLogic;
    }

    /**
     * Get the {@link AutomatSlotLogic} object with a message and the changeList
     *
     * @param s String for the message
     * @param changeList changeList as {@link List<Integer>}
     * @return {@link AutomatSlotLogic}
     */
    private AutomatSlotLogic getAutomatSlotLogic(String s, List<Integer> changeList) {
        AutomatSlotLogic automatSlotLogic = new AutomatSlotLogic();
        automatSlotLogic.setChangeList(changeList);
        automatSlotLogic.setMessage(s);
        return automatSlotLogic;
    }

    /**
     * This method init a Map with drinks from the enum Drinks with a count of 10 items per slot
     */
    private void initArraysForDrinks() {
        for (IDrink.Drinks drink : IDrink.Drinks.values()) {
            drinkMap.put(drink.name(), countOfDrinkItems);
        }
    }

    /**
     * This method inti a Map and the numbers of stock for each coin slot
     */
    private void initArraysForCoins() {
        var max = 10;
        var min = 1;
        var i = 0;
        for (ICoin.Coins coin : ICoin.Coins.values()) {
            int rand = new Random().nextInt(max);
            coinObjMap.put(i, new Coin(rand, coin.getValue()));
            i++;
        }
    }

    /**
     * This method include the logic to calculate the change coins
     *
     * @param itemCost
     * @param coins
     * @return A list of the change coins
     */
    private List<Integer> returnChange(int itemCost, int coins) {

        var getSelectedCoin = 0;
        var calculateDifference = coins - itemCost;
        List<Integer> changeListArray = new ArrayList<>();

        if (calculateDifference == 0) {
            changeListArray.add(calculateDifference);
        } else {
            do {
                Coin coin = getPossibleCoinSlot(calculateDifference);

                if (coin == null) {
                    return rollbackCoins(changeListArray);
                } else {
                    getSelectedCoin = coin.getValueInCent();
                    changeListArray.add(getSelectedCoin);
                    calculateDifference = calculateDifference - getSelectedCoin;
                }
            } while (calculateDifference > 0);
        }
        return changeListArray;
    }

    /**
     * This method handle the rollback. When a slot has not enough coins for the change, the process will be rollback.
     *
     * @param changeListArray
     * @return A new and empty List
     */
    private List<Integer> rollbackCoins(List<Integer> changeListArray) {
        for (int item : changeListArray) {
            addCoinInSlot(item, 1);
        }
        return new ArrayList<>();
    }

    /**
     * @param item
     * @param count
     */
    private void addCoinInSlot(int item, int count) {
        for (Coin coinItem : coinObjMap.values()) {
            if (coinItem.getValueInCent() == item) {
                coinItem.setStock(coinItem.getStock() + count);
            }
        }
    }

    /**
     * Method to remove the coin from the Map
     *
     * @param item
     * @param removeCoinCount
     */
    private void removeCoinInSlot(int item, int removeCoinCount) {
        for (Coin coinItem : coinObjMap.values()) {
            if (coinItem.getValueInCent() == item) {
                if (coinItem.getStock() - removeCoinCount < 0) {
                    coinItem.setStock(0);
                } else {
                    coinItem.setStock(coinItem.getStock() - removeCoinCount);
                }
            }
        }
    }

    /**
     * This method get the possible slot of coins, based on the {@param restCoins} parameter.
     * When the count of a slot is empty, the algorithm choose the next lower slot of coin.
     * Is the list farther empty, this method return null.
     *
     * @param restCoins
     * @return a possible coin slot in form from a @Coin object.
     */
    private Coin getPossibleCoinSlot(int restCoins) {

        var i = 0;
        var y = 1;
        for (Coin coin : coinObjMap.values()) {
            if (coin.getValueInCent() <= restCoins) {
                i++;
                continue;
            } else {
                if (restCoins != coin.getValueInCent() && restCoins <= coin.getValueInCent()) {

                    if (i - y < 0) return null;

                    if (coinObjMap.get(i - y).getStock() > 0) {
                        return decrementStockOfCoin(coinObjMap.get(i - y));
                    } else {
                        y++;
                        if (i - y < 0) return null;

                        if (coinObjMap.get(i - y).getStock() > 0) {
                            return decrementStockOfCoin(coinObjMap.get(i - y));
                        } else {
                            continue;
                        }
                    }
                }
                if (coin.getStock() > 0) {
                    coin.setStock(coin.getStock() - 1);
                    return coin;
                } else {
                    return decrementStockOfCoin(coinObjMap.get(i - 1));
                }
            }
        }
        return null;
    }

    /**
     * Decrement the stock of coin
     *
     * @param coin
     * @return coin
     */
    private Coin decrementStockOfCoin(Coin coin) {
        coin.setStock(coin.getStock() - 1);
        return coin;
    }

    /**
     * This method checks whether the given coins are enough to cover the costs from the drink.
     *
     * @param itemCost costs for a drink
     * @param coins    given coins
     * @return true or false
     */
    private boolean isCoinSumHigherAsItemCost(int itemCost, int coins) {
        int calculateDifference = coins - itemCost;
        return (calculateDifference < 0) ? false : true;
    }
}

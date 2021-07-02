package org.drink.coin;

import org.drink.model.Coin;

import java.util.*;

public class CoinLogic implements ICoinLogic {
    private Map<Integer, Coin> coinObjMap = new HashMap<>();

    public Map<Integer, Coin> getCoinObjMap() {
        return coinObjMap;
    }

    public CoinLogic() {
        initArraysForCoins();
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
    public void removeCoin(Coin coin) {
        removeCoinInSlot(coin.getValueInCent(), coin.getStock());
    }

    /**
     * This method include the logic to calculate the change coins
     *
     * @param itemCost
     * @param coins
     * @return A list of the change coins
     */
    public List<Integer> returnChange(int itemCost, int coins) {
        CoinLogic coinLogic = new CoinLogic();
        var getSelectedCoin = 0;
        var calculateDifference = coins - itemCost;
        List<Integer> changeListArray = new ArrayList<>();

        if (calculateDifference == 0) {
            changeListArray.add(calculateDifference);
        } else {
            do {
                Coin coin = coinLogic.getPossibleCoinSlot(calculateDifference);

                if (coin == null) {
                    return coinLogic.rollbackCoins((changeListArray));
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
     * Set the selected slot empty
     *
     * @param coin
     */
    @Override
    public void setCoinSlotEmpty(Coin coin) {
        for (Coin coinItem : coinObjMap.values()) {
            if (coinItem.getValueInCent() == coin.getValueInCent()) {
                coinItem.setStock(0);
            }
        }
    }

    /**
     * This method inti a Map and the numbers of stock for each coin slot
     */
    private void initArraysForCoins() {
        var max = 10;
        var min = 1;
        var i = 0;
        for (ICoinLogic.Coins coin : ICoinLogic.Coins.values()) {
            int rand = new Random().nextInt(max);
            coinObjMap.put(i, new Coin(rand, coin.getValue()));
            i++;
        }
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
    public Coin getPossibleCoinSlot(int restCoins) {

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
     * This method handle the rollback. When a slot has not enough coins for the change, the process will be rollback.
     *
     * @param changeListArray
     * @return A new and empty List
     */
    public List<Integer> rollbackCoins(List<Integer> changeListArray) {
        for (int item : changeListArray) {
            addCoinInSlot(item, 1);
        }
        return new ArrayList<>();
    }
}

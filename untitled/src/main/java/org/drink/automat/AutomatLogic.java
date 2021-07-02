package org.drink.automat;

import org.drink.coin.CoinLogic;
import org.drink.coin.ICoinLogic;
import org.drink.exception.CoinException;
import org.drink.exception.SlotException;
import org.drink.interfaces.IDrink;
import org.drink.model.*;
import org.drink.slot.ISlotLogic;
import org.drink.slot.SlotLogic;

import java.util.List;

public class AutomatLogic implements IAutomatLogic, ICoinLogic, ISlotLogic {

    /**
     * The method get a drink back, based of the Interface {@link IDrink} and the Enum {@link IDrink.Drinks}
     *
     * @param drink Enum of the drink, see {@link IDrink.Drinks}
     * @param coins The sum of given coins
     * @return Based of the selection from the Interface {@link IDrink} and the Enum {@link IDrink.Drinks}
     * will be return this method a object representation from {@link IDrink}
     */
    @Override
    public IDrink getDrink(IDrink.Drinks drink, int coins) throws CoinException, SlotException {

        var slotLogic = new SlotLogic();
        var coinLogic = new CoinLogic();
        List<Integer> changeList = null;
        switch (drink) {
            case LIMO:
                changeList = startAutomatProcess(drink, coins, slotLogic, coinLogic);
                return new Limo(changeList, true);
            case CRAFTBEER:
                changeList = startAutomatProcess(drink, coins, slotLogic, coinLogic);
                return new CraftBeer(changeList, true);
            case WATER:
                changeList = startAutomatProcess(drink, coins, slotLogic, coinLogic);
                return new Water(changeList, true);
            default:
                return null;
        }
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

    @Override
    public void addCoin(Coin coin) {
        ICoinLogic coinLogic = new CoinLogic();
        coinLogic.addCoin(coin);
    }

    @Override
    public void removeCoin(Coin coin) {
        ICoinLogic coinLogic = new CoinLogic();
        coinLogic.removeCoin(coin);
    }

    @Override
    public void setCoinSlotEmpty(Coin coin) {
        ICoinLogic coinLogic = new CoinLogic();
        coinLogic.setCoinSlotEmpty(coin);
    }

    @Override
    public boolean isDrinkAvailable(String drink) {
        ISlotLogic slotLogic = new SlotLogic();
        return slotLogic.isDrinkAvailable(drink);
    }

    @Override
    public void addDrink(IDrink.Drinks drink, int count) {
        ISlotLogic slotLogic = new SlotLogic();
        slotLogic.addDrink(drink, count);
    }

    private List<Integer> startAutomatProcess(IDrink.Drinks drink, int coins, SlotLogic slotLogic, CoinLogic coinLogic) throws CoinException, SlotException {
        List<Integer> changeList;
        if (!isCoinSumHigherAsItemCost(drink.getValue(), coins)) {
            throw new CoinException(String.format("Sorry, to get %1$s, please insert %2$.2f€ more", drink.name(), ((drink.getValue() - coins) / 100.0)));
        }
        if (!slotLogic.isDrinkAvailable(drink.name())) {
            throw new SlotException("The selected slot is empty");
        }
        changeList = coinLogic.returnChange(drink.getValue(), coins);

        if (changeList.isEmpty()) {
            throw new CoinException(drink.name()+": Not enough coins to change available");
        }
        slotLogic.decrementStockOfDrink(drink);
        return changeList;
    }
}

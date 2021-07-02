package org.drinkTest.controller;

import org.drink.automat.AutomatLogic;
import org.drink.exception.CoinException;
import org.drink.exception.SlotException;
import org.drink.interfaces.IDrink;
import org.drink.model.Coin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Dennis Martens
 */
@DisplayName("Automat Logic Test")
class SlotLogicTest {
    @Test
    @DisplayName("succeedingTestForABeerWithCoinChange")
    void succeedingTestForABeerWithCoinChange() throws CoinException, SlotException {
        AutomatLogic automatLogic = new AutomatLogic();

        //Set stock from 10ct to 0
        Coin coin = new Coin(0, 10);
        automatLogic.setCoinSlotEmpty(coin);
        coin.setStock(1);
        coin.setValueInCent(20);
        automatLogic.addCoin(coin);

        IDrink craftBeer = automatLogic.getDrink(IDrink.Drinks.LIMO, 140);

        List<Integer> awaitChangeList = new ArrayList<>();
        awaitChangeList.add(20);


        assertArrayEquals(awaitChangeList.toArray(), craftBeer.getChangeList().toArray());
    }
}

package org.drinkTest.controller;

import org.drink.controller.SlotLogic;
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

    private SlotLogic slotLogic = new SlotLogic();

    @Test
    @DisplayName("succeedingTestForABeerWithCoinChange")
    void succeedingTestForABeerWithCoinChange() {
        //Set stock from 10ct to 0
        slotLogic.getCoinObjMap().get(0).setStock(0);
        slotLogic.getCoinObjMap().get(1).setStock(1);

        IDrink craftBeer = slotLogic.getDrink(org.drink.interfaces.IDrink.Drinks.CRAFTBEER, 320);

        List<Integer> awaitChangeList = new ArrayList<>();
        awaitChangeList.add(20);


        assertArrayEquals(awaitChangeList.toArray(), craftBeer.getChangeList().toArray());
    }

    @Test
    @DisplayName("succeedingTestForABeerWithCoinChange in a loop")
    void succeedingTestForABeerWithCoinChangeLoop() {
        //Set stock from 10ct to 0
        slotLogic.getCoinObjMap().get(0).setStock(0);
        int maxLoop = slotLogic.getCoinObjMap().get(1).getStock();
        for (int i = 0; i < maxLoop; i++) {
            IDrink craftBeer = slotLogic.getDrink(org.drink.interfaces.IDrink.Drinks.CRAFTBEER, 320);

            List<Integer> awaitChangeList = new ArrayList<>();
            awaitChangeList.add(20);

            assertArrayEquals(awaitChangeList.toArray(), craftBeer.getChangeList().toArray());
        }
    }

    @Test
    @DisplayName("Expected a Water without coin change and a message")
    void succeedingTestForABeerWithoutCoinChange() {
        IDrink water = slotLogic.getDrink(IDrink.Drinks.WATER, 80);
        assertEquals("Have fun with your WATER", water.getMessage());
        assertEquals("Change Sum is: 0", water.toString());

        //changeList has one entry, a 0 entry. ChangeList has in this case a size from 1
        assertEquals(1, water.getChangeList().size());
    }

    @Test
    @DisplayName("Too few coins")
    void failTestForAWater() {
        IDrink water = slotLogic.getDrink(IDrink.Drinks.WATER, 60);
        assertEquals("Sorry, to get WATER, please insert 0,20€ more", water.getMessage());
        assertEquals(0, water.getChangeList().size());
    }

    @Test
    @DisplayName("Stock up of 20 craft beer units")
    void testAddDrink() {
        int currentStock = slotLogic.getDrinkMap().get(IDrink.Drinks.CRAFTBEER.name());
        slotLogic.addDrink(IDrink.Drinks.CRAFTBEER, 10);
        int expectStock = currentStock + 10;
        assertEquals(expectStock, slotLogic.getDrinkMap().get(IDrink.Drinks.CRAFTBEER.name()));
    }

    @Test
    @DisplayName("Buy beer until the slot is empty")
    void testBuyBeer() {
        var map = slotLogic.getCoinObjMap();
        for (int i = 0; i < 3; i++) {
            Coin coin = map.get(i);
            if (i == 0 || i == 1) coin.setStock(0);
            if (i == 2) coin.setStock(4);
        }

        for (int i = 0; i < 5; i++) {
            IDrink craftBeer = slotLogic.getDrink(IDrink.Drinks.CRAFTBEER, 350);
            if (i == 4) {
                assertEquals(0, craftBeer.getChangeList().toArray().length);
                assertEquals("No change coins available", craftBeer.getMessage());
            } else {
                List<Integer> awaitChangeList = new ArrayList<>();
                awaitChangeList.add(50);
                assertArrayEquals(awaitChangeList.toArray(), craftBeer.getChangeList().toArray());
            }
        }
    }

    @Test
    @DisplayName("Add coins to a slot")
    void addCoin() {
        var map = slotLogic.getCoinObjMap();
        for (int i = 0; i < map.size(); i++) {
            Coin coin = map.get(i);
            var currentStock = coin.getStock();
            coin.setStock(currentStock + 20);
            map.put(i, coin);
            coin = map.get(i);
            assertEquals(currentStock + 20, coin.getStock());
        }
    }

    @Test
    @DisplayName("Set stock of 0 from each coin")
    void removeCoin() {
        var map = slotLogic.getCoinObjMap();
        for (int i = 0; i < map.size(); i++) {
            Coin coin = map.get(i);
            var currentStock = coin.getStock();
            coin.setStock(0);
            map.put(i, coin);
            coin = map.get(i);
            assertEquals(0, coin.getStock());
        }
    }

    @Test
    @DisplayName("Set stock of 0 from each coin")
    void removeCoinAndBuyADrink() {
        var map = slotLogic.getCoinObjMap();
        for (int i = 0; i < map.size(); i++) {
            Coin coin = map.get(i);
            var currentStock = coin.getStock();
            coin.setStock(0);
            map.put(i, coin);
            coin = map.get(i);
            assertEquals(0, coin.getStock());
        }
        IDrink craftBeer = slotLogic.getDrink(IDrink.Drinks.CRAFTBEER, 350);
        assertEquals(0, craftBeer.getChangeList().toArray().length);
        assertEquals("No change coins available", craftBeer.getMessage());
    }

    @Test
    @DisplayName("Get change coin 50, 20 ,10")
    void succeedingTestForABeerWithAExpectedCoinChange() {
        /**
         *   Setup coins to 13x10ct, 5x20ct, 5x50ct
         *   Expected buy 5 beers and get 50ct,20ct,10ct as change and the sixth beer expected 8x10ct
         */
        var map = slotLogic.getCoinObjMap();
        for (int i = 0; i < 4; i++) {
            Coin coin = map.get(i);
            coin.setStock(5);
            if (i == 0) coin.setStock(13);
            map.put(i, coin);
        }

        for (int i = 0; i < 4; i++) {
            Coin coin = map.get(i);
            if (i == 0) {
                assertEquals(13, coin.getStock());
            } else {
                assertEquals(5, coin.getStock());
            }
        }

        for (int i = 0; i < 5; i++) {
            IDrink craftBeer = slotLogic.getDrink(org.drink.interfaces.IDrink.Drinks.CRAFTBEER, 380);
            List<Integer> awaitChangeList = new ArrayList<>();
            awaitChangeList.add(50);
            awaitChangeList.add(20);
            awaitChangeList.add(10);
            assertArrayEquals(awaitChangeList.toArray(), craftBeer.getChangeList().toArray());
        }

        assertEquals(8, map.get(0).getStock());
        assertEquals(0, map.get(1).getStock());
        assertEquals(0, map.get(2).getStock());

        IDrink craftBeer = slotLogic.getDrink(org.drink.interfaces.IDrink.Drinks.CRAFTBEER, 380);
        List<Integer> awaitChangeList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            awaitChangeList.add(10);
        }
        assertArrayEquals(awaitChangeList.toArray(), craftBeer.getChangeList().toArray());

        assertEquals(0, map.get(0).getStock());
    }

}

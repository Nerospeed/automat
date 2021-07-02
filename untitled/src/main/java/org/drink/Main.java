package org.drink;

import org.drink.automat.AutomatLogic;
import org.drink.exception.CoinException;
import org.drink.exception.SlotException;
import org.drink.interfaces.IDrink;

import java.io.IOException;


/**
 * @author Dennis Martens
 */
public class Main {
    public static void main(String[] args) throws CoinException, SlotException {

        AutomatLogic automatLogic = new AutomatLogic();

        try {
            IDrink a = automatLogic.getDrink(IDrink.Drinks.LIMO, 200);
            System.out.println(IDrink.Drinks.LIMO.name()+" is bought "+a.isDrinkBought() + " back coins " + a.getChangeList());
        }catch (Exception ex){
            System.out.println(ex);
        }

        try {
            IDrink b = automatLogic.getDrink(IDrink.Drinks.CRAFTBEER, 400);
            System.out.println(IDrink.Drinks.CRAFTBEER.name()+" is bought "+b.isDrinkBought() + " back coins " + b.getChangeList());
        }catch (Exception ex){
            System.out.println(ex);
        }

        try {
            IDrink c = automatLogic.getDrink(IDrink.Drinks.WATER, 80);
            System.out.println(IDrink.Drinks.WATER.name()+" is bought "+c.isDrinkBought() + " back coins " + c.getChangeList());
        }catch (Exception ex){
            System.out.println(ex);
        }
    }
}

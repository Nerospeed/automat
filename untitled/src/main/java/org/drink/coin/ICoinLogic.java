package org.drink.coin;

import org.drink.model.Coin;

public interface ICoinLogic {
    public enum Coins {
        TEN_CENT(10),
        TWENTY_CENT(20),
        FIFTY_CENT(50),
        ONE_EURO(100),
        TWO_EURO(200);

        private int value;

        Coins(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public void addCoin(Coin coin);

    public void removeCoin(Coin coin);

    public void setCoinSlotEmpty(Coin coin);
}

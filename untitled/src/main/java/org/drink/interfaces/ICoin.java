package org.drink.interfaces;

/**
 * @author Dennis Martens
 */
public interface ICoin {
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
}

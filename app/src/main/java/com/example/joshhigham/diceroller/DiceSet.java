package com.example.joshhigham.diceroller;

import java.util.Random;
import java.util.Vector;

public class DiceSet {
    public int diceQuantity;
    public int diceSize;
    public String modSign;
    public int diceMod;

    private Random rand = new Random();

    public DiceSet() {
        diceMod = diceSize = diceMod = 0;
        modSign = "+";
    }
     public DiceSet(int dq, int ds, String ms, int dm) {
         diceQuantity = dq;
         diceSize = ds;
         modSign = ms;
         diceMod = dm;
     }

    public DiceSet(DiceSet tempDice) {
        diceQuantity = tempDice.diceQuantity;
        diceSize = tempDice.diceSize;
        modSign = tempDice.modSign;
        diceMod = tempDice.diceMod;
    }

    @Override
    public String toString() {
        String retVal = Integer.toString(diceQuantity)+ 'd' + Integer.toString(diceSize) +
                modSign + Integer.toString(diceMod);
        return retVal;
    }

    public long getModInfo(long total) {
        switch(modSign) {
            case "+":
                total += diceMod;
                break;
            case "*":
                total *= diceMod;
                break;
            case "-":
                total -= diceMod;
                break;
            case "/":
                total /= diceMod;
                break;
            default:
                break;
        }
        return total;
    }

    /**
     * Returns the dice rolling.
     * @return Determines Rolling
     */
    public long roll() {
        int total = 0;
        for (int i = 0; i < diceQuantity; ++i) {
            total += rand.nextInt(diceSize) + 1;
        }

        return getModInfo(total);
    }

    /**
     * Handles rolls with multiple dice rolling
     * @return Vector of Integers containing all the rolls
     */
    public Vector<Integer> preciseRoll() {
        Vector<Integer> rolls = new Vector<>();
        for (int i = 0; i < diceQuantity; ++i) {
            rolls.addElement(rand.nextInt(diceSize) + 1);
        }

        return rolls;
    }

    public long getHighestPossible() {
        return getModInfo(diceQuantity * diceSize);
    }

    public long getLowestPossible() {
        return getModInfo(diceQuantity);
    }
}

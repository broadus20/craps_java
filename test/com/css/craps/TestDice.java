package com.css.craps;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestDice {
    Dice dice;

    @Before
    public void setUp() {
        dice = new Dice();
    }

    @Test
    public void testDiceRoll() {
        dice.rollDice();

        assertTrue(dice.getD1() > 0 && dice.getD1() < 7);
        assertTrue(dice.getD2() > 0 && dice.getD2() < 7);
    }

}
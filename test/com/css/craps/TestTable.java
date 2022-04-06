package com.css.craps;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestTable {
    Table table;
    Player player;
    Dice dice;

    @Before
    public void setUp() {
        table = new Table();
        player = new Player();
        dice = new Dice();

        player.setDontPassBetPlaced(true);
        player.setPasslineBet(100);
        table.setPointOn(false);
    }

    @Test
    public void testBankStartingNumber() {
        assertEquals(player.getBank(), 10_000);
    }

    @Test
    public void testPayCondition1() {
        //testing pass line payout

        if (dice.getD1And2() == 7 || dice.getD1And2() == 11) {
            assertEquals(10_200, player.getBank());
            assertEquals(0, player.getPasslineBet());
            assertFalse(player.isTherePassLineOdds());
        }
        else {
            assertFalse(player.isTherePassLineOdds());
        }
    }

    @Test
    public void testPayCondition2() {
        table.setPlayersPointNumber(dice.getD1And2());

        //assertEquals(11_000, player.getBank());
    }
}
package com.css.craps;

public class Player {
    public static int bank = 10000;

    //bets place
    boolean passlineBet = false;
    boolean passlineOdds = false;
    boolean dontPassBet = false;
    boolean dontPassOdds = false;
    boolean fieldBet = false;

    // Table Bets
    public int passline = 0;
    public int oddsPassline = 0;
    public int dontpass = 0;
    public int oddsDontpass = 0;
    public int field = 0;
}
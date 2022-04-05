package com.css.craps;

public class Dice {


    private int d1;
    private int d2;
    private int d1And2;

    public int getD1And2() {
        return d1And2;
    }

    public void setD1And2(int d1And2) {
        this.d1And2 = d1And2;
    }

    public int getD1() {
        return d1;
    }

    public int getD2() {
        return d2;
    }

    public void rollDice(){
        d1 = randomInt(1,6);
        d2 = randomInt(1,6);

        setD1And2(d1 + d2);
    }

    private static int randomInt(int min, int max) {
        int result = 0;

        double rand = Math.random(); // 0.0 - 0.99
        double scaled = (rand * (max-min + 1) + min);
        result = (int) scaled;

        return result;
    }
}
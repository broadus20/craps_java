package com.css.craps;

class Player extends Dice{
    private int bank = 10000;
    private int bet = 0;

    //bets place
    boolean passLineBet = false;
    //boolean passLineOdds = false;
    boolean dontPassBet = false;
    //boolean dontPassOdds = false;
    boolean fieldBet = false;
    
    // Table Bets -  why are these on Player??
    public int passLine = 0;        //passLineBetAmount
    public int oddsPassLine = 0;
    public int dontPass = 0;
    //public int oddsDontPass = 0;
    public int field = 0;

    //ctor
    public Player() {
        
    }
    
    //business method
    public void makeBet() {

    }
    
    //accessor methods
    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public boolean getpassLineBet() {
        return passLineBet;
    }

    public void setPassLineBet(boolean passLineBet) {
        this.passLineBet = passLineBet;
    }

    public boolean getDontPassBet() {
        return dontPassBet;
    }

    public void setDontPassBet(boolean dontPassBet) {
        this.dontPassBet = dontPassBet;
    }

    public boolean getFieldBet() {
        return fieldBet;
    }

    public void setFieldBet(boolean fieldBet) {
        this.fieldBet = fieldBet;
    }

    public int getPassLine() {
        return passLine;
    }

    public void setPassLine(int passLine) {
        this.passLine = passLine;
    }

    public int getOddsPassLine() {
        return oddsPassLine;
    }

    public void setOddsPassLine(int oddsPassLine) {
        this.oddsPassLine = oddsPassLine;
    }

    public int getDontPass() {
        return dontPass;
    }

    public void setDontPass(int dontPass) {
        this.dontPass = dontPass;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }


    //toString()
    @Override
    public String toString() {
        return "Player: " +
            "bank=" + getBank() +
            ", passLineBet=" + getpassLineBet() +
            ", dontPassBet=" + getDontPassBet() +
            ", fieldBet=" + getFieldBet() +
            ", passLine=" + getPassLine() +
            ", dontPass=" + getDontPass() +
            ", field=" + getField() +
            '.';
    }
}
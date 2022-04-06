package com.css.craps;

public class Player {
    //which is the balance
    private int bank = 10000;

    //bets place (make the fields private)
    private boolean isPasslineBetPlaced = false;
    private boolean isTherePassLineOdds = false;
    private boolean isDontPassBetPlaced = false;
    private boolean isThereDontPassOdds = false;
    private boolean isFieldBet = false;

    // Table Bets
    private int passlineBet = 0;
    private int oddsPasslineBet = 0;
    private int dontpassBet = 0;
    private int oddsDontpassBet = 0;
    private int fieldBet = 0;

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public boolean isPasslineBetPlaced() {
        return isPasslineBetPlaced;
    }

    public void setPasslineBetPlaced(boolean passlineBetPlaced) {
        this.isPasslineBetPlaced = passlineBetPlaced;
    }

    public boolean isTherePassLineOdds() {
        return isTherePassLineOdds;
    }

    public void setTherePassLineOdds(boolean therePassLineOdds) {
        this.isTherePassLineOdds = therePassLineOdds;
    }
    //this method name needs to be changed, but it will be easy to just refactor and rename
    public boolean isDontPassBetPlaced() {
        return isDontPassBetPlaced;
    }

    public void setDontPassBetPlaced(boolean dontPassBetPlaced) {
        this.isDontPassBetPlaced = dontPassBetPlaced;
    }

    public boolean isThereDontPassOdds() {
        return isThereDontPassOdds;
    }

    public void setThereDontPassOdds(boolean thereDontPassOdds) {
        this.isThereDontPassOdds = thereDontPassOdds;
    }

    public boolean isFieldBet() {
        return isFieldBet;
    }

    public void setFieldBet(boolean fieldBet) {
        this.isFieldBet = fieldBet;
    }

    public int getPasslineBet() {
        return passlineBet;
    }

    public void setPasslineBet(int passlineBet) {
        this.passlineBet = passlineBet;
    }

    public int getOddsPasslineBet() {
        return oddsPasslineBet;
    }

    public void setOddsPasslineBet(int oddsPasslineBet) {
        this.oddsPasslineBet = oddsPasslineBet;
    }

    public int getDontpassBet() {
        return dontpassBet;
    }

    public void setDontpassBet(int dontpassBet) {
        this.dontpassBet = dontpassBet;
    }

    public int getOddsDontpassBet() {
        return oddsDontpassBet;
    }

    public void setOddsDontpassBet(int oddsDontpassBet) {
        this.oddsDontpassBet = oddsDontpassBet;
    }

    public int getFieldBet() {
        return fieldBet;
    }

    public void setFieldBetNumber(int fieldBet) {
        this.fieldBet = fieldBet;
    }
}
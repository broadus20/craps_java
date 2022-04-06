package com.css.craps;

import com.css.craps.app.CrapsApp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Table {
    private final String baseDiceDisplayPath = "data/dice_";

    //make fields private later
    private Scanner scanner = new Scanner(System.in);
    private Player player = new Player();
    private Dice dice = new Dice();


    // Table Modifiers
    private boolean pointOn = false;
    private int point = 0;
    private  int MAX = 1000;
    private  int MIN = 5;
    // TODO: Function to return odds ratio depending on point
    private double oddsRatio = 0.0; // (6,8)[6/5] - (5,9)[3/2] - (4,10)[2/1]
//    public int roll;


    /**
     * ROLLING THE DICE
     */
    public int getRoll() {
        return dice.getD1And2();
    }

    public void rollDice() throws InterruptedException {
        System.out.println("Press Enter to Roll... ");
        String input = scanner.nextLine();

        if (input.equals("x") || input.equals("X")) {
            System.out.println("Come back any time");
            System.exit(0);
        }

        dice.rollDice();
        dice.show();
        if (this.getRoll() == 8 ||this.getRoll() == 11 ){//is there supposed to be logic in here
            System.out.println("You rolled an: " + this.getRoll());
        }
        else {
            System.out.println("You rolled a: " + this.getRoll());
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println("\n");
    }


    public boolean isPointOn() {
        return pointOn;
    }

    public void setPointOn(boolean pointOn) {
        this.pointOn = pointOn;
    }

//    private int randomInt(int min, int max) {
//        int result = 0;
//
//        double rand = Math.random(); // 0.0 - 0.99
//        double scaled = (rand * (max-min + 1) + min);
//        result = (int) scaled;
//
//        return result;
//    }

    // Method to ask user to input amount for bet
    //changed from getBet to usersInputBet
    public int getBet(){
        int bet = 0;

        boolean validInput = false;
        while (!validInput) {
            System.out.println("\nBet____?  ");
            bet = scanner.nextInt(); //BLOCKS for input
            if (bet == 0){
                validInput = true;
            }
            else if (MIN <= bet && bet <= player.getBank() && bet <= MAX) {
                validInput = true;
           }
            else{
                System.out.println(bet + " is an invalid input \n TABLE MIN: " + MIN + "\n TABLE MAX: " + MAX);
            }
        }

        player.setBank(player.getBank()-bet);
        return bet;
    }

    // General method for placing bets
    //name change from bet -> playerChoosesWhatTypeOfBet
    public void bet(){
        System.out.println("Type 1 for Passline bet");
        System.out.println("Type 2 for Don't Pass bet");
        System.out.println("Type 3 for Field bet");
        boolean validInput = false;
        while (!validInput) {
            try {
                String betType; // Get type of bet to be placed
                betType = scanner.nextLine(); // 1 will be passline bet
                if (betType.equals("x") || betType.equals("X")) {
                    System.out.println("Come back any time!");
                    System.exit(0);
                }
                if (betType.equals("1")) {
                    passlineBet();
                    validInput = true;
                } else if (betType.equals("2")) { // 2 will be don't pass bet
                    dontpassBet();
                    validInput = true;
                } else if(betType.equals("3")){ // field bet
                    fieldBet();
                    validInput = true;
                } else {
                    CrapsApp.clearScreen();

                    System.out.println(betType + " is not a valid bet, please try again.");
                    System.out.println("\n\nType 1 for Passline bet");
                    System.out.println("Type 2 for Don't Pass bet");
                    System.out.println("Type 3 for Field bet");
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //fieldBet -> playerPlacesFieldBet

    public void fieldBet(){
//        player.isFieldBet = true;
        player.setFieldBet(true);
//        player.fieldBet = getBet();
        player.setFieldBetNumber(getBet());
    }

    //dontpassBet -> playerPlacesDontpassBet

    public void dontpassBet(){
        if (isPointOn() != true){
//            player.isDontPassBetPlaced = true;
            player.setDontPassBetPlaced(true);
//            player.dontpassBet = getBet();
            player.setDontpassBet(getBet());
        }
        else{
            System.out.println("Point is on " + getPlayersPointNumber() + " please wait to make passline bet.");
        }
    }

//    passlineBet -> playerPLacesPasslineBet

    public void passlineBet(){
        if (pointOn != true) {
//            player.isPasslineBetPlaced = true;
            player.setPasslineBetPlaced(true);
//            player.passlineBet = getBet(); code before
            player.setPasslineBet(getBet());
        }
        else {
            System.out.println("Point is on " + getPlayersPointNumber() + " please wait until the point is off to make passline bet.");
        }
    }

//    pay -> playerRegularBet

    public void pay(){
        int money = player.getBank();
        // check passline bet exists
        if (player.isDontPassBetPlaced()){
            System.out.println("this is the point; " + isPointOn());
            if (isPointOn()==false){ // Point is off
                if (dice.getD1And2() == 7 || dice.getD1And2() == 11){
                    money += player.getPasslineBet() *2;
//                    player.passlineBet = 0;
                    player.setPasslineBet(0);
//                    player.isPasslineBetPlaced = false;
                    player.setTherePassLineOdds(false);
                }
                else if (dice.getD1And2()  == 2 || dice.getD1And2()  == 3 || dice.getD1And2()  ==12){
                    player.setPasslineBet(0);
//                    player.passlineBet = 0;
                    player.setTherePassLineOdds(false);

//                    player.isPasslineBetPlaced = false;
                }
            }
            else { // point established payout
                if (dice.getD1And2() == getPlayersPointNumber()){ // point hit
                    money += player.getPasslineBet() + player.getOddsPasslineBet() * oddsRatio;
                    player.setPasslineBet(0);
//                    player.passlineBet = 0;
                    player.setOddsPasslineBet(0);
//                    player.oddsPasslineBet = 0;
                    player.setPasslineBetPlaced(false);
//                    player.isPasslineBetPlaced = false;
                }
                else if (dice.getD1And2() == 7){ // 7 out
                    player.setPasslineBet(0);
//                    player.passlineBet = 0;
                    player.setOddsPasslineBet(0);
//                    player.oddsPasslineBet = 0;
                    player.setPasslineBetPlaced(false);
//                    player.isPasslineBetPlaced = false;
                }
            }
        }
        // don't passline bet exists
        if (player.isDontPassBetPlaced()){
            if (!isPointOn()){ // Point is off
                if (dice.getD1And2() == 2 || dice.getD1And2() == 3){ // payout
                    money += player.getDontpassBet() *2;
                    player.setDontpassBet(0);
                    player.setDontPassBetPlaced(false);
                }
                else if (dice.getD1And2() == 7 || dice.getD1And2()== 11){
//                    player.dontpassBet = 0;
//                    player.isDontPassBetPlaced = false;
                    player.setDontpassBet(0);
                    player.setDontPassBetPlaced(false);
                }
            }
            else { // point is on
                if (dice.getD1And2() == 7){ // 7 out (win)
                    money += player.getDontpassBet() + player.getOddsDontpassBet() * oddsRatio;
                    player.setOddsDontpassBet(0);
                    player.setDontpassBet(0);
                    player.setDontPassBetPlaced(false);
                }
                else if (dice.getD1And2() == getPlayersPointNumber()){ // point hit (loss)
                    player.setOddsDontpassBet(0);
                    player.setDontpassBet(0);
                    player.setDontPassBetPlaced(false);
                }
            }
        }
        player.setBank(money);
    }
//pay-> payFieldBet
    public void payField(){
        int money = player.getBank();
        if (player.isFieldBet()){
            if (dice.getD1And2()== 3 || dice.getD1And2() == 4 || dice.getD1And2()== 9 || dice.getD1And2() == 10){
                money += player.getFieldBet() *2;
                player.setFieldBetNumber(0);
                player.setFieldBet(false);
            }  else if (dice.getD1And2() == 2){
                money += player.getFieldBet() *3;
                player.setFieldBetNumber(0);
                player.setFieldBet(false);
            } else if (dice.getD1And2() == 12){
                money += player.getFieldBet() *4;
                player.setFieldBetNumber(0);
                player.setFieldBet(false);
            } else {
                player.setFieldBetNumber(0);
                player.setFieldBet(false);
            }
        }
//        player.bank = money;
        player.setBank(money);
    }



    public void layout() {
        System.out.println("You have $" + displayPlayerBank() + " in the bank");
        layoutBets();
    }

    public void layoutBets(){
        if (player.isPasslineBetPlaced()){
            System.out.println("You have $" + player.getPasslineBet() + " on the Pass Line");
            if (player.isTherePassLineOdds()){
                System.out.println("You have $" + player.getOddsPasslineBet() + " odds");
            }
        }
        if (player.isDontPassBetPlaced()){
            System.out.println("You have $" + player.getDontpassBet() + " on the Don't Pass Line");
            if (player.isThereDontPassOdds()){
                System.out.println("You have $" + player.getOddsDontpassBet() + " odds");
            }
        }
    }


    //change this from getBank to displayBank, then created a getter method for the Player's bank method called getBank
    public int displayPlayerBank() {
        return player.getBank();
    }

    public  int getPlayersPointNumber() {
        return point;
    }

    public  void setPlayersPointNumber(int point) {
        //this was originally Table.point. after making things non-static, I changed it to this.point
        this.point = point;
    }


}
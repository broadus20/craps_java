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
    private double passlineOdds = 0.0;
    private double dontpassOdds = 0.0;

    // Roll Dice
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


    // Method to ask user to input amount for bet
    //changed from getBet to usersInputBet
    public int getBet(){
        int bet = 0;

        boolean validInput = false;
        while (!validInput) {
            System.out.println("Bet____?  ");
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
        System.out.println("Type x to leave table");
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
                    System.out.println("\nPlacing Passline Bet");
                    passlineBet();
                    validInput = true;
                } else if (betType.equals("2")) { // 2 will be don't pass bet
                    System.out.println("\nPlacing Don't Pass Bet");
                    dontpassBet();
                    validInput = true;
                } else if(betType.equals("3")){ // field bet
                    System.out.println("\nPlacing Field Bet");
                    fieldBet();
                    validInput = true;
                } else {
                    CrapsApp.clearScreen();

                    System.out.println(betType + " is not a valid bet, please try again.");
                    System.out.println("\n\nType 1 for Passline bet");
                    System.out.println("Type 2 for Don't Pass bet");
                    System.out.println("Type 3 for Field bet");
                    System.out.println("Type x to leave table");
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void fieldBet(){
        if (player.isFieldBet()) { // If bet already placed... return money to bank and set new bet
            player.setBank(player.getBank() + player.getFieldBet());
            player.setFieldBetNumber(0);
            player.setFieldBet(false);
        }
        player.setFieldBetNumber(getBet());
        System.out.println("\nYou now have $" + player.getFieldBet() + " on the field");
        if (player.getFieldBet() != 0) {
            player.setFieldBet(true);
        }
    }

    public void dontpassBet(){
        if (isPointOn() != true){
            if (player.isPasslineBetPlaced()) { // If bet already placed... return money to bank and set new bet
                player.setBank(player.getBank() + player.getDontpassBet());
                player.setDontpassBet(0);
            }
            player.setDontpassBet(getBet());
            System.out.println("\nYou now have $" + player.getDontpassBet() + " on the Don't Pass line");
            if (player.getDontpassBet() != 0) {
                player.setDontPassBetPlaced(true);
            }
        } else if (pointOn && !player.isDontPassBetPlaced()){ // If point is on and no bet is placed, cannot place odds
            System.out.println("Please wait until point is off to place Don't Pass bet");
        } else{
            System.out.println("Point is on " + getPlayersPointNumber() + " placing odds on Don't Pass Line.");
            if (pointOn){
                if (point == 4 || point == 10){
                    System.out.println("Odds pay 1/2");
                    passlineOdds = 1/2;
                } else if (point == 5 || point == 9){
                    System.out.println("Odds pay 2/3");
                    passlineOdds = 2/3;
                } else if (point == 6 || point == 8){
                    System.out.println("Odds pay 5/6");
                    passlineOdds = 5/6;
                }

            }
            if (player.isThereDontPassOdds()) { // If bet already placed... return money to bank and set new bet
                player.setBank(player.getBank() + player.getOddsDontpassBet());
                player.setOddsDontpassBet(0);
                player.setThereDontPassOdds(false);
            }
            player.setOddsPasslineBet(getBet());
            System.out.println("\nYou now have $" + player.getOddsDontpassBet() + " Don't Pass odds");

            if (player.getOddsDontpassBet() != 0) {
                player.setThereDontPassOdds(true);
            }
        }
    }

    public void passlineBet(){
        double odds;
        if (pointOn != true) {
            if (player.isPasslineBetPlaced()) { // If bet already placed... return money to bank and set new bet
                player.setBank(player.getBank() + player.getPasslineBet());
                player.setPasslineBet(0);
                player.setTherePassLineOdds(false);
            }
            player.setPasslineBet(getBet());

            if (player.getPasslineBet() != 0) {
                player.setPasslineBetPlaced(true);
            }
            System.out.println("\nYou now have $" + player.getPasslineBet() + " on the Passline");
        }
        else if (pointOn && !player.isPasslineBetPlaced()){ // If point is on and no bet is placed, cannot place odds
            System.out.println("Please wait until point is off to place Passline bet");
        } else {
            System.out.println("Point is on " + getPlayersPointNumber() + " placing odds on Passline.");
            if (pointOn){
                if (point == 4 || point == 10){
                    System.out.println("Odds pay 2/1");
                    passlineOdds = 2.0;
                } else if (point == 5 || point == 9){
                    System.out.println("Odds pay 3/2");
                    passlineOdds = 3/2;
                } else if (point == 6 || point == 8){
                    System.out.println("Odds pay 6/5");
                    passlineOdds = 6/5;
                }

            }
            if (player.isTherePassLineOdds()) { // If bet already placed... return money to bank and set new bet
                player.setBank(player.getBank() + player.getOddsPasslineBet());
                player.setOddsPasslineBet(0);
                player.setTherePassLineOdds(false);
            }
            player.setOddsPasslineBet(getBet());
            System.out.println("\nYou now have $" + player.getOddsPasslineBet() + " Passline odds");

            if (player.getOddsPasslineBet() != 0) {
                player.setTherePassLineOdds(true);
            }
        }
    }


    public void pay(){
        int money = player.getBank();
        // check passline bet exists
        if (player.isPasslineBetPlaced()){
            if (isPointOn()==false){ // Point is off
                if (dice.getD1And2() == 7 || dice.getD1And2() == 11){
                    money += player.getPasslineBet() *2;
                    System.out.println("Passline win pays $" +  player.getPasslineBet() *2);
                    player.setPasslineBet(0);
                    player.setOddsPasslineBet(0);
                    player.setPasslineBetPlaced(false);
                    player.setTherePassLineOdds(false);
                }
                else if (dice.getD1And2()  == 2 || dice.getD1And2()  == 3 || dice.getD1And2()  ==12){
                    System.out.println("Passline lost");
                    player.setPasslineBet(0);
                    player.setOddsPasslineBet(0);
                    player.setPasslineBetPlaced(false);
                    player.setTherePassLineOdds(false);
                }
            } else { // point established payout
                if (dice.getD1And2() == getPlayersPointNumber()){ // point hit
                    money += player.getPasslineBet() + player.getOddsPasslineBet() * passlineOdds;
                    System.out.println("You hit your point! \n " +
                            "passline pays $ " + player.getPasslineBet() +
                            "\npassline odds pay $" + player.getOddsPasslineBet() * passlineOdds);
                    player.setPasslineBet(0);
                    player.setOddsPasslineBet(0);
                    player.setPasslineBetPlaced(false);
                    player.setTherePassLineOdds(false);
                }
                else if (dice.getD1And2() == 7){ // 7 out
                    player.setPasslineBet(0);
                    player.setOddsPasslineBet(0);
                    player.setPasslineBetPlaced(false);
                    player.setTherePassLineOdds(false);
                }
            }
        }
        // don't passline bet exists
        if (player.isDontPassBetPlaced()){
            if (!isPointOn()){ // Point is off
                if (dice.getD1And2() == 2 || dice.getD1And2() == 3){ // payout
                    System.out.println("Dont Pass line pays $" + player.getDontpassBet() *2);
                    money += player.getDontpassBet() *2;
                    player.setDontpassBet(0);
                    player.setDontPassBetPlaced(false);
                }
                else if (dice.getD1And2() == 7 || dice.getD1And2()== 11){
                    System.out.println("Don't pass bet lost");
                    player.setDontpassBet(0);
                    player.setDontPassBetPlaced(false);
                }
            }
            else { // point is on
                if (dice.getD1And2() == 7){ // 7 out (win)
                    money += player.getDontpassBet() + player.getOddsDontpassBet() * dontpassOdds;
                    System.out.println("Dont pass pays $" + player.getDontpassBet() + player.getOddsDontpassBet() * dontpassOdds);
                    player.setOddsDontpassBet(0);
                    player.setDontpassBet(0);
                    player.setDontPassBetPlaced(false);
                    player.setThereDontPassOdds(false);
                }
                else if (dice.getD1And2() == getPlayersPointNumber()){ // point hit (loss)
                    System.out.println("Don't pass lost");
                    player.setOddsDontpassBet(0);
                    player.setDontpassBet(0);
                    player.setDontPassBetPlaced(false);
                    player.setThereDontPassOdds(false);
                }
            }
        }
        player.setBank(money);
    }

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
        if (player.isFieldBet()){
            System.out.println("You have $" + player.getFieldBet() + " on the field");
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
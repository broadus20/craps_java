package com.css.craps;

import java.util.Scanner;

public class Table {
    private static Scanner scanner = new Scanner(System.in);
    public static Player player = new Player();


    // Table Modifiers
    public static boolean pointOn = false;
    public static int point = 0;
    private static int MAX = 1000;
    private static int MIN = 5;
    // TODO: Function to return odds ratio depending on point
    private double oddsRatio = 0.0; // (6,8)[6/5] - (5,9)[3/2] - (4,10)[2/1]
    public int roll;

    public static int rollDice(){
        int d1 = randomInt(1,6);
        int d2 = randomInt(1,6);

        return d1 + d2;
    }

    private static int randomInt(int min, int max) {
        int result = 0;

        double rand = Math.random(); // 0.0 - 0.99
        double scaled = (rand * (max-min + 1) + min);
        result = (int) scaled;

        return result;
    }

    // Method to ask user to input amount for bet
    public static int getBet(){
        int bet = 0;

        boolean validInput = false;
        while (!validInput) {
            System.out.println("Bet___?  ");
            bet = scanner.nextInt(); //BLOCKS for input
            if (bet == 0){
                validInput = true;
            }
            else if (MIN <= bet && bet <= player.bank && bet <= MAX) {
                validInput = true;
           }
            else{
                System.out.println(bet + " is an invalid input \n TABLE MIN: " + MIN + "\n TABLE MAX: " + MAX);
            }
        }
        player.bank = player.bank - bet;
        return bet;
    }

    // General method for placing bets
    public static void bet(){
        System.out.println("Type 1 for Passline bet");
        System.out.println("Type 2 for Don't Pass bet");
        System.out.println("Type 3 for Field bet");
        boolean validInput = false;
        while (!validInput) {
            try {
                int betType; // Get type of bet to be placed
                betType = scanner.nextInt(); // 1 will be passline bet
                if (betType == 1) {
                    passlineBet();
                    validInput = true;
                } else if (betType == 2) { // 2 will be don't pass bet
                    dontpassBet();
                    validInput = true;
                } else if(betType == 3){ // field bet
                    fieldBet();
                    validInput = true;
                } else {
                    System.out.println(betType + " is not a valid bet, please try again.");
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void fieldBet(){
        player.fieldBet = true;
        player.field = getBet();
    }

    public static void dontpassBet(){
        if (pointOn != true){
            player.dontPassBet = true;
            player.dontpass = getBet();
        }
        else{
            System.out.println("Point is on " + getPoint() + " please wait to make passline bet.");
        }
    }

    public static void passlineBet(){
        if (pointOn != true) {
            player.passlineBet = true;
            player.passline = getBet();
        }
        else {
            System.out.println("Point is on " + getPoint() + " please wait until the point is off to make passline bet.");
        }
    }

    public void pay(){
        int money = player.bank;
        // check passline bet exists
        if (player.passlineBet){
            if (!pointOn){ // Point is off
                if (this.roll == 7 || this.roll == 11){
                    money += player.passline*2;
                    player.passline = 0;
                    player.passlineBet = false;
                }
                else if (this.roll == 2 || this.roll == 3 || this.roll ==12){
                    player.passline = 0;
                    player.passlineBet = false;
                }
            }
            else { // point established payout
                if (this.roll == point){ // point hit
                    money += player.passline + player.oddsPassline * oddsRatio;
                    player.passline = 0;
                    player.oddsPassline = 0;
                    player.passlineBet = false;
                }
                else if (this.roll == 7){ // 7 out
                    player.passline = 0;
                    player.oddsPassline = 0;
                    player.passlineBet = false;
                }
            }
        }
        // don't passline bet exists
        if (player.dontPassBet){
            if (!pointOn){ // Point is off
                if (this.roll == 2 || this.roll == 3){ // payout
                    money += player.dontpass*2;
                    player.dontpass = 0;
                    player.dontPassBet = false;
                }
                else if (this.roll == 7 || this.roll == 11){
                    player.dontpass = 0;
                    player.dontPassBet = false;
                }
            }
            else { // point is on
                if (this.roll == 7){ // 7 out (win)
                    money += player.dontpass + player.oddsDontpass * oddsRatio;
                    player.dontpass = 0;
                    player.oddsDontpass = 0;
                    player.dontPassBet = false;
                }
                else if (this.roll == point){ // point hit (loss)
                    player.dontpass = 0;
                    player.oddsDontpass = 0;
                    player.dontPassBet = false;
                }
            }
        }
        player.bank = money;
    }

    public void payField(){
        int money = player.bank;
        if (player.fieldBet){
            if (this.roll == 3 || this.roll == 4 || this.roll == 9 || this.roll == 10){
                money += player.field*2;
                player.field = 0;
                player.fieldBet = false;
            }  else if (this.roll == 2){
                money += player.field*3;
                player.field = 0;
                player.fieldBet = false;
            } else if (this.roll == 12){
                money += player.field*4;
                player.field = 0;
                player.fieldBet = false;
            } else {
                player.field = 0;
                player.fieldBet = false;
            }
        }
        player.bank = money;
    }

    public static void layout() {
        System.out.println("You have $" + getBank() + " in the bank");
        layoutBets();
    }

    public static void layoutBets(){
        if ( player.passlineBet){
            System.out.println("You have $" + player.passline + " on the Pass Line");
            if (player.passlineOdds){
                System.out.println("You have $" + player.oddsPassline + " odds");
            }
        }
        if (player.dontPassBet){
            System.out.println("You have $" + player.dontpass + " on the Don't Pass Line");
            if (player.dontPassOdds){
                System.out.println("You have $" + player.oddsDontpass + " odds");
            }
        }
    }

    public static int getBank() {
        return player.bank;
    }

    public static int getPoint() {
        return point;
    }

    public static void setPoint(int point) {
        Table.point = point;
    }
}
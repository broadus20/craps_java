package com.css.craps;

import java.util.Scanner;

public class Table {
    private static Scanner scanner;
    private static Player player = new Player();


    // Table Modifiers
    public static boolean pointOn = false;
    public static int point = 0;
    private static int MAX = 1000;
    private static int MIN = 5;
    // TODO: Function to return oddsratio depending on point
    private double oddsRatio = 0.0; // (6,8)[6/5] - (5,9)[3/2] - (4,10)[2/1]
    public int roll;


    public void Table(){

    }

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
            System.out.println("How much?  ");
            String input = scanner.nextLine(); //BLOCKS for input

            // TODO: 1,2,3,4,5,6  digits for a bet
            if (input.matches("\\d{1,2}")) {
                bet = Integer.parseInt(input);
                if (MIN <= bet && bet <= player.bank && bet <= MAX) {
                    validInput = true;
                }
            }
        }
        return bet;
    }

    // General method for placing bets
    public static void bet(){
        System.out.println("Type 1 for passline bet: ");
        int betType; // Get type of bet to be placed
        betType = scanner.nextInt(); // 1 will be passline bet
        if (betType == 1){
            passlineBet();
        }
    }

    public static void passlineBet(){
        if (pointOn != true) {
            player.passline = getBet();
        }
        else {
            System.out.println("Point is on " + getPoint() + " please wait to make passline bet.");
        }
    }

    // TODO: win/loss pay **** Math for payouts
    public void pay(){
        int money = player.bank;
        if (player.passlineBet){ // check passline bet
            if (!pointOn){ // No point payout
                if (this.roll == 7 || this.roll == 11){
                    money += player.passline;
                }
            }
            else { // point established payout
                if (this.roll == point){
                    money += player.passline + player.oddsPassline * oddsRatio;
                }
            }
        }
        // TODO: Don't pass Bet
        // TODO: Field Bet
    }

    public static int getPoint() {
        return point;
    }

    public static void setPoint(int point) {
        Table.point = point;
    }
}
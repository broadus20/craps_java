package com.css.craps;

import java.util.Scanner;

public class Table {
    private static Scanner scanner;
    private static Player player = new Player();


    // Table Modifiers
    private static boolean pointOn = false;
    private int pointNum = 0;
    private static int MAX_BET = 1000;
    private static int MIN_BET = 5;
    // TODO: Function to return oddsratio depending on point, will affect payout
    private double oddsRatio = 0.0; // (6,8)[6/5] - (5,9)[3/2] - (4,10)[2/1]
    public int roll;


    public void Table(){

    }

    // Method to ask user to input amount for bet
    public static int userInputBet(){
        int bet = 0;

        boolean validInput = false;
        while (!validInput) {
            System.out.println("How much?  ");
            String input = scanner.nextLine(); //BLOCKS for input

            // TODO: 1,2,3,4,5,6  digits for a bet
            if (input.matches("\\d{1,2}")) {
                bet = Integer.parseInt(input);
                if (MIN_BET <= bet && bet <= player.getBank() && bet <= MAX_BET) {
                    validInput = true;
                }
            }
        }
        return bet;
    }

    // General method for placing bets
    public void bet(){
        System.out.println("Type 1 for passline bet: ");
        int betType; // Get type of bet to be placed
        betType = scanner.nextInt(); // 1 will be passline bet
        if (betType == 1){
            passlineBet();
        }
    }

    public void passlineBet(){
        if (pointOn != true) {
            player.passLine = userInputBet();
        }
        else {
            System.out.println("Point is on " + getPointNum() + " please wait to make passline bet.");
        }
    }

    // TODO: win/loss pay **** Math for payouts
    public void pay(){
        int money = player.getBank();
        if (player.getpassLineBet()){ // check passline bet
            if (!getPointOn()){ // No point payout
                if (this.getRoll() == 7 || this.getRoll() == 11){
                    money += player.getPassLine();
                }
            }
            else { // point established payout
                if (this.getRoll() == getPointNum()){
                    money += player.getPassLine()+ player.getOddsPassLine() * getOddsRatio();
                }
            }
        }
        // TODO: Don't pass Bet
        // TODO: Field Bet
    }

    public static boolean getPointOn() {
        return pointOn;
    }

    public static void setPointOn(boolean pointOn) {
        Table.pointOn = pointOn;
    }

    public int getPointNum() {
        return pointNum;
    }

    public void setPointNum(int pointNum) {
        this.pointNum = pointNum;
    }

    public int getRoll() {
        return roll;
    }

    public double getOddsRatio() {
        return oddsRatio;
    }
}
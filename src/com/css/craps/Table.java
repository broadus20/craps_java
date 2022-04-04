package com.css.craps;

import java.util.Scanner;

public class Table {
    private static Scanner scanner;
    private static Player player = new Player();


    // Table Modifiers
    private boolean pointOn = false;
    private int point = 0;
    private static int MAX = 1000;
    private static int MIN = 5;

    // Table Bets
    private int passlineBet = 0;
    private int passlineOdds = 0;
    private int dontpassBet = 0;
    private int dontpassOdds = 0;
    private int fieldBet = 0;


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
            if (input.matches("\\d{1,2}")) { // any
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
        int betType; // Get type of bet to be placed
        betType = 1; // 1 will be passline bet
        if (betType == 1){

        }
    }

    public static void passlineBet(){
        if (pointOn != true) {
            passlineBet = getBet();
        }
        else {

        }
    }

    // TODO: win/loss pay
    public static void pay(){
        break;
    }
}
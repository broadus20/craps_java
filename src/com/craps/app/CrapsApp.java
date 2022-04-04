package com.craps.app;

import com.css.craps.Table;

import java.util.Scanner;

public class CrapsApp {
    private static Table table = new Table();
    private Scanner scanner = new Scanner(System.in);

    public void execute(){
        welcome();
        while (true){
            main();
        }
    }

    private void main() {
        try{
            // Make a bet
            promptForBet();
            // Roll Dice
            rollDice();
            // outcome
            outcome();
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void outcome() {
        // Point Off, If win point stays off, If loss money is wiped, If point - phase 2
        if (table.pointOn == false) {
            resultNoPoint();
        }
        else if (table.roll == table.point) { // if the point is hit
            table.pay();
            table.pointOn = false;
        }
        else { // if 7, wipe board
            if (table.roll == 7){
                table.pay();
                table.pointOn = false;
            }
        }
    }

    private void resultNoPoint(){
        // Win Case
        if (table.roll == 7 || table.roll == 11){
            table.pay();
        }
        // Lose Case
        else if (table.roll == 2 || table.roll == 3 || table.roll == 12){
            table.pay();
        }
        // Set Point
        else{
            table.point = table.roll;
        }
    }

    private String promptForBet() {
        String yn;

        boolean validInput = false;
        while (!validInput) {
            System.out.println("Would you like to make a bet? [y/n] ");
            String input = scanner.nextLine();

            if (input.matches("\\s{y,n}")) {
                switch (input) {
                    case "y":
                        validInput = true;
                        Table.bet();
                        break;
                    case "n":
                        validInput = true;
                        break;
                    default:
                        System.out.println("Please try again");
                }

            }
        }
        return "";
    }

    private void rollDice(){
        System.out.println("Roll Dice");
        System.out.println("\n\n");
        String input = scanner.nextLine();
        table.roll = table.rollDice();
        System.out.println("You rolled a: " + table.roll);
        System.out.println("\n\n");
    }

    private void welcome(){
        System.out.println("\n\n");
        System.out.println("T H E   F L A M I N G O   H O T E L");
        System.out.println("------------------------------------");
        System.out.println("\n\n");
    }
}
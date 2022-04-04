package com.craps.app;

import com.css.craps.Dice;
import com.css.craps.Table;

import java.util.Scanner;

public class CrapsApp {
    private static Table table = new Table();
    private static Dice dice = new Dice();
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

    // Point Off, If win point stays off, If loss money is wiped, If point - phase 2
    private void confirmPoint() {
        if (table.getPointOn() == false) {
            resultNoPoint();
        }
        else {
            outcome();
        }
    }

    private void outcome() {
        if (table.roll == table.getPointNum()) { // if the point is hit
            table.pay();
            table.setPointOn(false);
        }
        else { // if 7, wipe board
            if (table.roll == 7){
                table.pay();
                table.setPointOn(false);
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
            table.setPointNum(table.roll);
        }
    }

    private void promptForBet() {
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Would you like to place a bet? [y/n] ");
            String input = scanner.nextLine();
            if (input.equals("y") || input.equals("Y")) {
                table.bet();
                //multiBet();
                validInput = true;
            } else if (input.equals("n") || input.equals("N")){
                validInput = true;
            }
            else {
                System.out.println(input + " is not a valid input. Please try again");
            }
        }
    }

    private void rollDice(){
        System.out.println("Roll Dice");
        System.out.println("\n\n");
        String input = scanner.nextLine();
        table.roll = dice.rollDice();
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
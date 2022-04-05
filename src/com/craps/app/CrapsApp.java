package com.craps.app;

import com.css.craps.Dice;
import com.css.craps.Table;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class CrapsApp {
    private static final String flamingoPath = "data/flamingo_hotel.txt";
    private Table table = new Table();
    private Scanner scanner = new Scanner(System.in);

    public void execute(){      // error: while statement cannot complete without throwing exception
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
        if (table.calculateRoll() == table.getPointNum()) { // if the point is hit
            table.pay();
            table.setPointOn(false);
        }
        else { // if 7, wipe board
            if (table.calculateRoll() == 7){
                table.pay();
                table.setPointOn(false);
            }
        }
    }

    private void resultNoPoint(){
        // Win Case
        if (table.calculateRoll() == 7 || table.calculateRoll() == 11){
            table.pay();
        }
        // Lose Case
        else if (table.calculateRoll()== 2 ||table.calculateRoll()== 3 ||table.calculateRoll() == 12){
            table.pay();
        }
        // Set Point
        else{
            table.setPointNum(table.calculateRoll());
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
        System.out.println("You rolled a: " + table.calculateRoll());
        System.out.println("\n\n");
    }

    private void welcome(){
        System.out.println("\n\n");
        try {
            List<String> lines = Files.readAllLines(Paths.get(flamingoPath));
            for (String line : lines) {
                System.out.println(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n\n");
    }
}
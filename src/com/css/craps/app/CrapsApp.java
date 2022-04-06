package com.css.craps.app;

import com.css.craps.Table;

import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
//app.utils library, a console.clear() (clears the console)

public class CrapsApp {
    private static final String flamingoPath = "data/flamingo_hotel.txt";
    private static final String welcomeDicePath = "data/welcome_dice.txt";
    private static final String rulesPath = "data/help/rules.txt";
    private Table table = new Table();
    private Scanner scanner = new Scanner(System.in);

    public void execute() throws IOException {      // error: while statement cannot complete without throwing exception
        welcome();
        System.out.println("Press [?] at any time if you need help");
        while (true){
            main();
        }
    }

    private void main() {
        try{
            // Make a bet
            promptForBet();
            // Roll Dice
            table.rollDice();
            // outcome
            outcome();
            // Play profile
            layout();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void clearScreen() {
        System.out.println(System.lineSeparator().repeat(50));
    }

    private void layout() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        table.layout();
        System.out.println("\n");
        if(table.isPointOn()){
            System.out.println("The point is: " + table.getPlayersPointNumber());
        }
        else{
            System.out.println("There is no point");
        }
        System.out.println("\n");
    }


    private void outcome() {
        // Point Off, If win point stays off, If loss money is wiped, If point - phase 2
        if (table.isPointOn() == false) {
            table.payField();
            resultNoPoint();
        }
        else if (table.getRoll() == table.getPlayersPointNumber()) { // if the point is hit
            table.payField();
            table.pay();
            table.setPointOn(false);
            table.setPlayersPointNumber(0);
        }
        else { // if 7, wipe board
            if (table.getRoll() == 7){
                table.payField();
                table.pay();
                table.setPointOn(false);
                table.setPlayersPointNumber(0);
            }
        }
    }

    private void resultNoPoint(){
        // Win Case
        if (table.getRoll() == 7 || table.getRoll() == 11){
            table.pay();
        }
        // Lose Case
        else if (table.getRoll() == 2 || table.getRoll() == 3 || table.getRoll() == 12){
            table.pay();
        }
        // Set Point
        else{
            table.setPlayersPointNumber(table.getRoll());
            table.setPointOn(true);
        }
    }

    private void promptForBet() {
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Would you like to place a bet? [y/n] ");
            String input = scanner.nextLine();
            if (input.equals("y") || input.equals("Y")) {
                clearScreen();
                table.bet();
                multiBet();
                validInput = true;
            } else if (input.equals("n") || input.equals("N")){
                clearScreen();
                validInput = true;
            } else if (input.equals("?")){ // [?] Help Prompter
                clearScreen();
                System.out.println("\n\n");
                try {
                    List<String> lines = Files.readAllLines(Paths.get(rulesPath));
                    for (String line : lines) {
                        System.out.println(line);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("\n");
            } else {
                clearScreen();
            System.out.println(input + " is not a valid input. Please try again");
            }
        }
    }


    private void multiBet(){
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Would you like to place another bet? [y/n] ");
            String input = scanner.nextLine();
            if (input.equals("y") || input.equals("Y")) {
                table.bet();
            } else if (input.equals("n") || input.equals("N")){
                validInput = true;
            }
            else {
                System.out.println(input + " is not a valid input. Please try again");
            }
        }
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
        try {
            List<String> lines = Files.readAllLines(Paths.get(welcomeDicePath));
            for (String line : lines) {
                System.out.println(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n");

    }
}
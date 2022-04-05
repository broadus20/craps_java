package com.craps.app;

import com.css.craps.Table;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CrapsApp {
    private static final String flamingoPath = "data/flamingo_hotel.txt";
    private static final String welcomeDicePath = "data/welcome_dice.txt";
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
            // Play profile
            layout();
        }
        catch (Exception e){
            e.printStackTrace();
        }
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
        else if (table.getRoll() == table.point) { // if the point is hit
            table.payField();
            table.pay();
            table.setPointOn(false);
        }
        else { // if 7, wipe board
            if (table.getRoll() == 7){
                table.payField();
                table.pay();
                table.setPointOn(false);
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
            table.setPoint(table.getRoll());
            table.setPointOn(false);
        }
    }

    private void promptForBet() {
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Would you like to place a bet? [y/n] ");
            String input = scanner.nextLine();
            if (input.equals("y") || input.equals("Y")) {
                table.bet();
                multiBet();
                validInput = true;
            } else if (input.equals("n") || input.equals("N")){
                validInput = true;
            }
            else {
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

    private void rollDice(){
        System.out.println("Press Enter to Roll... ");
        String input = scanner.nextLine();

        if (table.getRoll() == 8 ||table.getRoll() == 11 ){
            System.out.println("You rolled an: " + table.getRoll());
        }
        else {
            System.out.println("You rolled a: " + table.getRoll());
        }
        System.out.println("\n");
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
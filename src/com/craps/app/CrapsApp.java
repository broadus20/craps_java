package com.craps.app;

import com.css.craps.Table;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
//app.utils library, a console.clear() (clears the console)

public class CrapsApp {
    private Table table = new Table();
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
        else if (table.getRoll() == table.getPoint()) { // if the point is hit
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
        System.out.println("T H E   F L A M I N G O   H O T E L");
        System.out.println("------------------------------------");
        System.out.println(
                "  ____\n" +
                " /\\' .\\    _____\n" +
                "/: \\___\\  / .  /\\\n" +
                "\\' / . / /____/..\\\n" +
                " \\/___/  \\'  '\\  /\n" +
                "          \\'__'\\/\n" +
                "\n");
    }
}

//we need to create a base package name. so com.css.craps.app
//
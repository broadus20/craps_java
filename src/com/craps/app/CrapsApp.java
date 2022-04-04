package com.craps.app;

import com.css.craps.Table;

import java.util.Scanner;

public class CrapsApp {
    private Table tableImpl = new Table();
    private Scanner scanner = new Scanner(System.in);

    public void execute(){
        welcome();
        while (true){
            main();
        }
    }

    private void main() {
        try{
            // bets
            promptForBet();

            // Roll Dice
            System.out.println("Roll Dice");
            System.out.println("\n\n");
            String input = scanner.nextLine();
            int roll = Table.rollDice();
            System.out.println("You rolled a: " + roll);
            System.out.println("\n\n");


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private String promptForBet() {
        String yn;

        boolean validInput = false;
        while (!validInput) {
            System.out.println("Would you like to make a bet? [y/n] ");
            String input = scanner.nextLine();

            if (input.matches("\\s{Y,N}")) {
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

    private void welcome(){
        System.out.println("\n\n");
        System.out.println("T H E   F L A M I N G O   H O T E L");
        System.out.println("------------------------------------");
        System.out.println("\n\n");
    }



}
package com.css.craps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dice {

    private static final List<String> diceImages = new ArrayList<>();

    private int d1;
    private int d2;
    private int d1And2;

    public Dice() {
         loadDiceImages();
    }

    //eat the exception and don't say throws
    private void loadDiceImages() {
        try {
            for (int i = 1; i <= 6; i++) {
                String diceImage = Files.readString(Path.of("data/dice_" + i + ".txt"));
                diceImages.add(diceImage);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        String d1Image = diceImages.get(this.getD1() - 1);
        String d2Image = diceImages.get(this.getD2() - 1);

        List<String> d1Lines = Arrays.asList(d1Image.split("\\n"));
        List<String> d2Lines = Arrays.asList(d2Image.split("\\n"));

        for (int i = 0; i <d1Lines.size(); i++) {
            System.out.println(d1Lines.get(i) + "     " + d2Lines.get(i));
        }
    }


    public int getD1And2() {
        return d1And2;
    }

    public void setD1And2(int d1And2) {
        this.d1And2 = d1And2;
    }

    public int getD1() {
        return d1;
    }

    public int getD2() {
        return d2;
    }

    public void rollDice(){
        d1 = randomInt(1,6);
        d2 = randomInt(1,6);

        setD1And2(d1 + d2);
    }

    private static int randomInt(int min, int max) {
        int result = 0;

        double rand = Math.random(); // 0.0 - 0.99
        double scaled = (rand * (max-min + 1) + min);
        result = (int) scaled;

        return result;
    }
}
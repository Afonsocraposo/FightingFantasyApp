package com.example.robot.fightingfantasy.classes;

import java.io.Serializable;
import java.util.Random;

public class Dice implements Serializable {

    private Random dice1;
    private Random dice2;

    public Dice(){
        dice1 = new Random();
        dice2 = new Random();
    }

    public int roll1Dice(){
        return dice1.nextInt(6) + 1;
    }

    public int roll2Dice(){
        return (dice1.nextInt(6) + 2 + dice2.nextInt(6));
    }

}

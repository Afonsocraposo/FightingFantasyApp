package com.example.robot.fightingfantasy.classes;

import java.io.Serializable;

public class Item implements Serializable {

    private String item;

    public Item(){}

    public String getItem() {
        return item;
    }

    public void setItem(String nome) {
        this.item = nome;
    }
}
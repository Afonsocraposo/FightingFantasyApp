package com.example.robot.fightingfantasy.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {

    private int abilita;
    private int abilitaIni;

    private int resistenza;
    private int resistenzaIni;

    private int fortuna;
    private int fortunaIni;

    private List<Item> equipa;

    private int oro;
    private String tesoro;
    private int provviste;

    private boolean luck;

    Dice dice = new Dice();

    public Player(){
        abilitaIni = dice.roll1Dice() + 6;
        abilita = abilitaIni;

        resistenzaIni = dice.roll2Dice() + 12;
        resistenza = resistenzaIni;

        fortunaIni = dice.roll1Dice() +6;
        fortuna = fortunaIni;

        equipa = new ArrayList<>();

        Item spada = new Item();
        spada.setItem("Spada");
        equipa.add(spada);

        Item scudo = new Item();
        scudo.setItem("Scudo");
        equipa.add(scudo);

        Item armatura = new Item();
        armatura.setItem("Armatura di cuoio");
        equipa.add(armatura);

        oro = 0;
        tesoro = "--";
        provviste = 10;
    }

    public void changeAbilita(int n){
        abilita += n;
        if(abilita>abilitaIni) abilita=abilitaIni;
        if(abilita<0) abilita=0;
    }

    public void changeResistenza(int n){
        resistenza += n;
        if(resistenza>resistenzaIni) resistenza=resistenzaIni;
        if(resistenza<0) resistenza=0;
    }

    public void changeFortuna(int n){
        fortuna += n;
        if(fortuna>fortunaIni) fortuna=fortunaIni;
        if(fortuna<0) fortunaIni=0;
    }

    public boolean tentareFortuna(){
        int res = dice.roll2Dice();
        if(res<=fortuna){
            luck = true;
        }else{
            luck = false;
        }
        fortuna--;
        return luck;
    }

    public void resetLuck(){
        luck = false;
    }

    public void setAbilitaIni(int abilitaIni) {
        this.abilitaIni = abilitaIni;
    }

    public void setResistenzaIni(int resistenzaIni) {
        this.resistenzaIni = resistenzaIni;
    }

    public void setFortunaIni(int fortunaIni) {
        this.fortunaIni = fortunaIni;
    }

    public void potionAbilita(){
        abilita = abilitaIni;
    }

    public void potionResistenza(){
        resistenza = resistenzaIni;
    }

    public void potionFortuna(){
        fortunaIni++;
        fortuna = fortunaIni;
    }

    public int getAbilita() {
        return abilita;
    }

    public int getAbilitaIni() {
        return abilitaIni;
    }

    public int getResistenza() {
        return resistenza;
    }

    public int getResistenzaIni() {
        return resistenzaIni;
    }

    public int getFortuna() {
        return fortuna;
    }

    public int getFortunaIni() {
        return fortunaIni;
    }

    public int getOro() {
        return oro;
    }

    public String getTesoro() {
        return tesoro;
    }

    public int getProvviste() {
        return provviste;
    }

    public boolean getLuck() {
        return luck;
    }

    public List<Item> getEquipa(){
        return equipa;
    }

    public void eat() {
        if(provviste>0){
            provviste--;
            changeResistenza(4);
        }
    }

    public void changeOro(int n){
        oro += n;
    }

    public void changeProvviste(int n){
        if(provviste>0) provviste += n;
    }

    public void setTesoro(String tesoro) {
        this.tesoro = tesoro;
    }
}

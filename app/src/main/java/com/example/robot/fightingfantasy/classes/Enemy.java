package com.example.robot.fightingfantasy.classes;

import java.io.Serializable;

public class Enemy implements Serializable {

    protected int abilita;
    protected int abilitaIni;

    protected int resistenza;
    protected int resistenzaIni;

    public Enemy(int abil, int resis){
        abilitaIni = abil;
        abilita = abilitaIni;

        resistenzaIni = resis;
        resistenza = resistenzaIni;

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

    public int getAbilita() {
        return abilita;
    }

    public int getResistenza() {
        return resistenza;
    }

    public int getAbilitaIni() {
        return abilitaIni;
    }

    public int getResistenzaIni() {
        return resistenzaIni;
    }
}

package com.example.robot.fightingfantasy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.robot.fightingfantasy.classes.Dice;
import com.example.robot.fightingfantasy.classes.Enemy;
import com.example.robot.fightingfantasy.classes.Player;


public class CombatActivity extends AppCompatActivity {

    Player player;
    Enemy enemy;

    TextView valueAbilita;
    TextView valueResistenza;
    TextView valueFortuna;

    TextView valueAbilitaEnem;
    TextView valueResistenzaEnem;

    TextView valueDice;
    TextView valueDiceEnem;

    RelativeLayout backPlay;

    Dice dice;

    Context context;

    Boolean firstClick;
    Boolean fortunaClicked;
    Boolean exitClick;
    Boolean fortunaExitClicked;

    int damage;
    int valueEnem;
    int valuePlay;
    int valueDiceFortuna;
    int damageExit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat);

        context = CombatActivity.this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            player = (Player) getIntent().getSerializableExtra("pl"); //Obtaining data
            enemy = (Enemy) getIntent().getSerializableExtra("en");
        }

        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        dice = new Dice();

        valueDice = findViewById(R.id.textDice);
        valueDiceEnem = findViewById(R.id.textDiceEnem);

        backPlay = findViewById(R.id.relativeLayoutPlay);

        firstClick = true;
        fortunaClicked = false;
        exitClick = false;
        fortunaExitClicked = false;

        damage = 0;

        final Button btnUsareFortuna = findViewById(R.id.buttonUsareFortuna);


        Button btnCombat = findViewById(R.id.buttonCombattere);
        btnCombat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vibe.vibrate(50);
                exitClick = false;
                fortunaExitClicked = false;

                if(firstClick) {


                    valueEnem = dice.roll2Dice() + enemy.getAbilita();
                    String textValueEnem = Integer.toString(valueEnem);
                    valueDiceEnem.setText(textValueEnem);
                    valueDiceEnem.setTextColor(Color.BLACK);


                    valuePlay = dice.roll2Dice() + player.getAbilita();
                    String textValuePlay = Integer.toString(valuePlay);
                    valueDice.setText(textValuePlay);
                    valueDice.setTextColor(Color.BLACK);


                    if (valuePlay > valueEnem) {
                        valueDice.setBackgroundResource(R.color.light_green);
                        valueDiceEnem.setBackgroundResource(R.color.white);
                    } else {
                        valueDice.setBackgroundResource(R.color.white);
                        valueDiceEnem.setBackgroundResource(R.color.light_red);

                    }

                    damage = 2;

                    firstClick = false;
                    fortunaClicked = false;
                    btnUsareFortuna.setBackgroundResource(R.color.gold);

                } else {

                    valueDice.setBackgroundResource(R.color.white);
                    valueDiceEnem.setBackgroundResource(R.color.white);

                    if (valuePlay > valueEnem) {

                        String textDamageEnem = "-" + Integer.toString(damage);
                        valueDiceEnem.setText(textDamageEnem);
                        valueDiceEnem.setTextColor(Color.RED);
                        valueDice.setText(" ");

                        enemy.changeResistenza(-damage);
                        String textResistenzaEnem = Integer.toString(enemy.getResistenza()) + "/" +  Integer.toString(enemy.getResistenzaIni());
                        valueResistenzaEnem.setText(textResistenzaEnem);

                    } else {

                        String textDamagePlay = "-" + Integer.toString(damage);
                        valueDice.setText(textDamagePlay);
                        valueDice.setTextColor(Color.RED);
                        valueDiceEnem.setText(" ");

                        player.changeResistenza(-damage);
                        String textResistenza = Integer.toString(player.getResistenza()) + "/" +  Integer.toString(player.getResistenzaIni());
                        valueResistenza.setText(textResistenza);

                    }

                    backPlay.setBackgroundResource(R.color.black);
                    firstClick = true;

                    if(player.getResistenza()<=0 || enemy.getResistenza()<=0){

                        Intent intentPlayer = new Intent();
                        intentPlayer.putExtra("PLAYER", player);

                        setResult(2, intentPlayer );
                        finish();
                    }

                }
            }
        });

        btnUsareFortuna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(exitClick && !fortunaExitClicked){

                    vibe.vibrate(50);

                    valueDiceFortuna = dice.roll2Dice();

                    if (valueDiceFortuna <= player.getFortuna()) {

                        damageExit = 1;
                        btnUsareFortuna.setBackgroundResource(R.color.light_green);


                    } else {

                        damageExit = 3;
                        btnUsareFortuna.setBackgroundResource(R.color.red);


                    }

                    fortunaExitClicked = true;

                    player.changeFortuna(-1);
                    String textFortuna = Integer.toString(player.getFortuna()) + "/" + Integer.toString(player.getFortunaIni());
                    valueFortuna.setText(textFortuna);

                }else {

                    if (!fortunaClicked && player.getFortuna() > 0) {

                        vibe.vibrate(50);

                        exitClick = false;
                        fortunaExitClicked = false;

                        valueDiceFortuna = dice.roll2Dice();

                        if (valueDiceFortuna <= player.getFortuna()) {


                            backPlay.setBackgroundResource(R.color.gold);

                            if (valueEnem < valuePlay) {
                                damage = 4;
                            } else {
                                damage = 1;
                            }

                        } else {

                            backPlay.setBackgroundResource(R.color.red);

                            if (valueEnem < valuePlay) {
                                damage = 1;
                            } else {
                                damage = 3;
                            }

                        }

                        fortunaClicked = true;

                        btnUsareFortuna.setBackgroundResource(R.color.white);
                        player.changeFortuna(-1);
                        String textFortuna = Integer.toString(player.getFortuna()) + "/" + Integer.toString(player.getFortunaIni());
                        valueFortuna.setText(textFortuna);

                    }

                }

            }
        });


        Button btnFuggire = findViewById(R.id.buttonFuggire);
        btnFuggire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!exitClick){
                    exitClick = true;
                    fortunaExitClicked = false;
                    btnUsareFortuna.setBackgroundResource(R.color.gold);

                } else {
                    player.changeResistenza(-damageExit);

                    Intent intentPlayer = new Intent();
                    intentPlayer.putExtra("PLAYER", player);

                    setResult(2, intentPlayer );
                    finish();
                }

            }
        });

        valueAbilita = findViewById(R.id.valueAbilita);
        String textAbilita = Integer.toString(player.getAbilita()) + "/" +  Integer.toString(player.getAbilitaIni());
        valueAbilita.setText(textAbilita);

        valueResistenza = findViewById(R.id.valueResistenza);
        String textResistenza = Integer.toString(player.getResistenza()) + "/" +  Integer.toString(player.getResistenzaIni());
        valueResistenza.setText(textResistenza);

        valueFortuna = findViewById(R.id.valueFortuna);
        String textFortuna = Integer.toString(player.getFortuna()) + "/" +  Integer.toString(player.getFortunaIni());
        valueFortuna.setText(textFortuna);

        valueAbilitaEnem = findViewById(R.id.valueAbilitaEnem);
        String textAbilitaEnem = Integer.toString(enemy.getAbilita()) + "/" +  Integer.toString(enemy.getAbilitaIni());
        valueAbilitaEnem.setText(textAbilitaEnem);

        valueResistenzaEnem = findViewById(R.id.valueResistenzaEnem);
        String textResistenzaEnem = Integer.toString(enemy.getResistenza()) + "/" +  Integer.toString(enemy.getResistenzaIni());
        valueResistenzaEnem.setText(textResistenzaEnem);

    }


    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }

}


package com.example.robot.fightingfantasy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.robot.fightingfantasy.classes.Dice;
import com.example.robot.fightingfantasy.classes.Enemy;
import com.example.robot.fightingfantasy.classes.Item;
import com.example.robot.fightingfantasy.classes.Player;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ClickRecyclerView_Interface {

    TextView valueAbilita;
    TextView valueResistenza;
    TextView valueFortuna;
    TextView valueOro;
    TextView valueTesoro;
    TextView valueProvviste;
    TextView valueDice;

    Context context;

    Player player;
    Dice dice;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        if(savedInstanceState!=null){
            player = (Player) savedInstanceState.getSerializable("savePLAYER");
        }else{

            player = readJSON("ffPLAYER");

            if(player==null) player = new Player();
        }

        dice = new Dice();


        valueAbilita = findViewById(R.id.valueAbilita);
        String textAbilita = Integer.toString(player.getAbilita()) + "/" +  Integer.toString(player.getAbilitaIni());
        valueAbilita.setText(textAbilita);

        valueResistenza = findViewById(R.id.valueResistenza);
        String textResistenza = Integer.toString(player.getResistenza()) + "/" +  Integer.toString(player.getResistenzaIni());
        valueResistenza.setText(textResistenza);

        valueFortuna = findViewById(R.id.valueFortuna);
        String textFortuna = Integer.toString(player.getFortuna()) + "/" +  Integer.toString(player.getFortunaIni());
        valueFortuna.setText(textFortuna);

        valueOro = findViewById(R.id.valueOro);
        String textOro = Integer.toString(player.getOro());
        valueOro.setText(textOro);

        valueTesoro = findViewById(R.id.valueTesoro);
        valueTesoro.setText(player.getTesoro());

        valueProvviste = findViewById(R.id.valueProvviste);
        String textProvviste = Integer.toString(player.getProvviste());
        valueProvviste.setText(textProvviste);

        valueDice = findViewById(R.id.textDice);

        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Button btnDice1 = findViewById(R.id.dice1);
        btnDice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = dice.roll1Dice();
                String textValue = Integer.toString(value);
                vibe.vibrate(50);
                valueDice.setText(textValue);
            }
        });

        Button btnDice2 = findViewById(R.id.dice2);
        btnDice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = dice.roll2Dice();
                String textValue = Integer.toString(value);
                vibe.vibrate(50);
                valueDice.setText(textValue);
            }
        });

        Button btnMangiare = findViewById(R.id.buttonMangiare);
        btnMangiare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.eat();

                String textResistenza = Integer.toString(player.getResistenza()) + "/" +  Integer.toString(player.getResistenzaIni());
                valueResistenza.setText(textResistenza);

                String textProvviste = Integer.toString(player.getProvviste());
                valueProvviste.setText(textProvviste);
            }
        });

        Button btnAbilitaPlus = findViewById(R.id.buttonAbilitaPlus);
        btnAbilitaPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.changeAbilita(1);
                String textAbilita = Integer.toString(player.getAbilita()) + "/" +  Integer.toString(player.getAbilitaIni());
                valueAbilita.setText(textAbilita);
            }
        });

        Button btnAbilitaMinus = findViewById(R.id.buttonAbilitaMinus);
        btnAbilitaMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.changeAbilita((-1));
                String textAbilita = Integer.toString(player.getAbilita()) + "/" +  Integer.toString(player.getAbilitaIni());
                valueAbilita.setText(textAbilita);
            }
        });

        Button btnResistenzaPlus = findViewById(R.id.buttonResistenzaPlus);
        btnResistenzaPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.changeResistenza(1);
                String textResistenza = Integer.toString(player.getResistenza()) + "/" +  Integer.toString(player.getResistenzaIni());
                valueResistenza.setText(textResistenza);
            }
        });

        Button btnResistenzaMinus = findViewById(R.id.buttonResistenzaMinus);
        btnResistenzaMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.changeResistenza((-1));
                String textResistenza = Integer.toString(player.getResistenza()) + "/" +  Integer.toString(player.getResistenzaIni());
                valueResistenza.setText(textResistenza);
            }
        });

        Button btnFortunaPlus = findViewById(R.id.buttonFortunaPlus);
        btnFortunaPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.changeFortuna(1);
                String textFortuna = Integer.toString(player.getFortuna()) + "/" +  Integer.toString(player.getFortunaIni());
                valueFortuna.setText(textFortuna);
            }
        });

        Button btnFortunaMinus = findViewById(R.id.buttonFortunaMinus);
        btnFortunaMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.changeFortuna((-1));
                String textFortuna = Integer.toString(player.getFortuna()) + "/" +  Integer.toString(player.getFortunaIni());
                valueFortuna.setText(textFortuna);
            }
        });


        Button btnOroPlus = findViewById(R.id.buttonOroPlus);
        btnOroPlus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        Integer value = Integer.parseInt(userInput.getText().toString());
                                        player.changeOro(value);
                                        String textOro = Integer.toString(player.getOro());
                                        valueOro.setText(textOro);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });


        Button btnOroMinus = findViewById(R.id.buttonOroMinus);
        btnOroMinus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = promptsView
                        .findViewById(R.id.editTextDialogUserInput);



                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        Integer value = - Integer.parseInt(userInput.getText().toString());
                                        player.changeOro(value);
                                        String textOro = Integer.toString(player.getOro());
                                        valueOro.setText(textOro);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });


        Button btnProvvistePlus = findViewById(R.id.buttonProvvistePlus);
        btnProvvistePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.changeProvviste(1);
                String textProvviste = Integer.toString(player.getProvviste());
                valueProvviste.setText(textProvviste);
            }
        });

        Button btnProvvisteMinus = findViewById(R.id.buttonProvvisteMinus);
        btnProvvisteMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.changeProvviste(-1);
                String textProvviste = Integer.toString(player.getProvviste());
                valueProvviste.setText(textProvviste);
            }
        });


        Button btnTesoro = findViewById(R.id.buttonEditTesoro);
        btnTesoro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts_item, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = promptsView
                        .findViewById(R.id.editTextDialogUserInput);



                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        player.setTesoro(userInput.getText().toString());
                                        valueTesoro.setText(player.getTesoro());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });


        setaRecyclerView();
        listenersButtons();


        Button btnCombat = findViewById(R.id.buttonCombat);
        btnCombat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts_enemy, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInputAbilita = promptsView
                        .findViewById(R.id.inputAbilita);

                final EditText userInputResistenza = promptsView
                        .findViewById(R.id.inputResistenza);


                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        Integer valueAbilitaE = Integer.parseInt(userInputAbilita.getText().toString());
                                        Integer valueResistenzaE = Integer.parseInt(userInputResistenza.getText().toString());

                                        Enemy enemy = new Enemy(valueAbilitaE, valueResistenzaE);

                                        Intent intent = new Intent(MainActivity.this, CombatActivity.class);
                                        intent.putExtra("pl", player);
                                        intent.putExtra("en", enemy);
                                        startActivityForResult(intent, 2);

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setMessage("Insert monster values:");

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check which request we're responding to
        if (requestCode == 2) {

            if(data!=null){

                player = (Player) data.getSerializableExtra("PLAYER");

                String textAbilita = Integer.toString(player.getAbilita()) + "/" +  Integer.toString(player.getAbilitaIni());
                valueAbilita.setText(textAbilita);

                String textResistenza = Integer.toString(player.getResistenza()) + "/" +  Integer.toString(player.getResistenzaIni());
                valueResistenza.setText(textResistenza);

                String textFortuna = Integer.toString(player.getFortuna()) + "/" +  Integer.toString(player.getFortunaIni());
                valueFortuna.setText(textFortuna);

            }

        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("savePLAYER",player);
    }

    @Override
    protected void onStop() {
        super.onStop();
        writeJSON(player,"ffPLAYER");
    }


    public boolean writeJSON(Player c, String yourSettingName)
    {
        SharedPreferences mSettings = getSharedPreferences("ffPLAYER", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSettings.edit();

        GsonBuilder gsonb = new GsonBuilder();
        Gson mGson = gsonb.create();
        try {
            String writeValue = mGson.toJson(c);
            mEditor.putString(yourSettingName, writeValue);
            mEditor.commit();
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public Player readJSON(String yourSettingName)
    {
        SharedPreferences mSettings = getSharedPreferences("ffPLAYER", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSettings.edit();

        GsonBuilder gsonb = new GsonBuilder();
        Gson mGson = gsonb.create();
        String loadValue = mSettings.getString(yourSettingName, "");
        Player c = mGson.fromJson(loadValue, Player.class);
        return c;
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        player = (Player) savedInstanceState.getSerializable("savePLAYER");
    }

    public void setaRecyclerView(){

        //Aqui é instanciado o Recyclerview
        mRecyclerView = findViewById(R.id.listEquipa);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<Item> lista = player.getEquipa();

        adapter = new MyAdapter(this, lista, this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.newPlayer:
                player = new Player();

                valueAbilita = findViewById(R.id.valueAbilita);
                String textAbilita = Integer.toString(player.getAbilita()) + "/" +  Integer.toString(player.getAbilitaIni());
                valueAbilita.setText(textAbilita);

                valueResistenza = findViewById(R.id.valueResistenza);
                String textResistenza = Integer.toString(player.getResistenza()) + "/" +  Integer.toString(player.getResistenzaIni());
                valueResistenza.setText(textResistenza);

                valueFortuna = findViewById(R.id.valueFortuna);
                String textFortuna = Integer.toString(player.getFortuna()) + "/" +  Integer.toString(player.getFortunaIni());
                valueFortuna.setText(textFortuna);

                valueOro = findViewById(R.id.valueOro);
                String textOro = Integer.toString(player.getOro());
                valueOro.setText(textOro);

                valueTesoro = findViewById(R.id.valueTesoro);
                valueTesoro.setText(player.getTesoro());

                valueProvviste = findViewById(R.id.valueProvviste);
                String textProvviste = Integer.toString(player.getProvviste());
                valueProvviste.setText(textProvviste);

                setaRecyclerView();
                listenersButtons();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Aqui é o método onde trata o clique em um item da lista
     */
    @Override
    public void onCustomClick(Object object) {

        final Item item = (Item) object;

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts_confirm, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                player.getEquipa().remove(item);
                                adapter.notifyDataSetChanged();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        })
                .setMessage(item.getItem());

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    /**
     * Chama os listeners para os botões
     */
    public void listenersButtons() {

        Button btnEquipaAdd = findViewById(R.id.buttonEquipaAdd);
        btnEquipaAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts_item, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = promptsView
                        .findViewById(R.id.editTextDialogUserInput);


                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Cria um novo item
                                        Item item1 = new Item();

                                        // get user input and set it to result
                                        // edit text
                                        item1.setItem(userInput.getText().toString());

                                        //Adiciona a pessoa1 e avisa o adapter que o conteúdo
                                        //da lista foi alterado
                                        player.getEquipa().add(item1);
                                        adapter.notifyDataSetChanged();

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }

        });







    }





}

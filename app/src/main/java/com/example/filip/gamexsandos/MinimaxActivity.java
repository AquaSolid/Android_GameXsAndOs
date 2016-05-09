package com.example.filip.gamexsandos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

public class MinimaxActivity extends AppCompatActivity {

    private Board BoardGame;
    private MyGame myGame;

    private Button mBoardButtons[][];

    private TextView mInfoTextView;
    private TextView mPlayerOneCount;
    private TextView mTieCount;
    private TextView mPlayerTwoCount;
    private TextView mPlayerOneText;
    private TextView mPlayerTwoText;

    private int mPlayerOneCounter = 0;
    private int mTieCounter = 0;
    private int mPlayerTwoCounter = 0;

    private Button newGame, exitGame;

    public int HUMAN = 1;
    public int COMPUTER = 2;
    Random random;

    private int First=0;
    private int Counter = 0;
    private boolean isGameOver = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        mBoardButtons = new Button[3][3];
        mBoardButtons[0][0] = (Button) findViewById(R.id.one);
        mBoardButtons[0][1] = (Button) findViewById(R.id.two);
        mBoardButtons[0][2] = (Button) findViewById(R.id.three);
        mBoardButtons[1][0] = (Button) findViewById(R.id.four);
        mBoardButtons[1][1] = (Button) findViewById(R.id.five);
        mBoardButtons[1][2] = (Button) findViewById(R.id.six);
        mBoardButtons[2][0] = (Button) findViewById(R.id.seven);
        mBoardButtons[2][1] = (Button) findViewById(R.id.eight);
        mBoardButtons[2][2] = (Button) findViewById(R.id.nine);

        newGame = (Button) findViewById(R.id.newGame1);
        exitGame = (Button) findViewById(R.id.exitGame1);
        // Text Fields
        mInfoTextView = (TextView) findViewById(R.id.information);
        mPlayerOneCount = (TextView) findViewById(R.id.humanCount);
        mTieCount = (TextView) findViewById(R.id.tiesCount);
        mPlayerTwoCount = (TextView) findViewById(R.id.androidCount);
        mPlayerOneText = (TextView) findViewById(R.id.human);
        mPlayerTwoText = (TextView) findViewById(R.id.android);
        // Counters
        mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
        mTieCount.setText(Integer.toString(mTieCounter));
        mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));

        random = new Random();

        exitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MinimaxActivity.this.finish();
            }
        });

        final CharSequence[] items = {"Computer", "Player"};

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Who goes first?");
        alertDialog.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item] == "Computer") {
                    First = 1; // Computer
                } else if (items[item] == "Player") {
                    First = 2; // Player
                }
                dialog.dismiss();

                myGame = new MyGame(MinimaxActivity.this);

                if (First == 1) {
                    startNewGame(true); // True For Computer
                }
                if (First == 2) {
                    startNewGame(false); // False For Player
                }

            }
        });
        alertDialog.show();

        newGame.setOnClickListener(new View.OnClickListener() { // FIX STARTNEWGAME
            @Override
            public void onClick(View v) {
                if (Counter % 2 == 0) {
                    startNewGame(false);
                    Counter++;
                } else {
                    startNewGame(true);
                    Counter++;
                }
            }
        });

    }

    private void startNewGame(boolean GoesFirst) {

        MyResetBoard(); // Look at board reset

        mPlayerOneText.setText("Human:");
        mPlayerTwoText.setText("Android:");

        if(GoesFirst){
            // Computer Goes First
            mInfoTextView.setText("Android's Turn.");
            setMove(random.nextInt(3), random.nextInt(3), COMPUTER);
            GoesFirst = false;
        }else{
            //Player Goes First
            mInfoTextView.setText("Human's Turn.");
            GoesFirst = true;
        }
        isGameOver = false;
    }

    private void MyResetBoard(){
        myGame.resetBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mBoardButtons[i][j].setText("");
                mBoardButtons[i][j].setEnabled(true);
                mBoardButtons[i][j].setOnClickListener(new ButtonClickListener(i,j));
                mBoardButtons[i][j].setBackgroundResource(R.drawable.empty);
            }
        }
    }

    private class ButtonClickListener implements View.OnClickListener {

        int x,y;

        public ButtonClickListener(int i, int j) {
            this.x = i;
            this.y = j;
        }

        @Override
        public void onClick(View v) {
            if (!isGameOver){ // If the game is not over
                if (mBoardButtons[x][y].isEnabled()){
                    setMove(x, y, HUMAN); // Human makes a move CROSS


                    int winner = myGame.CheckGameState();

                    if (winner == myGame.PLAYING) { // If still playing
                        mInfoTextView.setText(R.string.turn_computer);
                        int[] result = myGame.move();
                        setMove(result[0], result[1], COMPUTER);
                        winner = myGame.CheckGameState();
                    }

                    winner = myGame.CheckGameState();

                    if (winner == myGame.PLAYING){
                        mInfoTextView.setText(R.string.turn_human);
                    }
                    else if (winner == myGame.DRAW) { // If draw
                        mInfoTextView.setText(R.string.result_tie);
                        mTieCounter++;
                        mTieCount.setText(Integer.toString(mTieCounter));
                        isGameOver = true;
                    } else if (winner == myGame.CROSS_WON) { // X Won
                        mInfoTextView.setText(R.string.result_human_wins);
                        mPlayerOneCounter++;
                        mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
                        isGameOver = true;
                    } else if (winner == myGame.NOUGHT_WON){ // O Won
                        mInfoTextView.setText(R.string.result_android_wins);
                        mPlayerTwoCounter++;
                        mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
                        isGameOver = true;
                    }


                }
            }
        }
    }

    public void setMove(int x, int y, int player){
        myGame.placeAMove(x, y, player);
        mBoardButtons[x][y].setEnabled(false);
        if (player == 1) {
            mBoardButtons[x][y].setBackgroundResource(R.drawable.x);
        } else {
            mBoardButtons[x][y].setBackgroundResource(R.drawable.o);
        }
    }

    //Unused methods
    public int ComputerFirst(){
        return 1;
    }
    public int PlayerFirst(){
        return 2;
    }
    public void showDialog() {
        // Uses a fragment.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentDialogBox dialog = new FragmentDialogBox();
        dialog.show(fragmentManager, "MyDialog");

    }
    public void showDialogHOME(String title, String msg, final Activity activity) {
        // Uses nothing.
        final CharSequence[] items = {"X", "O"};

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item] == "Computer") {
                    First = 1;
                    Toast.makeText(getApplication(), "Computer goes first.", Toast.LENGTH_SHORT).show();
                } else if (items[item] == "Player") {
                    First = 2;
                    Toast.makeText(getApplication(), "Player goes first.", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        alertDialog.show();
        //Dialog dialog = alertDialog.create();
        //return dialog;

    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayList mSelectedItems = new ArrayList();  // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the dialog title
        builder.setTitle("Pick Toppings")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(R.array.toppings, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    mSelectedItems.add(which);
                                } else if (mSelectedItems.contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    mSelectedItems.remove(Integer.valueOf(which));
                                }
                            }
                        })
                        // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }
}
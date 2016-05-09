package com.example.filip.gamexsandos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


// Created by filip on 20.09.2015.

public class PlayActivity extends AppCompatActivity {

    private TicTacToeGame mGame;

    private Button mBoardButtons[];

    private TextView mInfoTextView;
    private TextView mPlayerOneCount;
    private TextView mTieCount;
    private TextView mPlayerTwoCount;
    private TextView mPlayerOneText;
    private TextView mPlayerTwoText;

    private int mPlayerOneCounter = 0;
    private int mTieCounter = 0;
    private int mPlayerTwoCounter = 0;

    private boolean mPlayerOneFirst = true;
    private boolean mIsSinglePlayer = false;
    private boolean mIsPlayerOneTurn = true;
    private boolean mGameOver = false;

    private Button newGame, exitGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        final boolean mGameType = getIntent().getExtras().getBoolean("gameType");

        mBoardButtons = new Button[mGame.getBoardSize()];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);

        newGame = (Button) findViewById(R.id.newGame1);
        exitGame = (Button) findViewById(R.id.exitGame1);

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Boolean.toString(mGameType)+" ", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplication(), Boolean.toString(mGameType) + " ", Toast.LENGTH_SHORT).show();
                startNewGame(mGameType);
            }
        });

        exitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayActivity.this.finish();
            }
        });

        mInfoTextView = (TextView) findViewById(R.id.information);
        mPlayerOneCount = (TextView) findViewById(R.id.humanCount);
        mTieCount = (TextView) findViewById(R.id.tiesCount);
        mPlayerTwoCount = (TextView) findViewById(R.id.androidCount);
        mPlayerOneText = (TextView) findViewById(R.id.human);
        mPlayerTwoText = (TextView) findViewById(R.id.android);

        mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
        mTieCount.setText(Integer.toString(mTieCounter));
        mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));

        mGame = new TicTacToeGame();

        // System.out.println gets redirected to LogCat and printed using Log.i()
        // There is no console to send the messages to so the System.out.println messages get lost.
        // In the same way this happens when you run a "traditional" Java application with javaw.
        System.out.println("You win!");

        startNewGame(mGameType);

        //showDialogHOME("Who goes first?", null, null);
    }

    private void startNewGame(boolean isSingle) {

        this.mIsSinglePlayer = isSingle;

        mGame.clearBoard();

        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
            mBoardButtons[i].setBackgroundResource(R.drawable.empty);
        }

        if (mIsSinglePlayer) {
            mPlayerOneText.setText("Human:");
            mPlayerTwoText.setText("Android:");

            if (mPlayerOneFirst) {
                mInfoTextView.setText(R.string.first_human);
                mPlayerOneFirst = false;
            } else {
                mInfoTextView.setText(R.string.turn_computer);
                int move = mGame.getComputerMove();
                setMove(TicTacToeGame.PLAYER_TWO, move);
                mPlayerOneFirst = true;
            }
        } else {
            mPlayerOneText.setText("Player One:");
            mPlayerTwoText.setText("Player Two:");

            if (mPlayerOneFirst) {
                mInfoTextView.setText(R.string.turn_player_one);
                mPlayerOneFirst = false;
            } else {
                mInfoTextView.setText(R.string.turn_player_two);
                mPlayerOneFirst = true;
            }
        }

        mGameOver = false;
    }

    private class ButtonClickListener implements View.OnClickListener {

        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        public void onClick(View view) {
            if (!mGameOver) {
                if (mBoardButtons[location].isEnabled()) {
                    if (mIsSinglePlayer) {

                        setMove(TicTacToeGame.PLAYER_ONE, location);

                        int winner = mGame.checkForWinner();

                        if (winner == 0) {
                            mInfoTextView.setText(R.string.turn_computer);
                            int move = mGame.getComputerMove();
                            setMove(TicTacToeGame.PLAYER_TWO, move);
                            winner = mGame.checkForWinner();
                        }

                        if (winner == 0)
                            mInfoTextView.setText(R.string.turn_human);
                        else if (winner == 1) {
                            mInfoTextView.setText(R.string.result_tie);
                            mTieCounter++;
                            mTieCount.setText(Integer.toString(mTieCounter));
                            mGameOver = true;
                        } else if (winner == 2) {
                            mInfoTextView.setText(R.string.result_human_wins);
                            mPlayerOneCounter++;
                            mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
                            mGameOver = true;
                        } else {
                            mInfoTextView.setText(R.string.result_android_wins);
                            mPlayerTwoCounter++;
                            mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
                            mGameOver = true;
                        }
                    } else {
                        //Toast.makeText(getApplication(), "we are here in ELSE", Toast.LENGTH_SHORT).show();
                        if (mIsPlayerOneTurn) {
                            setMove(TicTacToeGame.PLAYER_ONE, location);
                            //Toast.makeText(getApplication(), "Player One Clicked on " + Integer.toString(location) + " ", Toast.LENGTH_SHORT).show();
                        } else {
                            setMove(TicTacToeGame.PLAYER_TWO, location);
                            //Toast.makeText(getApplication(), "Player Two Clicked on " + Integer.toString(location) + " ", Toast.LENGTH_SHORT).show();
                        }
                        int winner = mGame.checkForWinner();

                        if (winner == 0) {
                            if (mIsPlayerOneTurn) {
                                mInfoTextView.setText(R.string.turn_player_two);
                                mIsPlayerOneTurn = false;
                            } else {
                                mInfoTextView.setText(R.string.turn_player_one);
                                mIsPlayerOneTurn = true;
                            }
                        } else if (winner == 1) {
                            mInfoTextView.setText(R.string.result_tie);
                            mTieCounter++;
                            mTieCount.setText(Integer.toString(mTieCounter));
                            mGameOver = true;
                        } else if (winner == 2) {
                            mInfoTextView.setText(R.string.result_player_one_wins);
                            mPlayerOneCounter++;
                            mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
                            mGameOver = true;
                            mIsPlayerOneTurn = false;
                        } else {
                            mInfoTextView.setText(R.string.result_player_two_wins);
                            mPlayerTwoCounter++;
                            mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
                            mGameOver = true;
                            mIsPlayerOneTurn = true;
                        }

                    }
                }
            }
        }
    }

    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        //mBoardButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.PLAYER_ONE) {
            //    mBoardButtons[location].setTextColor(Color.GREEN);
            mBoardButtons[location].setBackgroundResource(R.drawable.x);
        } else {
            //   mBoardButtons[location].setTextColor(Color.RED);
            mBoardButtons[location].setBackgroundResource(R.drawable.o);
        }
    }

    public void showDialog() {
        // Uses a fragment.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentDialogBox dialog = new FragmentDialogBox();
        dialog.show(fragmentManager, "MyDialog");

    }

    public Dialog showDialogHOME(String title, String msg, final Activity activity) {
        // Uses nothing.
        final CharSequence[] items = {"X", "O"};

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                switch (item) {
                    case 0:
                        Toast.makeText(getApplication(), "Player X goes first.", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplication(), "Player O goes first.", Toast.LENGTH_SHORT).show();
                        break;
                }
                dialog.dismiss();
            }
        });
        alertDialog.show();
        Dialog dialog = alertDialog.create();
        return dialog;

    }
}


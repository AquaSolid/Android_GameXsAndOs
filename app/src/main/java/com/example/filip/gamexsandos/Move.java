package com.example.filip.gamexsandos;

/**
 * Created by filip on 22.11.2015.
 */
public class Move {
    public static int x, y, score, player;

    public Move(){}

    public Move(int score){
        this.score = score;
    }

    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Move(int x, int y, int player) {
        this.x = x;
        this.y = y;
        this.player = player;
    }


}
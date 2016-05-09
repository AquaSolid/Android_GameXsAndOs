package com.example.filip.gamexsandos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button OnePlayerButton, TwoPlayerButton, scoreBoardsBuuton, goHelp, exitButton, minimaxButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OnePlayerButton = (Button) findViewById(R.id.play_button);
        TwoPlayerButton = (Button) findViewById(R.id.Twoplay_button);
        //scoreBoardsBuuton = (Button) findViewById(R.id.score_button2);
        goHelp = (Button) findViewById(R.id.help_button3);
        exitButton = (Button) findViewById(R.id.exit_button);
        minimaxButton = (Button) findViewById(R.id.minimax_button);

        OnePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG", "One Player Button Pressed!");
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra("gameType", true);
                startActivityForResult(intent, 0);
            }
        });
        TwoPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG", "Two Player Button Pressed!");
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra("gameType", false);
                startActivityForResult(intent, 0);
            }
        });
        /*
        scoreBoardsBuuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LanguageActivity.class);
                startActivity(intent);
            }
        });
        */
        goHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
        minimaxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MinimaxActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

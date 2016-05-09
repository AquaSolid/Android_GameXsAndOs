package com.example.filip.gamexsandos;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import java.util.Locale;

/**
 * Created by filip on 20.09.2015.
 */
public class LanguageActivity extends AppCompatActivity implements View.OnClickListener {


    Button changeLanguage;
    RadioButton en, fr, mk, du, sr;

    public String languageToLoad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        changeLanguage = (Button) findViewById(R.id.change_language_button);
        en = (RadioButton) findViewById(R.id.radioEnglish);
        fr = (RadioButton) findViewById(R.id.radioFrench);
        mk = (RadioButton) findViewById(R.id.radioMak);
        du = (RadioButton) findViewById(R.id.radioDutch);
        sr = (RadioButton) findViewById(R.id.radioSerb);

        changeLanguage.setOnClickListener(this);
        en.setOnClickListener(this);
        fr.setOnClickListener(this);
        mk.setOnClickListener(this);
        sr.setOnClickListener(this);
        du.setOnClickListener(this);


    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_language_button:
                //String languageToLoad  = "fa"; // your language
                languageToLoad = "mk";
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_language);
                Intent refresh = new Intent(this, LanguageActivity.class);
                startActivity(refresh);
                finish();
                break;

            case R.id.radioEnglish:
                languageToLoad = "en";
                break;

            case R.id.radioMak:
                languageToLoad = "mk";
                break;

            default:
                break;
        }

    }
}

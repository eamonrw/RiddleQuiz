package com.weingoldeamon.riddlequiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Toolbar toolb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolb = findViewById(R.id.toolbar);
        setSupportActionBar(toolb);
    }

    public void beginGame(View view) {
        Intent game = new Intent(this, GameActivity.class);
        startActivity(game);
    }

    public void openSettings(View view) {
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }

    public void quitApp(View view) {
        finishAndRemoveTask();
    }
}

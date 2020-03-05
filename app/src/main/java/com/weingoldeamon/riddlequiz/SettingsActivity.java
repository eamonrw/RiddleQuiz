package com.weingoldeamon.riddlequiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolb;
    Spinner colorSpinner;
    SeekBar timerBar;
    SharedPreferences pref;
    int[] colorThemes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        colorThemes = new int[]{R.style.BlueTheme, R.style.DarkTheme};
        pref = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        toolb = findViewById(R.id.toolbar);
        setSupportActionBar(toolb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        colorSpinner = findViewById(R.id.color_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.color_themes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.dark_dropdown);
        colorSpinner.setAdapter(adapter);
        timerBar = findViewById(R.id.seekBar);
        timerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SharedPreferences.Editor edit = pref.edit();
                edit.putInt("Timer Length", progress+30);
                edit.commit();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                setTheme(colorThemes[pos]);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}

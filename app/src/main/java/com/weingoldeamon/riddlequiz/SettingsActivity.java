package com.weingoldeamon.riddlequiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.transition.Fade;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.google.gson.Gson;

public class SettingsActivity extends AppCompatActivity {

    Gson gson;
    CurrentSettings cs;
    Toolbar toolb;
    Spinner colorSpinner;
    SeekBar timerBar, qBar;
    SharedPreferences pref;
    int[] colorThemes = new int[]{R.style.BlueTheme, R.style.DarkTheme, R.style.RedTheme, R.style.GreenTheme};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final long cur_time = SystemClock.uptimeMillis();
        pref = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        setTheme(colorThemes[pref.getInt("Theme", 0)]);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        gson = new Gson();
        cs = gson.fromJson(pref.getString("Current Settings", "{\"timer_length\":60,\"num_questions\":6}"), CurrentSettings.class);
        toolb = findViewById(R.id.toolbar);
        setSupportActionBar(toolb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        colorSpinner = findViewById(R.id.color_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.color_themes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);
        timerBar = findViewById(R.id.seekBar);
        timerBar.setProgress(cs.getTimer_length() - 30);
        timerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cs.setTimer_length(progress + 30);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        qBar = findViewById(R.id.seekBar2);
        qBar.setProgress(cs.getNum_questions()-6);
        qBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cs.setNum_questions(progress+6);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        colorSpinner.setSelection(pref.getInt("Theme", 0));
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if(pos != pref.getInt("Theme", 0) && SystemClock.uptimeMillis() - cur_time > 300) {
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putInt("Theme", pos);
                    edit.apply();
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onPause() {
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("Current Settings", gson.toJson(cs));
        edit.apply();
        super.onPause();
    }
}

class CurrentSettings {
    private int timer_length = 60;
    private int num_questions = 6;
    public CurrentSettings() {

    }

    public int getNum_questions() {
        return num_questions;
    }

    public void setNum_questions(int num_questions) {
        this.num_questions = num_questions;
    }

    public int getTimer_length() {
        return timer_length;
    }

    public void setTimer_length(int timer_length) {
        this.timer_length = timer_length;
    }
}
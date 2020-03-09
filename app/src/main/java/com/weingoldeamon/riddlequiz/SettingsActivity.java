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

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolb;
    Spinner colorSpinner;
    SeekBar timerBar;
    SharedPreferences pref;
    int[] colorThemes = new int[]{R.style.BlueTheme, R.style.DarkTheme};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final long cur_time = SystemClock.uptimeMillis();
        pref = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        setTheme(colorThemes[pref.getInt("Theme", 0)]);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolb = findViewById(R.id.toolbar);
        setSupportActionBar(toolb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        colorSpinner = findViewById(R.id.color_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.color_themes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);
        timerBar = findViewById(R.id.seekBar);
        timerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SharedPreferences.Editor edit = pref.edit();
                edit.putInt("Timer Length", progress+30);
                edit.apply();
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
                if(pos != pref.getInt("Theme", 0) && SystemClock.uptimeMillis() - cur_time > 300) {
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putInt("Theme", pos);
                    edit.apply();
                    new AlertDialog.Builder(SettingsActivity.this, R.style.AlertDialogCustom)
                    .setMessage("Restart App to Change Theme?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .show();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
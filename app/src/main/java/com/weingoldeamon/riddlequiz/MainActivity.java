package com.weingoldeamon.riddlequiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Toolbar toolb;
    SharedPreferences pref;
    int[] colorThemes = new int[]{R.style.BlueTheme, R.style.DarkTheme, R.style.RedTheme, R.style.GreenTheme};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pref = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        setTheme(colorThemes[pref.getInt("Theme", 0)]);

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
        QuitDialog qd = new QuitDialog(this, MainActivity.this, "Are you sure you would like to quit?");
        qd.displayFragment();
    }

}

class QuitDialog extends DialogFragment {

    private AlertDialog.Builder db;
    AppCompatActivity mActivity;
    public QuitDialog(AppCompatActivity activity, Context context, String message) {
        mActivity = activity;
        db = new AlertDialog.Builder(context, R.style.AlertDialogCustom)
        .setMessage(message)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mActivity.finishAndRemoveTask();
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
    }
    public void displayFragment() {
        db.show();
    }

}

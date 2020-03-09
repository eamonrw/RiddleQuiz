package com.weingoldeamon.riddlequiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class GameActivity extends AppCompatActivity {

    Toolbar toolb;
    TextView questionText;
    EditText answerField;
    int curQ = 0, numQ = -1;
    int quizTime = 60;
    String[] qArray;
    Resources res;
    Button submitButton;
    CountDownTimer quizTimer;
    ProgressBar timerBar;
    SharedPreferences pref;
    int[] colorThemes = new int[]{R.style.BlueTheme, R.style.DarkTheme};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pref = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        setTheme(colorThemes[pref.getInt("Theme", 0)]);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        toolb = findViewById(R.id.toolbar);
        setSupportActionBar(toolb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        res = getResources();
        questionText = findViewById(R.id.question_box);
        answerField = findViewById(R.id.answer_field);
        submitButton = findViewById(R.id.submit_button);
        qArray = res.getStringArray(R.array.questions);
        timerBar = findViewById(R.id.progressBar);
        numQ = qArray.length;

        questionText.setText(qArray[curQ].substring(0, qArray[curQ].indexOf('?')+1));
        quizTimer = new CountDownTimer(quizTime*1000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerBar.setProgress((int)(millisUntilFinished));
            }
            @Override
            public void onFinish() {
                questionText.setText("Time's up!");
                submitButton.setClickable(false);
                timerBar.setProgress(0);
            }
        }.start();
    }

    public void submitAnswer(View view) {
        if(curQ < numQ) {
            String answer = answerField.getText().toString();
            if(answer.toLowerCase().contains(qArray[curQ].substring(qArray[curQ].indexOf('?')+1))) {
                curQ++;
                answerField.setText("");
                if(curQ < numQ)
                    questionText.setText(qArray[curQ].substring(0, qArray[curQ].indexOf('?')+1));
                else {
                    questionText.setText(res.getString(R.string.finished_text));
                    submitButton.setClickable(false);
                    quizTimer.cancel();
                }
            }
        }
    }

    public void resetQuiz(View view) {
        curQ = 0;
        questionText.setText(qArray[curQ].substring(0, qArray[curQ].indexOf('?')+1));
        submitButton.setClickable(true);
        quizTimer.start();
    }
}
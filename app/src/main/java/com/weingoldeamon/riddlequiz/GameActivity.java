package com.weingoldeamon.riddlequiz;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class GameActivity extends AppCompatActivity {

    Toolbar toolb;
    TextView questionText, timerText;
    EditText answerField;
    int curQ = 0, numQ = -1;
    int quizTime = 60;
    String[] qArray;
    Resources res;
    Button submitButton;
    CountDownTimer quizTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        toolb = findViewById(R.id.toolbar);
        setSupportActionBar(toolb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        res = getResources();
        questionText = findViewById(R.id.question_box);
        timerText = findViewById(R.id.timer_box);
        answerField = findViewById(R.id.answer_field);
        submitButton = findViewById(R.id.submit_button);
        qArray = res.getStringArray(R.array.questions);
        numQ = qArray.length;

        questionText.setText(qArray[curQ].substring(0, qArray[curQ].indexOf('?')+1));
        quizTimer = new CountDownTimer(quizTime*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText("Time Left: " + millisUntilFinished / 1000);
            }
            @Override
            public void onFinish() {
                questionText.setText("Time's up!");
                submitButton.setClickable(false);
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

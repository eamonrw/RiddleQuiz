package com.weingoldeamon.riddlequiz;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

public class GameActivity extends Activity {

    TextView questionText, timerText;
    EditText answerField;
    int currentQuestion = 0;
    int quizTime = 60;
    String[] questionArray, answerArray;
    Resources res;
    Button submitButton;
    CountDownTimer quizTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        res = getResources();
        questionText = findViewById(R.id.question_box);
        timerText = findViewById(R.id.timer_box);
        answerField = findViewById(R.id.answer_field);
        submitButton = findViewById(R.id.submit_button);
        questionArray = res.getStringArray(R.array.questions);
        answerArray = res.getStringArray(R.array.answers);

        questionText.setText(questionArray[currentQuestion]);
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
        if(currentQuestion<5) {
            String answer = answerField.getText().toString();
            if(answer.toLowerCase().contains(answerArray[currentQuestion])) {
                currentQuestion++;
                answerField.setText("");
                if(currentQuestion<5)
                    questionText.setText(questionArray[currentQuestion]);
                else {
                    questionText.setText(res.getString(R.string.finished_text));
                    submitButton.setClickable(false);
                    quizTimer.cancel();
                }
            }
        }
    }

    public void resetQuiz(View view) {
        currentQuestion = 0;
        questionText.setText(questionArray[currentQuestion]);
        submitButton.setClickable(true);
        quizTimer.start();
    }

    public void goBack(View view) {
        Intent game = new Intent(this, MainActivity.class);
        startActivity(game);
    }
}

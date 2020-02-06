package com.weingoldeamon.riddlequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    TextView questionText;
    EditText answerField;
    int currentQuestion = 0;
    String[] questionArray, answerArray;
    Resources res;
    Button submitButton, startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = getResources();
        questionText = findViewById(R.id.question_box);
        answerField = findViewById(R.id.answer_field);
        submitButton = findViewById(R.id.submit_button);
        startButton = findViewById(R.id.start_button);
        questionArray = res.getStringArray(R.array.questions);
        answerArray = res.getStringArray(R.array.answers);
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
                }
            }
        }
    }

    public void startQuiz(View view) {
        questionText.setText(questionArray[currentQuestion]);
        view.setClickable(false);
    }

    public void resetQuiz(View view) {
        submitButton.setClickable(true);
        startButton.setClickable(true);
        currentQuestion = 0;
        questionText.setText(res.getString(R.string.begin_text));
    }
}

package com.mellobit.garry.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;
    private ImageButton previousButton;
    private ImageButton nextButton;
    private TextView questionTextView;

    private Question[] questionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        trueButton = (Button) findViewById(R.id.true_button);
        falseButton = (Button) findViewById(R.id.false_button);

        previousButton = (ImageButton) findViewById(R.id.previous_button);
        nextButton = (ImageButton) findViewById(R.id.next_button);

        questionTextView = (TextView) findViewById(R.id.question_text_view);

        final int questionTextResId = questionBank[currentIndex].getTextResId();
        questionTextView.setText(questionTextResId);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousQuestion();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });
    }

    private void previousQuestion() {
        if (currentIndex > 0) {
            currentIndex--;
        } else {
            currentIndex = questionBank.length-1;
        }

        int questionTextResId = questionBank[currentIndex].getTextResId();
        questionTextView.setText(questionTextResId);
    }

    private void nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.length;
        int questionTextResId = questionBank[currentIndex].getTextResId();
        questionTextView.setText(questionTextResId);
    }

    private void checkAnswer(boolean userPressedTrue) {
        if(userPressedTrue == questionBank[currentIndex].isAnswerTrue()) {
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }
}
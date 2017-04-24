package com.mellobit.garry.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String LOG_TAG = QuizActivity.class.getSimpleName();
    private static final String KEY_INDEX = "index";
    private static final String KEY_SCORE = "score";
    private static final String KEY_QUESTIONS_ANSWERED = "questions_answered";
    private static final String KEY_QUESTIONS_ANSWERED_INDEX = "questions_answered_index";

    private Button trueButton;
    private Button falseButton;
    private Button cheatButton;
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
    private int questionsAnswered = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null) {
            this.currentIndex = savedInstanceState.getInt(KEY_INDEX);
            this.score = savedInstanceState.getInt(KEY_SCORE);
            this.questionsAnswered = savedInstanceState.getInt(KEY_QUESTIONS_ANSWERED);

            boolean[] questionAnsweredIndex = savedInstanceState.getBooleanArray(KEY_QUESTIONS_ANSWERED_INDEX);
            for(int i=0; i<questionAnsweredIndex.length; i++) {
                if (questionAnsweredIndex[i]){
                    questionBank[i].setAnswered(true);
                } else {
                    questionBank[i].setAnswered(false);
                }
            }
        }

        trueButton = (Button) findViewById(R.id.true_button);
        falseButton = (Button) findViewById(R.id.false_button);
        cheatButton = (Button) findViewById(R.id.cheat_button);

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

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                startActivity(intent);
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

        checkQuestionAnswered();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        boolean[] questionsAnsweredIndex = new boolean[questionBank.length];

        for (int i =0; i < questionBank.length; i++) {
            if(questionBank[i].isAnswered()){
                questionsAnsweredIndex[i] = true;
            } else {
                questionsAnsweredIndex[i] = false;
            }
        }

        outState.putBooleanArray(KEY_QUESTIONS_ANSWERED_INDEX, questionsAnsweredIndex);
        outState.putInt(KEY_INDEX, currentIndex);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_QUESTIONS_ANSWERED, questionsAnswered);

    }

    private void previousQuestion() {
        if (currentIndex > 0) {
            currentIndex--;
        } else {
            currentIndex = questionBank.length-1;
        }

        int questionTextResId = questionBank[currentIndex].getTextResId();
        questionTextView.setText(questionTextResId);
        checkQuestionAnswered();
    }

    private void nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.length;
        int questionTextResId = questionBank[currentIndex].getTextResId();
        questionTextView.setText(questionTextResId);
        checkQuestionAnswered();


    }

    private void checkAnswer(boolean userPressedTrue) {
        if(userPressedTrue == questionBank[currentIndex].isAnswerTrue()) {
            score++;
        }

        questionBank[currentIndex].setAnswered(true);
        questionsAnswered ++;
        checkQuestionAnswered();
        checkisFinished();
    }

    private void checkQuestionAnswered() {
        if(questionBank[currentIndex].isAnswered()) {
            trueButton.setEnabled(false);
            falseButton.setEnabled(false);
        } else {
            trueButton.setEnabled(true);
            falseButton.setEnabled(true);
        }
    }

    private void checkisFinished() {
        if (questionsAnswered == questionBank.length) {
            showScore();
        }
    }

    private void showScore() {
        float percentCorrect = ((float)score)/((float)questionBank.length) * 100;
        String scoreText = getString(R.string.score_label, percentCorrect);
        Toast.makeText(this, scoreText, Toast.LENGTH_SHORT).show();
    }
}
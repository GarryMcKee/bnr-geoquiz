package com.mellobit.garry.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.mellobit.garry.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.mellobit.garry.geoquiz.answer_shown";
    private static final String KEY_ANSWER_WAS_SHOWN = "answer_was_shown";
    private static final String KEY_ANSWER_IS_TRUE = "answer_is_true";

    private boolean answerIsTrue;
    private boolean answerWasShown;

    private TextView answerTextView;
    private Button showAnswerButton;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        answerIsTrue = getIntent().getExtras().getBoolean(EXTRA_ANSWER_IS_TRUE, false);

        answerTextView = (TextView) findViewById(R.id.answer_text_view);
        showAnswerButton = (Button)findViewById(R.id.show_answer_button);

        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerIsTrue) {
                    answerTextView.setText(R.string.true_button);
                } else {
                    answerTextView.setText(R.string.false_button);
                }

                answerWasShown = true;
                setAnswerShown(answerWasShown);
            }
        });

        if(savedInstanceState != null) {
            answerWasShown = savedInstanceState.getBoolean(KEY_ANSWER_WAS_SHOWN);
            answerIsTrue = savedInstanceState.getBoolean(KEY_ANSWER_IS_TRUE);
            setAnswerShown(answerWasShown);

            if(answerWasShown) {
                if(answerIsTrue) {
                    answerTextView.setText(R.string.true_button);
                } else {
                    answerTextView.setText(R.string.false_button);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_ANSWER_WAS_SHOWN, answerWasShown);
        outState.putBoolean(KEY_ANSWER_IS_TRUE, answerIsTrue);
    }

    private void setAnswerShown(boolean answerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, answerShown);
        setResult(RESULT_OK, data);
    }
}

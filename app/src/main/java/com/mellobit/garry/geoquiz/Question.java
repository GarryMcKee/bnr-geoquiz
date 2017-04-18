package com.mellobit.garry.geoquiz;

/**
 * Created by Garry on 18/04/2017.
 */

public class Question {

    private int textResId;
    private boolean answerTrue;

    public Question(int textResId, boolean answerTrue) {
        this.setTextResId(textResId);
        this.setAnswerTrue(answerTrue);
    }

    public int getTextResId() {
        return textResId;
    }

    public void setTextResId(int textResId) {
        this.textResId = textResId;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }
}

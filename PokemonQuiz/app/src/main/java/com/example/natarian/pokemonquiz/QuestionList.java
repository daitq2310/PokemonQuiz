package com.example.natarian.pokemonquiz;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Natarian on 3/10/17.
 */

public class QuestionList {

    @SerializedName("question")
    private List<Question> questionList;

    public QuestionList() {
    }

    public QuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}

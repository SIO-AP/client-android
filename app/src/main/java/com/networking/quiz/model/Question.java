package com.networking.quiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Question implements Parcelable {
    private String descriptionQuestion;
    private ArrayList<Answer> answers;
    private int difficulty;

    public Question(String descriptionQuestion, ArrayList<Answer> answers) {
        this.descriptionQuestion = descriptionQuestion;
        this.answers = answers;
    }
    public Question(String descriptionQuestion, ArrayList<Answer> answers, int difficulty) {
        this.descriptionQuestion = descriptionQuestion;
        this.answers = answers;
        this.difficulty = difficulty;
    }


    protected Question(Parcel in) {
        descriptionQuestion = in.readString();
        answers = in.createTypedArrayList(Answer.CREATOR);
        difficulty = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getDescriptionQuestion() {
        return descriptionQuestion;
    }

    public void setDescriptionQuestion(String descriptionQuestion) {
        this.descriptionQuestion = descriptionQuestion;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(descriptionQuestion);
        parcel.writeTypedList(answers);
        parcel.writeInt(difficulty);
    }
}

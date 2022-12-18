package com.networking.quiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.networking.quiz.controller.Controller;

import java.util.ArrayList;

public class QuizGame implements Parcelable {
//	private String playerName;
//    private int playerScore;

    private Player myPlayer;

    private ArrayList<Question> questions;
    private ArrayList<Integer> listeIdQuestions;



    public QuizGame(Player player, ArrayList<Question> questions) {
        this.myPlayer = player;
        this.questions = questions;
    }


    protected QuizGame(Parcel in) {
        myPlayer = in.readParcelable(Player.class.getClassLoader());
        questions = in.createTypedArrayList(Question.CREATOR);

    }

    public static final Creator<QuizGame> CREATOR = new Creator<QuizGame>() {
        @Override
        public QuizGame createFromParcel(Parcel in) {
            return new QuizGame(in);
        }

        @Override
        public QuizGame[] newArray(int size) {
            return new QuizGame[size];
        }
    };


    public Player getMyPlayer() {
        return myPlayer;
    }

    public void setMyPlayer(Player myPlayer) {
        this.myPlayer = myPlayer;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public Boolean isCorrectThisAnswer(Question question, int idAnswer) {
        if (question.getAnswers().get(idAnswer).getIsCorrect()) {
            this.myPlayer.setMyScore(this.myPlayer.getMyScore() + 10);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(myPlayer, i);
        parcel.writeTypedList(questions);

    }
}
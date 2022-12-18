package com.networking.quiz.controller;

import android.os.Parcel;
import android.os.Parcelable;

import com.networking.quiz.model.QuizGame;

public class Controller implements Parcelable {

    private QuizGame laGame;


    public Controller() {
       this.laGame = null;

    }


    protected Controller(Parcel in) {
        laGame = in.readParcelable(QuizGame.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(laGame, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Controller> CREATOR = new Creator<Controller>() {
        @Override
        public Controller createFromParcel(Parcel in) {
            return new Controller(in);
        }

        @Override
        public Controller[] newArray(int size) {
            return new Controller[size];
        }
    };


    public QuizGame getLaGame() {
        return laGame;
    }
    public void setLaGame(QuizGame laGame) {
        this.laGame = laGame;
    }

}
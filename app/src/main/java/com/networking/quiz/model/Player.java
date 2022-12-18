package com.networking.quiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.networking.quiz.controller.Controller;

public class Player implements Parcelable {


    private String myName;
    private int myScore;


    public Player(String aName, int aScore) {

        this.myName = aName;
        this.myScore = aScore;
    }


    protected Player(Parcel in) {
        myName = in.readString();
        myScore = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(myName);
        dest.writeInt(myScore);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public int getMyScore() {
        return myScore;
    }

    public void setMyScore(int myScore) {
        this.myScore = myScore;
    }



}
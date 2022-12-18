package com.networking.quiz.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer  implements Parcelable {
    String codeAnswer;
    String descriptionAnswer;
    boolean isCorrect;

    public Answer(String codeAnswer, String descriptionAnswer, boolean isCorrect) {
        this.codeAnswer = codeAnswer;
        this.descriptionAnswer = descriptionAnswer;
        this.isCorrect = isCorrect;
    }


    protected Answer(Parcel in) {
        codeAnswer = in.readString();
        descriptionAnswer = in.readString();
        isCorrect = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(codeAnswer);
        dest.writeString(descriptionAnswer);
        dest.writeByte((byte) (isCorrect ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

    public String getCodeAnswer() {
        return codeAnswer;
    }

    public void setCodeAnswer(String codeAnswer) {
        this.codeAnswer = codeAnswer;
    }

    public String getDescriptionAnswer() {
        return descriptionAnswer;
    }

    public void setDescriptionAnswer(String descriptionAnswer) {
        this.descriptionAnswer = descriptionAnswer;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

}
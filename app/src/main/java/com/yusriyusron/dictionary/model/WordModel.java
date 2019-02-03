package com.yusriyusron.dictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

public class WordModel implements Parcelable {
    private String word;
    private String translation;

    public WordModel() {
    }

    public WordModel(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(word);
        parcel.writeString(translation);
    }

    protected WordModel(Parcel in) {
        word = in.readString();
        translation = in.readString();
    }

    public static final Creator<WordModel> CREATOR = new Creator<WordModel>() {
        @Override
        public WordModel createFromParcel(Parcel in) {
            return new WordModel(in);
        }

        @Override
        public WordModel[] newArray(int size) {
            return new WordModel[size];
        }
    };
}

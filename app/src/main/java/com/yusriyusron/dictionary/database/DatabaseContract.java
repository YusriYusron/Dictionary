package com.yusriyusron.dictionary.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_EN = "english";
    public static String TABLE_ID = "bahasa";
    static final class WordColumns implements BaseColumns{
        static String WORD = "word";
        static String TRANSLATION = "translation";
    }
}

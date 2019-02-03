package com.yusriyusron.dictionary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yusriyusron.dictionary.database.DatabaseContract.WordColumns;
import static com.yusriyusron.dictionary.database.DatabaseContract.TABLE_EN;
import static com.yusriyusron.dictionary.database.DatabaseContract.TABLE_ID;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbdictionary";
    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_DICTIONARY_EN = "CREATE TABLE "+TABLE_EN+
            " ("+WordColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            WordColumns.WORD+" TEXT NOT NULL, "+
            WordColumns.TRANSLATION+" TEXT NOT NULL);";

    public static String CREATE_TABLE_DICTIONARY_ID = "CREATE TABLE "+TABLE_ID+
            " ("+WordColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            WordColumns.WORD+" TEXT NOT NULL, "+
            WordColumns.TRANSLATION+" TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_DICTIONARY_EN);
        sqLiteDatabase.execSQL(CREATE_TABLE_DICTIONARY_ID);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_EN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_ID);
        onCreate(sqLiteDatabase);
    }
}

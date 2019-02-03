package com.yusriyusron.dictionary.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.yusriyusron.dictionary.model.WordModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.yusriyusron.dictionary.database.DatabaseContract.WordColumns.TRANSLATION;
import static com.yusriyusron.dictionary.database.DatabaseContract.WordColumns.WORD;

public class DictionaryHelper {

    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public DictionaryHelper(Context context) {
        this.context = context;
    }

    public DictionaryHelper open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<WordModel> query(String tableName, String filter){
        ArrayList<WordModel> arrayList = new ArrayList<>();
        Cursor cursor;

        if (!filter.equalsIgnoreCase("")){
            cursor = database.query(tableName,null,WORD+" LIKE ?", new String[]{filter+"%"},
                    null,null,_ID+" ASC",null);
        }else {
            cursor = database.query(tableName,null,null,null,null,null,WORD+" ASC", null);
        }
        cursor.moveToFirst();
        WordModel wordModel;
        if (cursor.getCount()>0){
            do {
                wordModel = new WordModel();
                wordModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                wordModel.setTranslation(cursor.getString(cursor.getColumnIndexOrThrow(TRANSLATION)));
                arrayList.add(wordModel);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSucces(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransaction(String tableName, WordModel wordModel){
        String sql = "INSERT INTO "+tableName+" ("+WORD+", "+TRANSLATION+") VALUES (?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1,wordModel.getWord());
        statement.bindString(2,wordModel.getTranslation());
        statement.execute();
        statement.clearBindings();
    }
}

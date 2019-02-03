package com.yusriyusron.dictionary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.yusriyusron.dictionary.database.DatabaseContract;
import com.yusriyusron.dictionary.database.DictionaryHelper;
import com.yusriyusron.dictionary.model.WordModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void>{
        final String TAG = LoadData.class.getSimpleName();
        DictionaryHelper dictionaryHelper;
        double progress;
        double maxProgress = 100;

        @Override
        protected void onPreExecute() {
            dictionaryHelper = new DictionaryHelper(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences preferences = getSharedPreferences("LOADER", Context.MODE_PRIVATE);
            Boolean firstRun = preferences.getBoolean("LOADED", true);

            if (firstRun) {

                ArrayList<WordModel> englishWords = preLoadRaw(R.raw.english_indonesia);
                ArrayList<WordModel> indonesiaWords = preLoadRaw(R.raw.indonesia_english);

                progress = 10;
                publishProgress((int) progress);
                Double progressMaxInsert = 100.0;
                Double progressDiff = (progressMaxInsert - progress) / (englishWords.size() + indonesiaWords.size());

                dictionaryHelper.open();

                dictionaryHelper.beginTransaction();
                try {
                    for (WordModel model : englishWords) {
                        dictionaryHelper.insertTransaction(DatabaseContract.TABLE_EN,model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }

                    for (WordModel model : indonesiaWords) {
                        dictionaryHelper.insertTransaction(DatabaseContract.TABLE_ID,model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    // Jika semua proses telah di set success maka akan di commit ke database
                    dictionaryHelper.setTransactionSucces();
                } catch (Exception e) {
                    // Jika gagal maka do nothing
                    Log.e(TAG, "doInBackground: Exception");
                }
                dictionaryHelper.endTransaction();
                dictionaryHelper.close();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("LOADED", false);
                editor.apply();

                publishProgress((int) maxProgress);

            } else {
                try {
                    synchronized (this) {
                        this.wait(1000);

                        publishProgress(50);

                        this.wait(1000);
                        publishProgress((int) maxProgress);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(MainActivity.this,DictionaryActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public ArrayList<WordModel> preLoadRaw(int fileName){
        ArrayList<WordModel> mahasiswaModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(fileName);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitString = line.split("\t");

                WordModel mahasiswaModel;

                mahasiswaModel = new WordModel(splitString[0], splitString[1]);
                mahasiswaModels.add(mahasiswaModel);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mahasiswaModels;
    }
}

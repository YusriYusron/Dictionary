package com.yusriyusron.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.yusriyusron.dictionary.model.WordModel;

public class DetailActivity extends AppCompatActivity {

    private TextView detailWord,detailTranslation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailWord = findViewById(R.id.detail_word);
        detailTranslation = findViewById(R.id.detail_translation);

        WordModel wordModel = getIntent().getParcelableExtra("WORD");
        detailWord.setText(wordModel.getWord());
        detailTranslation.setText(wordModel.getTranslation());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            Intent intent = new Intent(DetailActivity.this,DictionaryActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

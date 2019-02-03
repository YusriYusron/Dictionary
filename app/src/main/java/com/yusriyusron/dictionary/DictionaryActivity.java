package com.yusriyusron.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.yusriyusron.dictionary.adapter.WordAdapter;
import com.yusriyusron.dictionary.database.DatabaseContract;
import com.yusriyusron.dictionary.database.DictionaryHelper;
import com.yusriyusron.dictionary.model.WordModel;

import java.util.ArrayList;

public class DictionaryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText inputSearch;
    private ImageButton btnSearch;

    private ArrayList<WordModel> list;

    private WordAdapter wordAdapter;
    private DictionaryHelper dictionaryHelper;

    private String languageMode = DatabaseContract.TABLE_EN;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamus);

        recyclerView = findViewById(R.id.rv_words);
        inputSearch = findViewById(R.id.edit_search);
        btnSearch = findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataChanged(inputSearch.getText().toString());
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        dataChanged("");

        getSupportActionBar().setTitle("English - Indonesia");
    }

    private void dataChanged(String filter){
        wordAdapter = new WordAdapter(this);
        dictionaryHelper = new DictionaryHelper(this);

        recyclerView.setAdapter(wordAdapter);

        dictionaryHelper.open();

        list = dictionaryHelper.query(languageMode, filter);
        dictionaryHelper.close();
        wordAdapter.addItem(list);

        CustomClickListener.addTo(recyclerView).setOnItemClickListener(new CustomClickListener.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(DictionaryActivity.this,DetailActivity.class);
                intent.putExtra("WORD",list.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.en_to_id){
            languageMode = DatabaseContract.TABLE_EN;
            dataChanged("");
            getSupportActionBar().setTitle("English - Indonesia");
            return true;
        }else if (item.getItemId() == R.id.id_to_en){
            languageMode = DatabaseContract.TABLE_ID;
            dataChanged("");
            getSupportActionBar().setTitle("Indonesia - English");
            return true;
        }else {
            return super.onContextItemSelected(item);
        }
    }
}

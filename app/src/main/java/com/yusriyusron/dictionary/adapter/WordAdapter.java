package com.yusriyusron.dictionary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yusriyusron.dictionary.R;
import com.yusriyusron.dictionary.model.WordModel;

import java.util.ArrayList;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordHolder> {
    private ArrayList<WordModel> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInfleter;

    public WordAdapter(Context context) {
        this.context = context;
        mInfleter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public WordHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list,viewGroup,false);
        return new WordHolder(view);
    }

    public void addItem(ArrayList<WordModel> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull WordHolder wordHolder, int i) {
        wordHolder.textView.setText(mData.get(i).getWord());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class WordHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public WordHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.id_words);
        }
    }
}

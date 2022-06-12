package com.example.firstmultiscreenapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {
     private int mColorResourceId;

    public WordAdapter(@NonNull Context context, int resource, @NonNull List<Word> objects,int ColorResourceId) {
        super(context, resource, objects);
        mColorResourceId=ColorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView=convertView;
        if(listItemView==null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
     Word currentWord=getItem(position);
        TextView mivokTextView=(TextView) listItemView.findViewById(R.id.miwok_Text_view);
        mivokTextView.setText(currentWord.getMivok());

        TextView englishTextView=(TextView) listItemView.findViewById(R.id.english_Text_view);
        englishTextView.setText(currentWord.getEnglish());

        ImageView imageIcon=(ImageView) listItemView.findViewById(R.id.image_icon);
        if(currentWord.getImageAddress()==-1){
            imageIcon.setVisibility(View.GONE);
        }else {
            imageIcon.setImageResource(currentWord.getImageAddress());
        }

        View textContainer=listItemView.findViewById(R.id.container);
        int color= ContextCompat.getColor(getContext(),mColorResourceId);
        textContainer.setBackgroundColor(color);
        return listItemView;
    }
}

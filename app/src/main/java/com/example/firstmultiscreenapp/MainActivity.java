package com.example.firstmultiscreenapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView number=(TextView) findViewById(R.id.numbers);
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent numberIntent=new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(numberIntent);
            }
        });

        TextView family=(TextView) findViewById(R.id.family);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent familyIntent=new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(familyIntent);
                // Toast.makeText(view.getContext(),"open the list of family members",Toast.LENGTH_SHORT).show();
            }
        });

        TextView colors=(TextView) findViewById(R.id.colors);
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent colorIntent=new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(colorIntent);
                // Toast.makeText(view.getContext(),"open the list of Colors",Toast.LENGTH_SHORT).show();
            }
        });

        TextView phrases=(TextView) findViewById(R.id.phrases);
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phrasesIntent=new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(phrasesIntent);
                // Toast.makeText(view.getContext(),"open the list of Phrases",Toast.LENGTH_SHORT).show();
            }
        });



    }

}
package com.example.filmchoice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView name = (TextView) findViewById(R.id.textName2);
        TextView genres = (TextView) findViewById(R.id.textGenres);
        TextView actors = (TextView) findViewById(R.id.textActors);
        TextView director = (TextView) findViewById(R.id.textDirector);
        TextView year = (TextView) findViewById(R.id.textYear);
        TextView rating = (TextView) findViewById(R.id.textRating2);

        FloatingActionButton btnDelete = (FloatingActionButton) findViewById(R.id.floatingDelete);
        FloatingActionButton btnEdit = (FloatingActionButton) findViewById(R.id.floatingEdit);
    }
}
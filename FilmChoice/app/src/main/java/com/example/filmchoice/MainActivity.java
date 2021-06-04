package com.example.filmchoice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Film> films;
    RecyclerAdapter adapter;
    Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        DatabaseHandler db = new DatabaseHandler(this);

       /* Log.d("Insert: ", "Inserting ...");
        db.addFilm(new Film("Raya and The Last Dragon", "Fantasy", "Kelly Marie Tran", "Don Hall", 2021, 6.9));
        db.addFilm(new Film("Avengers: Endgame", "Action", "Robert DoWney Jr.", "Anthony Russo", 2019, 8.7));
        db.addFilm(new Film("Inception", "Sci-Fi", "Leonardo DiCaprio", "Christopher Nolan", 2010, 9.0));
        db.addFilm(new Film("Memento", "Thriller", "Guy Pearce", "Christopher Nolan", 2000, 8.8));
        db.addFilm(new Film("Interstellar", "Sci-Fi", "Matthew McConaughey", "Christopher Nolan", 2014, 9.1)); */

        //Log.d("Reading: ", "Reading all contacts...");
        films = db.getAllFilms();

       /* for (Film f : films)
        {
            String log = "Id: " + f.getId() + ", Name: " + f.getName() + ", Genre: " + f.getGenres() + ", Actor: " + f.getActors() + ", Director: " + f.getDirector() + ", Year: "
                    + f.getYear() + ", Rating: " + f.getRating();
            Log.d("Film: ", log);
        } */

        adapter = new RecyclerAdapter(films);
        RecyclerView view = findViewById(R.id.recycleFilms);

        FloatingActionButton btnAdd = (FloatingActionButton) findViewById(R.id.addButton);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        view.setAdapter(adapter);
        view.setLayoutManager(new LinearLayoutManager(this));

        EditText searchFilter = (EditText) findViewById(R.id.searchFilter);

        searchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Film film = data.getParcelableExtra("film");

        if(film != null) {
            for (Film f : films) {
                if (f.getName().equals(film.getName()))
                    f.setRating(film.getRating());
            }
        }

        adapter.notifyDataSetChanged();
    }

    void filter(String text){
        List<Film> temp = new ArrayList();
        for(Film f: films){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(f.getDirector().toLowerCase().contains(text.toLowerCase()) || f.getActors().toLowerCase().contains(text.toLowerCase())
                    || Integer.toString(f.getYear()).equals(text) || f.getName().contains(text)
                    || f.getGenres().contains(text)){
                temp.add(f);
            }
        }
        adapter.updateList(temp);
    }

}
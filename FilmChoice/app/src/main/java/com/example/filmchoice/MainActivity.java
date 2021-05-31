package com.example.filmchoice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Film> films;
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);

        Log.d("Insert: ", "Inserting ...");
        db.addFilm(new Film("Raya and The Last Dragon", "Fantasy", "Kelly Marie Tran", "Don Hall", 2021, 6.9));
        db.addFilm(new Film("Avengers: Endgame", "Action", "Robert DoWney Jr.", "Anthony Russo", 2019, 8.7));
        db.addFilm(new Film("Inception", "Sci-Fi", "Leonardo DiCaprio", "Christopher Nolan", 2010, 9.0));
        db.addFilm(new Film("Memento", "Thriller", "Guy Pearce", "Christopher Nolan", 2000, 8.8));
        db.addFilm(new Film("Interstellar", "Sci-Fi", "Matthew McConaughey", "Christopher Nolan", 2014, 9.1));

        Log.d("Reading: ", "Reading all contacts...");
        films = db.getAllFilms();

        for (Film f : films)
        {
            String log = "Id: " + f.getId() + ", Name: " + f.getName() + ", Genre: " + f.getGenres() + ", Actor: " + f.getActors() + ", Director: " + f.getDirector() + ", Year: "
                    + f.getYear() + ", Rating: " + f.getRating();
            Log.d("Film: ", log);
        }

        RecyclerView view = findViewById(R.id.recycleFilms);
        adapter = new RecyclerAdapter(films);
        view.setAdapter(adapter);
        view.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Film film = data.getParcelableExtra("film");
        for(Film f : films)
        {
            if(f.getName().equals(film.getName()))
                f.setRating(film.getRating());
        }

        adapter.notifyDataSetChanged();
    }
}
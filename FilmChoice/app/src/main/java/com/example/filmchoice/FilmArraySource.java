package com.example.filmchoice;

import android.content.Context;

import java.util.ArrayList;

public class FilmArraySource {
    public static ArrayList<FilmArray> generateItemsList(Film film) {
        ArrayList<FilmArray> films = new ArrayList<>();

        films.add(new FilmArray("Name:",  film.getName()));
        films.add(new FilmArray("Genres:",  film.getGenres()));
        films.add(new FilmArray("Actors:",  film.getActors()));
        films.add(new FilmArray("Director:",  film.getDirector()));
        films.add(new FilmArray("Year:",  film.getYear().toString()));
        films.add(new FilmArray("Rating:", film.getRating().toString() + "/10"));

        return  films;
    }
}

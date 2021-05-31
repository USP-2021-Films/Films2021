package com.example.filmchoice;

public class Film {
    Integer id;
    String name;
    String genres;
    String actors;
    String director;
    Integer year;
    Double rating;

    public Film() {
    }

    public Film(Integer id, String name, String genres, String actors, String director, Integer year, Double rating) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.actors = actors;
        this.director = director;
        this.year = year;
        this.rating = rating;
    }

    public Film(String name, String genres, String actors, String director, Integer year, Double rating) {
        this.name = name;
        this.genres = genres;
        this.actors = actors;
        this.director = director;
        this.year = year;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}

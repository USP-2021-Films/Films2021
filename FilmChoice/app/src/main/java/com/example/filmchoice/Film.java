package com.example.filmchoice;

import android.os.Parcel;
import android.os.Parcelable;

public class Film implements Parcelable {
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

    protected Film(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        genres = in.readString();
        actors = in.readString();
        director = in.readString();
        if (in.readByte() == 0) {
            year = null;
        } else {
            year = in.readInt();
        }
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(genres);
        dest.writeString(actors);
        dest.writeString(director);
        if (year == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(year);
        }
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rating);
        }
    }
}

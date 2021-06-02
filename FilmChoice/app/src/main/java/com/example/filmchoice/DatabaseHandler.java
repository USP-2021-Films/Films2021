package com.example.filmchoice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FilmChoice";
    private static final String TABLE_FILMS = "Films";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_GENRES = "genres";
    private static final String KEY_ACTORS = "actors";
    private static final String KEY_DIRECTOR = "director";
    private static final String KEY_YEAR = "year";
    private static final String KEY_RATING = "rating";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FILMS_TABLE = "CREATE TABLE " + TABLE_FILMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_GENRES + " TEXT," + KEY_ACTORS + " TEXT," + KEY_DIRECTOR + " TEXT," +
                KEY_YEAR + " TEXT," + KEY_RATING + " TEXT"+ ")";
        db.execSQL(CREATE_FILMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILMS);

        onCreate(db);
    }

    void addFilm(Film film)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, film.getName());
        values.put(KEY_GENRES, film.getGenres());
        values.put(KEY_ACTORS, film.getActors());
        values.put(KEY_DIRECTOR, film.getDirector());
        values.put(KEY_YEAR, film.getYear());
        values.put(KEY_RATING, film.getRating());

        db.insert(TABLE_FILMS, null, values);
        db.close();
    }

    Film getFilm(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FILMS, new String[] { KEY_ID,
                        KEY_NAME, KEY_GENRES, KEY_ACTORS, KEY_DIRECTOR, KEY_YEAR, KEY_RATING }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        Film film = new Film(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), Integer.parseInt(cursor.getString(5)), Double.parseDouble(cursor.getString(6)));

        return film;
    }

    public List<Film> getAllFilms()
    {
        List<Film> filmList = new ArrayList<Film>();
        String selectQuery = "SELECT * FROM " + TABLE_FILMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst())
        {
            do {
                Film film = new Film();
                film.setId(Integer.parseInt(cursor.getString(0)));
                film.setName(cursor.getString(1));
                film.setGenres(cursor.getString(2));
                film.setActors(cursor.getString(3));
                film.setDirector(cursor.getString(4));
                film.setYear(Integer.parseInt(cursor.getString(5)));
                film.setRating(Double.parseDouble(cursor.getString(6)));

                filmList.add(film);
            }while (cursor.moveToNext());
        }

        return filmList;
    }

    public int updateFilm(Film film)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, film.getName());
        values.put(KEY_GENRES, film.getGenres());
        values.put(KEY_ACTORS, film.getActors());
        values.put(KEY_DIRECTOR, film.getDirector());
        values.put(KEY_YEAR, film.getYear());
        values.put(KEY_RATING, film.getRating());

        return db.update(TABLE_FILMS, values, KEY_ID + " = ?",
                new String[]{ String.valueOf(film.getId())});
    }

    public void deleteFilm(Film film)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FILMS, KEY_ID + " =? ",
                new String[] { String.valueOf(film.getId())});
        db.close();
    }

    public int getFilmCount()
    {
        String countQuery = "SELECT * FROM " + TABLE_FILMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}

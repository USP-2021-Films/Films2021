package com.example.filmchoice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    LayoutInflater inflater;
    View layout;
    PopupWindow popUp;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        EditText name = (EditText) findViewById(R.id.addName);
        EditText genre = (EditText) findViewById(R.id.addGenres);
        EditText actor = (EditText) findViewById(R.id.addActors);
        EditText director = (EditText) findViewById(R.id.addDirector);
        EditText year = (EditText) findViewById(R.id.addYear);
        EditText rating = (EditText) findViewById(R.id.addRating);

        Button btnFinish = (Button) findViewById(R.id.addFinish);

        name.addTextChangedListener(new Validation(name, btnFinish));
        genre.addTextChangedListener(new Validation(genre, btnFinish));
        actor.addTextChangedListener(new Validation(actor, btnFinish));
        director.addTextChangedListener(new Validation(director, btnFinish));
        year.addTextChangedListener(new Validation(year, btnFinish));
        rating.addTextChangedListener(new Validation(rating, btnFinish));


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Film film = new Film();
                film.setName(name.getText().toString());
                film.setGenres(genre.getText().toString());
                film.setActors(actor.getText().toString());
                film.setDirector(director.getText().toString());
                film.setYear(Integer.parseInt(year.getText().toString()));
                film.setRating(Double.parseDouble(rating.getText().toString()));

                ShowPopUp(film, name, genre, actor, director, year, rating);
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void ShowPopUp(Film film, EditText name, EditText genres, EditText actors, EditText director, EditText year, EditText rating) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        inflater = (LayoutInflater) AddActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.popup, null);
        popUp = new PopupWindow(this);

        popUp.setContentView(layout);
        popUp.setWidth(width);
        popUp.setHeight(height);
        popUp.setFocusable(true);

        popUp.setBackgroundDrawable(null);

        popUp.showAtLocation(layout, Gravity.CENTER, 1, 1);

        TextView popUpText = (TextView) layout.findViewById(R.id.textPopUp);
        popUpText.setText("Are you sure you want to add this film?");
        Button btnYes = (Button) layout.findViewById(R.id.yesPopUp);
        Button btnNo = (Button) layout.findViewById(R.id.noPopUp);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHandler(AddActivity.this);
                if(tryParseInt(rating.getText().toString().trim()) != null)
                {
                    film.setRating(Double.valueOf(Integer.parseInt(rating.getText().toString())));
                }
                db.addFilm(film);

                name.setText(null);
                genres.setText(null);
                actors.setText(null);
                director.setText(null);
                year.setText(null);
                rating.setText(null);

                popUp.dismiss();

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public class Validation implements TextWatcher
    {
        private EditText et;
        private Button btn;
        DatabaseHandler db;

        public Validation(EditText et, Button btn) {
            this.et = et;
            this.btn = btn;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            this.et.getId();
            switch (this.et.getId())
            {
                case R.id.addName:
                    if(et.getText().toString().isEmpty())
                    {
                        et.setError("Празно поле!");
                        btn.setEnabled(false);
                    }
                    else
                    {
                        db = new DatabaseHandler(AddActivity.this);
                        List<Film> temp = db.getAllFilms();
                        String film = et.getText().toString().trim().toLowerCase();
                        for(Film f : temp)
                        {
                            if(film.equals(f.getName().trim().toLowerCase()))
                            {
                                et.setError("Съществуващ филм!");
                                btn.setEnabled(false);
                            }
                            else
                                btn.setEnabled(true);
                        }

                    }
                    break;
                case R.id.addGenres:
                case R.id.addActors:
                case R.id.addDirector:
                    if(et.getText().toString().isEmpty() || et.getText().toString().trim().length()<4)
                    {
                        et.setError("Поне 4 символа!");
                        btn.setEnabled(false);
                    }
                    else
                    {
                        btn.setEnabled(true);
                    }
                    break;
                case R.id.addYear:
                    if(et.getText().toString().isEmpty() || et.getText().toString().trim().length()!=4
                            || Integer.parseInt(et.getText().toString()) <1888 || Integer.parseInt(et.getText().toString()) >2049)
                    {
                        et.setError("Неправилна година!");
                        btn.setEnabled(false);
                    }
                    else
                    {
                        btn.setEnabled(true);
                    }
                    break;
                case R.id.addRating:
                    if(et.getText().toString().isEmpty())
                    {
                        et.setError("Неправилен рейтинг!");
                        btn.setEnabled(false);
                    }
                    else
                    {
                        if( tryParseInt(et.getText().toString()) == null)
                        {
                            if (tryParseDouble(et.getText().toString()) == null)
                            {
                                et.setError("Неправилен рейтинг!");
                                btn.setEnabled(false);
                            }
                            else
                            {
                                if(Double.parseDouble(et.getText().toString()) < 1.0 || Double.parseDouble(et.getText().toString()) > 10.0)
                                {
                                    et.setError("Между 1 и 10!");
                                    btn.setEnabled(false);
                                }
                                else {
                                    btn.setEnabled(true);
                                }
                            }
                        }
                        else
                        {
                            if(Integer.parseInt(et.getText().toString()) < 1 || Integer.parseInt(et.getText().toString()) > 10)
                            {
                                et.setError("Между 1 и 10!");
                                btn.setEnabled(false);
                            }
                            else {
                                btn.setEnabled(true);
                            }
                        }
                    }
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    }
    public static Integer tryParseInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    public static Double tryParseDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
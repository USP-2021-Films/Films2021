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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

public class EditActivity extends AppCompatActivity {
    LayoutInflater inflater;
    View layout;
    PopupWindow popUp;

    Film film;
    Film film2;

    DatabaseHandler db = new DatabaseHandler(EditActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        Intent i = getIntent();
        film = i.getParcelableExtra("film");

        EditText name = (EditText) findViewById(R.id.editName);
        EditText genre = (EditText) findViewById(R.id.editGenres);
        EditText actor = (EditText) findViewById(R.id.editActors);
        EditText director = (EditText) findViewById(R.id.editDirector);
        EditText year = (EditText) findViewById(R.id.editYear);
        EditText rating = (EditText) findViewById(R.id.editRating);

        Button btnFinish = (Button) findViewById(R.id.buttonFinish);

        genre.addTextChangedListener(new Validation(genre, btnFinish));
        actor.addTextChangedListener(new Validation(actor, btnFinish));
        director.addTextChangedListener(new Validation(director, btnFinish));
        year.addTextChangedListener(new Validation(year, btnFinish));
        rating.addTextChangedListener(new Validation(rating, btnFinish));

        name.setText(film.getName());
        genre.setText(film.getGenres());
        actor.setText(film.getActors());
        director.setText(film.getDirector());
        year.setText(film.getYear().toString());
        rating.setText(film.getRating().toString());


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                film2 = new Film();
                film2.setId(film.getId());
                film2.setName(name.getText().toString());
                film2.setGenres(genre.getText().toString());
                film2.setActors(actor.getText().toString());
                film2.setDirector(director.getText().toString());
                film2.setYear(Integer.parseInt(year.getText().toString()));
                film2.setRating(Double.parseDouble(rating.getText().toString()));

                ShowPopUp(film2, rating);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, ShowFilm.class);
        int id = film.getId();
        Film film3 = db.getFilm(id);
        i.putExtra("film", film3);
        finish();
        startActivity(i);

    }

    public void ShowPopUp(Film film, EditText rating) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        inflater = (LayoutInflater) EditActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.popup, null);
        popUp = new PopupWindow(this);

        popUp.setContentView(layout);
        popUp.setWidth(width);
        popUp.setHeight(height);
        popUp.setFocusable(true);

        popUp.setBackgroundDrawable(null);

        popUp.showAtLocation(layout, Gravity.CENTER, 1, 1);

        TextView popUpText = (TextView) layout.findViewById(R.id.textPopUp);
        popUpText.setText("Are you sure you want to change this film?");
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
                if(tryParseInt(rating.getText().toString().trim()) != null)
                {
                    film.setRating(Double.valueOf(Integer.parseInt(rating.getText().toString())));
                }
                db.updateFilm(film);
                popUp.dismiss();
                onBackPressed();
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
                case R.id.editGenres:
                case R.id.editActors:
                case R.id.editDirector:
                    if(et.getText().toString().matches("") || et.getText().toString().trim().length()<4)
                    {
                        et.setError("Поне 4 символа!");
                        btn.setEnabled(false);
                    }
                    else
                    {
                        btn.setEnabled(true);
                    }
                    break;
                case R.id.editYear:
                    if(et.getText().toString().matches("") || et.getText().toString().length()!=4
                            || Integer.parseInt(et.getText().toString().trim()) <1888 || Integer.parseInt(et.getText().toString().trim()) >2049)
                    {
                        et.setError("Неправилна година!");
                        btn.setEnabled(false);
                    }
                    else
                    {
                        btn.setEnabled(true);
                    }
                    break;
                case R.id.editRating:
                    if(et.getText().toString().matches(""))
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
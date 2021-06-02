package com.example.filmchoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
    LayoutInflater inflater;
    View layout;
    PopupWindow popUp;

    Film film2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent i = getIntent();
        Film film = i.getParcelableExtra("film");

        EditText name = (EditText) findViewById(R.id.editName);
        EditText genre = (EditText) findViewById(R.id.editGenres);
        EditText actor = (EditText) findViewById(R.id.editActors);
        EditText director = (EditText) findViewById(R.id.editDirector);
        EditText year = (EditText) findViewById(R.id.editYear);
        EditText rating = (EditText) findViewById(R.id.editRating);

        name.setText(film.getName());
        genre.setText(film.getGenres());
        actor.setText(film.getActors());
        director.setText(film.getDirector());
        year.setText(film.getYear().toString());
        rating.setText(film.getRating().toString());

        Button btnFinish = (Button) findViewById(R.id.buttonFinish);

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

                ShowPopUp(film2);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, ShowFilm.class);
        i.putExtra("film", film2);
        finish();
        startActivity(i);

    }

    public void ShowPopUp(Film film) {
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
                DatabaseHandler db = new DatabaseHandler(EditActivity.this);
                db.updateFilm(film);
                popUp.dismiss();
                onBackPressed();
            }
        });
    }
}
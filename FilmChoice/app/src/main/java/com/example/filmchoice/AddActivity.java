package com.example.filmchoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        EditText name = (EditText) findViewById(R.id.addName);
        EditText genre = (EditText) findViewById(R.id.addGenres);
        EditText actor = (EditText) findViewById(R.id.addActors);
        EditText director = (EditText) findViewById(R.id.addDirector);
        EditText year = (EditText) findViewById(R.id.addYear);
        EditText rating = (EditText) findViewById(R.id.addRating);

        Button btnFinish = (Button) findViewById(R.id.addFinish);

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

                ShowPopUp(film);
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void ShowPopUp(Film film) {
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
                DatabaseHandler db = new DatabaseHandler(AddActivity.this);
                db.addFilm(film);
                popUp.dismiss();

            }
        });
    }


}
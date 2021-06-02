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
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShowFilm extends AppCompatActivity {
    LayoutInflater inflater;
    View layout;
    PopupWindow popUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        TextView name = (TextView) findViewById(R.id.textName2);
        TextView genres = (TextView) findViewById(R.id.textGenres);
        TextView actors = (TextView) findViewById(R.id.textActors);
        TextView director = (TextView) findViewById(R.id.textDirector);
        TextView year = (TextView) findViewById(R.id.textYear);
        TextView rating = (TextView) findViewById(R.id.textRating2);

        Intent i = getIntent();
        Film film = i.getParcelableExtra("film");
        int id = film.getId();
        DatabaseHandler db = new DatabaseHandler(ShowFilm.this);
        Film film2 = db.getFilm(id);

        name.setText("Name: " + film2.getName());
        genres.setText("Genre: " + film2.getGenres());
        actors.setText("Actor: " + film2.getActors());
        director.setText("Director: " + film2.getDirector());
        year.setText("Year: " + film2.getYear().toString());
        rating.setText("Rating: " + film2.getRating().toString() + "/10");


        FloatingActionButton btnDelete = (FloatingActionButton) findViewById(R.id.floatingDelete);
        FloatingActionButton btnEdit = (FloatingActionButton) findViewById(R.id.floatingEdit);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopUp(film2);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(ShowFilm.this, EditActivity.class);
                i2.putExtra("film", film2);
                startActivity(i2);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void ShowPopUp(Film film)
    {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        inflater = (LayoutInflater) ShowFilm.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.popup, null);
        popUp = new PopupWindow(this);

        popUp.setContentView(layout);
        popUp.setWidth(width);
        popUp.setHeight(height);
        popUp.setFocusable(true);

        popUp.setBackgroundDrawable(null);

        popUp.showAtLocation(layout, Gravity.CENTER, 1, 1);

        TextView popUpText = (TextView) layout.findViewById(R.id.textPopUp);
        popUpText.setText("Are you sure you want to delete this film?");
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
                DatabaseHandler db = new DatabaseHandler(ShowFilm.this);
                db.deleteFilm(film);
                popUp.dismiss();
                onBackPressed();
            }
        });

    }
}
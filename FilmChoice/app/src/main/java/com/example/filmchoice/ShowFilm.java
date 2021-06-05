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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShowFilm extends AppCompatActivity {
    LayoutInflater inflater;
    View layout;
    PopupWindow popUp;
    Film film2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button


        Intent i = getIntent();
        Film film = i.getParcelableExtra("film");
        int id = film.getId();
        DatabaseHandler db = new DatabaseHandler(ShowFilm.this);
        film2 = db.getFilm(id);

        ArrayList<FilmArray> arrayOfFilms = FilmArraySource.generateItemsList(film2);

        FilmArrayAdapter adapter = new FilmArrayAdapter(this, arrayOfFilms);

        ListView listView = findViewById(R.id.listFilm);
        listView.setAdapter(adapter);
        listView.setDivider(getDrawable(R.drawable.border));


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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
package com.example.filmchoice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class FilmArrayAdapter extends ArrayAdapter<FilmArray> {
    public FilmArrayAdapter(@NonNull Context context, @NonNull List<FilmArray> objects) {
        super(context, 0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        FilmArray film = getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.show_film_layout, parent, false);
        }

        TextView filmInfo = convertView.findViewById(R.id.filmInfo);
        TextView filmValue = convertView.findViewById(R.id.filmValue);

        TextView info = convertView.findViewById(R.id.filmInfo);
        TextView value = convertView.findViewById(R.id.filmValue);

        info.setText(film.getInfo());
        value.setText(film.getValue());

        return convertView;
    }
}

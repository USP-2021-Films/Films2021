package com.example.filmchoice;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView rating;

    public DataHolder(@NonNull View itemView) {
        super(itemView);
        this.name = (TextView) itemView.findViewById(R.id.textName2);
        this.rating = (TextView) itemView.findViewById(R.id.textRating);
    }

    public TextView getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public TextView getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating.setText(Double.toString(rating));
    }
}

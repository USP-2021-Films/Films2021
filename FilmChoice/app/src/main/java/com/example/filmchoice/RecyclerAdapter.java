package com.example.filmchoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<DataHolder>{

    private Context context;
    private List<Film> filmList;

    public RecyclerAdapter(List<Film> filmList)
    {
        this.filmList = filmList;
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recycler_item_layout, parent, false);
        DataHolder viewHolder = new DataHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {
        Film item = filmList.get(position);

        holder.setName(item.getName());
        holder.setRating(item.getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ShowFilm.class);
                i.putExtra("film", item);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
       return filmList.size();
    }

    public void updateList(List<Film> list){
        filmList = list;
        notifyDataSetChanged();
    }
}

package com.example.miniproject2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject2.R;
import com.example.miniproject2.entities.Theater;

import java.util.List;

public class TheaterAdapter extends RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder> {

    private final Context context;
    private final List<Theater> theaters;

    public TheaterAdapter(Context context, List<Theater> theaters) {
        this.context = context;
        this.theaters = theaters;
    }

    @NonNull
    @Override
    public TheaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_theater, parent, false);
        return new TheaterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheaterViewHolder holder, int position) {
        Theater theater = theaters.get(position);
        holder.tvName.setText(theater.getName());
        holder.tvLocation.setText(theater.getLocation());
    }

    @Override
    public int getItemCount() { return theaters.size(); }

    static class TheaterViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvLocation;

        TheaterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName     = itemView.findViewById(R.id.tvTheaterName);
            tvLocation = itemView.findViewById(R.id.tvTheaterLocation);
        }
    }
}

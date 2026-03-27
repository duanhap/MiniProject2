package com.example.miniproject2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject2.R;
import com.example.miniproject2.SeatSelectionActivity;
import com.example.miniproject2.model.Showtime;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ShowtimeViewHolder> {

    private final Context context;
    private final List<Showtime> showtimes;
    private final String movieTitle;
    private final String theaterName;

    public ShowtimeAdapter(Context context, List<Showtime> showtimes, String movieTitle, String theaterName) {
        this.context = context;
        this.showtimes = showtimes;
        this.movieTitle = movieTitle;
        this.theaterName = theaterName;
    }

    @NonNull
    @Override
    public ShowtimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_showtime, parent, false);
        return new ShowtimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowtimeViewHolder holder, int position) {
        Showtime showtime = showtimes.get(position);
        holder.tvTime.setText(showtime.getTime());
        holder.tvPrice.setText(showtime.getPriceFormatted());

        holder.btnSelect.setOnClickListener(v -> navigateToSeatSelection(showtime));
        holder.itemView.setOnClickListener(v -> navigateToSeatSelection(showtime));
    }

    private void navigateToSeatSelection(Showtime showtime) {
        Intent intent = new Intent(context, SeatSelectionActivity.class);
        intent.putExtra("movie_title", movieTitle);
        intent.putExtra("theater_name", theaterName);
        intent.putExtra("showtime_time", showtime.getTime());
        intent.putExtra("showtime_price", showtime.getPrice());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() { return showtimes.size(); }

    static class ShowtimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvPrice;
        MaterialButton btnSelect;

        ShowtimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime   = itemView.findViewById(R.id.tvShowtime);
            tvPrice  = itemView.findViewById(R.id.tvShowtimePrice);
            btnSelect = itemView.findViewById(R.id.btnSelectShowtime);
        }
    }
}

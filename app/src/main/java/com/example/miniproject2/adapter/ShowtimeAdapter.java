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
import com.example.miniproject2.entities.Showtime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ShowtimeViewHolder> {

    private final Context context;
    private final List<Showtime> showtimes;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm, dd/MM", Locale.getDefault());

    public ShowtimeAdapter(Context context, List<Showtime> showtimes) {
        this.context = context;
        this.showtimes = showtimes;
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
        
        holder.tvTime.setText(timeFormat.format(new Date(showtime.getShowTime())));
        holder.tvPrice.setText(String.format(Locale.getDefault(), "%,.0f đ", showtime.getPrice()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SeatSelectionActivity.class);
            intent.putExtra("showtimeId", showtime.getId());
            intent.putExtra("price", showtime.getPrice());
            context.startActivity(intent);
        });
        
        if (holder.btnSelect != null) {
            holder.btnSelect.setOnClickListener(v -> {
                Intent intent = new Intent(context, SeatSelectionActivity.class);
                intent.putExtra("showtimeId", showtime.getId());
                intent.putExtra("price", showtime.getPrice());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() { return showtimes.size(); }

    static class ShowtimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvPrice;
        View btnSelect;

        ShowtimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime  = itemView.findViewById(R.id.tvShowtime);
            tvPrice = itemView.findViewById(R.id.tvShowtimePrice);
            btnSelect = itemView.findViewById(R.id.btnSelectShowtime);
        }
    }
}

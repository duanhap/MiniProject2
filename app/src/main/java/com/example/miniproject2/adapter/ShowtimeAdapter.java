package com.example.miniproject2.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject2.R;
import com.example.miniproject2.LoginActivity;
import com.example.miniproject2.SeatSelectionActivity;
import com.example.miniproject2.entities.Movie;
import com.example.miniproject2.entities.Showtime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ShowtimeViewHolder> {

    private final Context context;
    private final List<Showtime> showtimes;
    private final Map<Integer, Movie> movieMap;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm, dd/MM", Locale.getDefault());

    public ShowtimeAdapter(Context context, List<Showtime> showtimes, Map<Integer, Movie> movieMap) {
        this.context = context;
        this.showtimes = showtimes;
        this.movieMap = movieMap != null ? movieMap : new HashMap<>();
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
        Movie movie = movieMap.get(showtime.getMovieId());
        
        holder.tvTime.setText(timeFormat.format(new Date(showtime.getShowTime())));
        holder.tvPrice.setText(String.format(Locale.getDefault(), "%,.0f đ", showtime.getPrice()));
        holder.tvMovieTitle.setText(movie != null ? movie.getTitle() : "Phim");
        setMoviePoster(holder.ivPoster, movie != null ? movie.getImage() : null);

        holder.itemView.setOnClickListener(v -> openNextScreen(showtime));
        
        if (holder.btnSelect != null) {
            holder.btnSelect.setOnClickListener(v -> openNextScreen(showtime));
        }
    }

    private void openNextScreen(Showtime showtime) {
        SharedPreferences prefs = context.getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean(LoginActivity.KEY_IS_LOGGED_IN, false);

        Intent intent;
        if (isLoggedIn) {
            intent = new Intent(context, SeatSelectionActivity.class);
            intent.putExtra("showtimeId", showtime.getId());
            intent.putExtra("price", showtime.getPrice());
        } else {
            intent = new Intent(context, LoginActivity.class);
            intent.putExtra(LoginActivity.EXTRA_SHOWTIME_ID, showtime.getId());
        }
        context.startActivity(intent);
    }

    private void setMoviePoster(ImageView imageView, String imageName) {
        if (imageName != null && !imageName.trim().isEmpty()) {
            int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            if (resId != 0) {
                imageView.setImageResource(resId);
                return;
            }
        }
        imageView.setImageResource(R.drawable.ic_movie);
    }

    @Override
    public int getItemCount() { return showtimes.size(); }

    static class ShowtimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvPrice, tvMovieTitle;
        ImageView ivPoster;
        View btnSelect;

        ShowtimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime  = itemView.findViewById(R.id.tvShowtime);
            tvPrice = itemView.findViewById(R.id.tvShowtimePrice);
            tvMovieTitle = itemView.findViewById(R.id.tvShowtimeMovieTitleItem);
            ivPoster = itemView.findViewById(R.id.ivShowtimeMoviePosterItem);
            btnSelect = itemView.findViewById(R.id.btnSelectShowtime);
        }
    }
}

package com.example.miniproject2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject2.R;
import com.example.miniproject2.ShowtimeActivity;
import com.example.miniproject2.entities.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final Context context;
    private final List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);

        holder.tvTitle.setText(movie.getTitle());
        holder.tvDuration.setText(movie.getDuration() + " min");

        // Display image from drawable resource name
        if (movie.getImage() != null && !movie.getImage().isEmpty()) {
            int resId = context.getResources().getIdentifier(movie.getImage(), "drawable", context.getPackageName());
            if (resId != 0) {
                holder.ivPoster.setImageResource(resId);
            }
        }

        // Click → navigate to ShowtimeActivity with movieId
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShowtimeActivity.class);
            intent.putExtra("movieId", movie.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle, tvDuration;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivMoviePoster);
            tvTitle  = itemView.findViewById(R.id.tvMovieTitle);
            tvDuration = itemView.findViewById(R.id.tvMovieDuration);
        }
    }
}

package com.example.miniproject2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject2.adapter.ShowtimeAdapter;
import com.example.miniproject2.dal.AppDatabase;
import com.example.miniproject2.entities.Movie;
import com.example.miniproject2.entities.Showtime;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowtimeActivity extends AppCompatActivity {

    private RecyclerView recyclerShowtimes;
    private int movieId;
    private ShowtimeAdapter adapter;
    private List<Showtime> showtimeList = new ArrayList<>();
    private final Map<Integer, Movie> movieMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtime);

        movieId = getIntent().getIntExtra("movieId", -1);

        setupToolbar();
        setupRecyclerView();
        loadData();
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupRecyclerView() {
        recyclerShowtimes = findViewById(R.id.recyclerShowtimes);
        recyclerShowtimes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShowtimeAdapter(this, showtimeList, movieMap);
        recyclerShowtimes.setAdapter(adapter);
    }

    private void loadData() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Movie movie = null;
            List<Showtime> showtimes;
            if (movieId == -1) {
                showtimes = AppDatabase.getInstance(this).showtimeDAO().getAll();
                List<Movie> movies = AppDatabase.getInstance(this).movieDAO().getAll();
                movieMap.clear();
                for (Movie item : movies) {
                    movieMap.put(item.getId(), item);
                }
            } else {
                movie = AppDatabase.getInstance(this).movieDAO().findById(movieId);
                showtimes = AppDatabase.getInstance(this).showtimeDAO().getByMovieId(movieId);
                movieMap.clear();
                if (movie != null) {
                    movieMap.put(movie.getId(), movie);
                }
            }

            Movie finalMovie = movie;
            runOnUiThread(() -> {
                TextView tvMovieTitle = findViewById(R.id.tvShowtimeMovieTitle);
                TextView tvTheater = findViewById(R.id.tvShowtimeTheater);
                ImageView ivPoster = findViewById(R.id.ivShowtimeMoviePoster);

                if (finalMovie != null) {
                    tvMovieTitle.setText(finalMovie.getTitle());
                    tvTheater.setText("Chọn suất chiếu");
                    setMoviePoster(ivPoster, finalMovie.getImage());
                } else {
                    tvMovieTitle.setText("Tất cả suất chiếu");
                    tvTheater.setText("Danh sách toàn hệ thống");
                    ivPoster.setImageResource(R.drawable.ic_movie);
                }

                showtimeList.clear();
                showtimeList.addAll(showtimes);
                adapter.notifyDataSetChanged();
            });
        });
    }

    private void setMoviePoster(ImageView imageView, String imageName) {
        if (imageName != null && !imageName.trim().isEmpty()) {
            int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            if (resId != 0) {
                imageView.setImageResource(resId);
                return;
            }
        }
        imageView.setImageResource(R.drawable.ic_movie);
    }
}

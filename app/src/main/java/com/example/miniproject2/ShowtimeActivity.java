package com.example.miniproject2;

import android.os.Bundle;
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
import java.util.List;

public class ShowtimeActivity extends AppCompatActivity {

    private RecyclerView recyclerShowtimes;
    private int movieId;
    private ShowtimeAdapter adapter;
    private List<Showtime> showtimeList = new ArrayList<>();

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
        adapter = new ShowtimeAdapter(this, showtimeList);
        recyclerShowtimes.setAdapter(adapter);
    }

    private void loadData() {
        if (movieId == -1) return;

        AppDatabase.databaseWriteExecutor.execute(() -> {
            Movie movie = AppDatabase.getInstance(this).movieDAO().findById(movieId);
            List<Showtime> showtimes = AppDatabase.getInstance(this).showtimeDAO().getByMovieId(movieId);

            runOnUiThread(() -> {
                if (movie != null) {
                    ((TextView) findViewById(R.id.tvShowtimeMovieTitle)).setText(movie.getTitle());
                }
                showtimeList.clear();
                showtimeList.addAll(showtimes);
                adapter.notifyDataSetChanged();
            });
        });
    }
}

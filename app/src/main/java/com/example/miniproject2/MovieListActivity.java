package com.example.miniproject2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject2.adapter.MovieAdapter;
import com.example.miniproject2.dal.AppDatabase;
import com.example.miniproject2.entities.Movie;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    private RecyclerView recyclerMovies;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        setupToolbar();
        setupRecyclerView();
        loadMovies();
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        recyclerMovies = findViewById(R.id.recyclerMovies);
        recyclerMovies.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter(this, movieList);
        recyclerMovies.setAdapter(movieAdapter);
        recyclerMovies.setItemAnimator(new androidx.recyclerview.widget.DefaultItemAnimator());
    }

    private void loadMovies() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Movie> movies = AppDatabase.getInstance(this).movieDAO().getAll();
            runOnUiThread(() -> {
                movieList.clear();
                movieList.addAll(movies);
                movieAdapter.notifyDataSetChanged();
            });
        });
    }
}

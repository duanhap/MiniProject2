package com.example.miniproject2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject2.adapter.MovieAdapter;
import com.example.miniproject2.dal.AppDatabase;
import com.example.miniproject2.entities.Movie;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieListActivity extends AppCompatActivity {

    private RecyclerView recyclerMovies;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<>();
    private final List<Movie> allMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        setupToolbar();
        setupRecyclerView();
        setupSearch();
        loadMovies();
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
        recyclerMovies = findViewById(R.id.recyclerMovies);
        recyclerMovies.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter(this, movieList);
        recyclerMovies.setAdapter(movieAdapter);
        recyclerMovies.setItemAnimator(new androidx.recyclerview.widget.DefaultItemAnimator());
    }

    private void setupSearch() {
        TextInputEditText etMovieSearch = findViewById(R.id.etMovieSearch);
        etMovieSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // no-op
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMovies(s != null ? s.toString() : "");
            }

            @Override
            public void afterTextChanged(Editable s) {
                // no-op
            }
        });
    }

    private void loadMovies() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Movie> movies = AppDatabase.getInstance(this).movieDAO().getAll();
            runOnUiThread(() -> {
                allMovies.clear();
                allMovies.addAll(movies);
                movieList.clear();
                movieList.addAll(movies);
                movieAdapter.notifyDataSetChanged();
            });
        });
    }

    private void filterMovies(String query) {
        String normalized = query == null ? "" : query.trim().toLowerCase(Locale.getDefault());
        movieList.clear();

        if (normalized.isEmpty()) {
            movieList.addAll(allMovies);
        } else {
            for (Movie movie : allMovies) {
                String title = movie.getTitle() != null ? movie.getTitle().toLowerCase(Locale.getDefault()) : "";
                if (title.contains(normalized)) {
                    movieList.add(movie);
                }
            }
        }
        movieAdapter.notifyDataSetChanged();
    }
}

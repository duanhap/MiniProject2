package com.example.miniproject2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;

import com.example.miniproject2.adapter.MovieAdapter;
import com.example.miniproject2.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    private RecyclerView recyclerMovies;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        setupToolbar();
        initData();
        setupRecyclerView();
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initData() {
        movieList = new ArrayList<>();
        // Sample movie data (imageResId = 0 uses gradient background)
        movieList.add(new Movie("Avengers: Endgame", 0, 181));
        movieList.add(new Movie("Spider-Man: No Way Home", 0, 148));
        movieList.add(new Movie("Top Gun: Maverick", 0, 131));
        movieList.add(new Movie("Doctor Strange in the Multiverse of Madness", 0, 126));
        movieList.add(new Movie("Thor: Love and Thunder", 0, 119));
        movieList.add(new Movie("Black Panther: Wakanda Forever", 0, 161));
        movieList.add(new Movie("Avatar: The Way of Water", 0, 192));
        movieList.add(new Movie("The Batman", 0, 176));
    }

    private void setupRecyclerView() {
        recyclerMovies = findViewById(R.id.recyclerMovies);
        recyclerMovies.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter(this, movieList);
        recyclerMovies.setAdapter(movieAdapter);

        // Add item animation
        recyclerMovies.setItemAnimator(new androidx.recyclerview.widget.DefaultItemAnimator());
    }
}

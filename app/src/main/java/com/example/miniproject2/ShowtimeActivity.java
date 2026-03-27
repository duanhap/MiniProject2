package com.example.miniproject2;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;

import com.example.miniproject2.adapter.ShowtimeAdapter;
import com.example.miniproject2.model.Showtime;

import java.util.ArrayList;
import java.util.List;

public class ShowtimeActivity extends AppCompatActivity {

    private RecyclerView recyclerShowtimes;
    private String movieTitle, theaterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtime);

        movieTitle  = getIntent().getStringExtra("movie_title");
        theaterName = getIntent().getStringExtra("theater_name");

        setupToolbar();
        setupHeader();
        setupRecyclerView();
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupHeader() {
        TextView tvMovie   = findViewById(R.id.tvShowtimeMovieTitle);
        TextView tvTheater = findViewById(R.id.tvShowtimeTheater);
        if (movieTitle  != null) tvMovie.setText(movieTitle);
        if (theaterName != null) tvTheater.setText(theaterName);
    }

    private void setupRecyclerView() {
        recyclerShowtimes = findViewById(R.id.recyclerShowtimes);
        recyclerShowtimes.setLayoutManager(new LinearLayoutManager(this));

        List<Showtime> showtimes = getSampleShowtimes();
        ShowtimeAdapter adapter = new ShowtimeAdapter(this, showtimes, movieTitle, theaterName);
        recyclerShowtimes.setAdapter(adapter);
    }

    private List<Showtime> getSampleShowtimes() {
        List<Showtime> list = new ArrayList<>();
        list.add(new Showtime("09:00", 90000));
        list.add(new Showtime("11:30", 100000));
        list.add(new Showtime("14:00", 110000));
        list.add(new Showtime("16:30", 110000));
        list.add(new Showtime("19:00", 130000));
        list.add(new Showtime("21:30", 150000));
        return list;
    }
}

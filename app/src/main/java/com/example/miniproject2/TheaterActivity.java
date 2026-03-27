package com.example.miniproject2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;

import com.example.miniproject2.adapter.TheaterAdapter;
import com.example.miniproject2.model.Theater;

import java.util.ArrayList;
import java.util.List;

public class TheaterActivity extends AppCompatActivity {

    private RecyclerView recyclerTheaters;
    private String movieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater);

        // Get passed data
        movieTitle = getIntent().getStringExtra("movie_title");

        setupToolbar();
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

    private void setupRecyclerView() {
        recyclerTheaters = findViewById(R.id.recyclerTheaters);
        recyclerTheaters.setLayoutManager(new LinearLayoutManager(this));

        List<Theater> theaters = getSampleTheaters();
        TheaterAdapter adapter = new TheaterAdapter(this, theaters, movieTitle);
        recyclerTheaters.setAdapter(adapter);
    }

    private List<Theater> getSampleTheaters() {
        List<Theater> list = new ArrayList<>();
        list.add(new Theater("CGV Vincom Center", "72 Lê Thánh Tôn, Q.1, TP.HCM"));
        list.add(new Theater("CGV Gigamall", "240A Hương Lộ 2, Q. Bình Thạnh"));
        list.add(new Theater("Lotte Cinema Gò Vấp", "242 Nguyễn Văn Nghi, Gò Vấp"));
        list.add(new Theater("BHD Star Phạm Hùng", "KDC Sala, Q.2, TP.HCM"));
        list.add(new Theater("Galaxy Nguyễn Du", "116 Nguyễn Du, Q.1, TP.HCM"));
        list.add(new Theater("Cinestar Quốc Thanh", "271 Nguyễn Trãi, Q.1, TP.HCM"));
        return list;
    }
}

package com.example.miniproject2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;

import com.example.miniproject2.adapter.TheaterAdapter;
import com.example.miniproject2.dal.AppDatabase;
import com.example.miniproject2.entities.Theater;

import java.util.List;

public class TheaterActivity extends AppCompatActivity {

    private RecyclerView recyclerTheaters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater);

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
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Theater> theaters = AppDatabase.getInstance(this).theaterDAO().getAll();
            runOnUiThread(() -> {
                TheaterAdapter adapter = new TheaterAdapter(this, theaters);
                recyclerTheaters.setAdapter(adapter);
            });
        });
    }
}

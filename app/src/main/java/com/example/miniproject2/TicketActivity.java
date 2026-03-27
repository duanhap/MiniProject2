package com.example.miniproject2;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class TicketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        setupToolbar();
        populateTicketData();
        setupButtons();
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void populateTicketData() {
        // Retrieve booking info from intent
        String movieTitle   = getIntent().getStringExtra("movie_title");
        String theaterName  = getIntent().getStringExtra("theater_name");
        String showtimeTime = getIntent().getStringExtra("showtime_time");
        String seats        = getIntent().getStringExtra("selected_seats");
        long   totalPrice   = getIntent().getLongExtra("total_price", 0L);

        // Format booking ID
        String bookingId = "CB-" + new SimpleDateFormat("yyyyMMdd", Locale.getDefault())
                .format(new Date()) + "-" + String.format("%04d", new Random().nextInt(9999));

        // Bind views
        setText(R.id.tvBookingId,    bookingId);
        setText(R.id.tvTicketMovie,  movieTitle  != null ? movieTitle  : "—");
        setText(R.id.tvTicketTheater, theaterName != null ? theaterName : "—");

        // Time + Date
        String today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        setText(R.id.tvTicketTime,   (showtimeTime != null ? showtimeTime : "—") + " • " + today);

        setText(R.id.tvTicketSeats,  seats != null ? seats : "—");
        setText(R.id.tvTicketPrice,
                String.format("%,d đ", totalPrice).replace(',', '.'));
    }

    private void setText(int viewId, String text) {
        TextView tv = findViewById(viewId);
        if (tv != null) tv.setText(text);
    }

    private void setupButtons() {
        MaterialButton btnDownload = findViewById(R.id.btnDownloadTicket);
        MaterialButton btnShare    = findViewById(R.id.btnShareTicket);

        btnDownload.setOnClickListener(v ->
                Toast.makeText(this, "Đã lưu vé vào thư viện ảnh!", Toast.LENGTH_SHORT).show());

        btnShare.setOnClickListener(v ->
                Toast.makeText(this, "Chức năng chia sẻ đang phát triển", Toast.LENGTH_SHORT).show());
    }
}

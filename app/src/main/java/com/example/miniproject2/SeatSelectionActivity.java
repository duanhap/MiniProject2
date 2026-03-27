package com.example.miniproject2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SeatSelectionActivity extends AppCompatActivity {

    private static final int ROWS    = 7;   // A–G
    private static final int COLS    = 8;   // 1–8
    private static final char FIRST_ROW_CHAR = 'A';

    // Pre-taken seats (simulate already booked)
    private static final Set<String> TAKEN_SEATS = new HashSet<>();
    static {
        TAKEN_SEATS.add("A3"); TAKEN_SEATS.add("A4");
        TAKEN_SEATS.add("C1"); TAKEN_SEATS.add("C2"); TAKEN_SEATS.add("C3");
        TAKEN_SEATS.add("D5"); TAKEN_SEATS.add("D6");
        TAKEN_SEATS.add("F2"); TAKEN_SEATS.add("F3"); TAKEN_SEATS.add("F4");
        TAKEN_SEATS.add("G7"); TAKEN_SEATS.add("G8");
    }

    private final Set<String> selectedSeats = new HashSet<>();
    private long pricePerSeat;
    private String movieTitle, theaterName, showtimeTime;

    private TextView tvSelectedSeats, tvTotalPrice;
    private MaterialButton btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        // Retrieve data from intent
        movieTitle   = getIntent().getStringExtra("movie_title");
        theaterName  = getIntent().getStringExtra("theater_name");
        showtimeTime = getIntent().getStringExtra("showtime_time");
        pricePerSeat = getIntent().getLongExtra("showtime_price", 120000L);

        setupToolbar();
        setupInfoHeader();
        initViews();
        buildSeatGrid();
        updateSummary();
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupInfoHeader() {
        TextView tvMovie    = findViewById(R.id.tvSeatMovieTitle);
        TextView tvTheater  = findViewById(R.id.tvSeatTheater);
        TextView tvShowtime = findViewById(R.id.tvSeatShowtime);
        if (movieTitle   != null) tvMovie.setText(movieTitle);
        if (theaterName  != null) tvTheater.setText(theaterName);
        if (showtimeTime != null) tvShowtime.setText(showtimeTime);
    }

    private void initViews() {
        tvSelectedSeats = findViewById(R.id.tvSelectedSeats);
        tvTotalPrice    = findViewById(R.id.tvTotalPrice);
        btnConfirm      = findViewById(R.id.btnConfirmSeats);

        btnConfirm.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất 1 ghế", Toast.LENGTH_SHORT).show();
                return;
            }
            navigateToTicket();
        });
    }

    private void buildSeatGrid() {
        GridLayout grid = findViewById(R.id.gridSeats);
        grid.setRowCount(ROWS + 1); // +1 for column header row
        grid.setColumnCount(COLS + 1); // +1 for row label column

        // Column header (numbers 1–8)
        addHeaderCell(grid, "", 0, 0);
        for (int col = 1; col <= COLS; col++) {
            addHeaderCell(grid, String.valueOf(col), 0, col);
        }

        // Seat rows (A–G)
        for (int row = 0; row < ROWS; row++) {
            char rowChar = (char) (FIRST_ROW_CHAR + row);

            // Row label
            addHeaderCell(grid, String.valueOf(rowChar), row + 1, 0);

            // Seat buttons
            for (int col = 1; col <= COLS; col++) {
                String seatLabel = rowChar + String.valueOf(col);
                addSeatView(grid, seatLabel, row + 1, col);
            }
        }
    }

    private void addHeaderCell(GridLayout grid, String text, int row, int col) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextColor(ContextCompat.getColor(this, R.color.text_hint));
        tv.setTextSize(10f);
        tv.setGravity(Gravity.CENTER);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                GridLayout.spec(row), GridLayout.spec(col));
        params.width  = col == 0 ? 28 : (int) (getResources().getDimension(R.dimen.seat_size));
        params.height = row == 0 ? 24 : (int) (getResources().getDimension(R.dimen.seat_size));
        params.setMargins(3, 3, 3, 3);
        tv.setLayoutParams(params);
        grid.addView(tv);
    }

    private void addSeatView(GridLayout grid, String seatLabel, int row, int col) {
        TextView seat = new TextView(this);
        seat.setText(seatLabel);
        seat.setTextSize(8f);
        seat.setGravity(Gravity.CENTER);

        boolean isTaken = TAKEN_SEATS.contains(seatLabel);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                GridLayout.spec(row), GridLayout.spec(col));
        int seatSizePx = (int) getResources().getDimension(R.dimen.seat_size);
        int marginPx   = (int) getResources().getDimension(R.dimen.seat_margin);
        params.width  = seatSizePx;
        params.height = seatSizePx;
        params.setMargins(marginPx, marginPx, marginPx, marginPx);
        seat.setLayoutParams(params);

        if (isTaken) {
            seat.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_seat_taken));
            seat.setTextColor(ContextCompat.getColor(this, R.color.seat_taken_border));
            seat.setEnabled(false);
        } else {
            applySeatAppearance(seat, false);
            seat.setOnClickListener(v -> onSeatClick(seat, seatLabel));
        }

        grid.addView(seat);
    }

    private void onSeatClick(TextView seat, String seatLabel) {
        boolean isNowSelected;
        if (selectedSeats.contains(seatLabel)) {
            selectedSeats.remove(seatLabel);
            isNowSelected = false;
        } else {
            selectedSeats.add(seatLabel);
            isNowSelected = true;
        }

        applySeatAppearance(seat, isNowSelected);

        // Scale animation
        ScaleAnimation scale;
        if (isNowSelected) {
            scale = new ScaleAnimation(1f, 1.15f, 1f, 1.15f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        } else {
            scale = new ScaleAnimation(1.15f, 1f, 1.15f, 1f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        }
        scale.setDuration(150);
        scale.setFillAfter(true);
        seat.startAnimation(scale);

        updateSummary();
    }

    private void applySeatAppearance(TextView seat, boolean selected) {
        if (selected) {
            seat.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_seat_selected));
            seat.setTextColor(Color.BLACK);
        } else {
            seat.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_seat_available));
            seat.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
        }
    }

    private void updateSummary() {
        int count    = selectedSeats.size();
        long total   = count * pricePerSeat;

        if (count == 0) {
            tvSelectedSeats.setText(getString(R.string.seat_none_selected));
        } else {
            List<String> sorted = new ArrayList<>(selectedSeats);
            java.util.Collections.sort(sorted);
            tvSelectedSeats.setText(String.join(", ", sorted));
        }

        tvTotalPrice.setText(String.format("%,d đ", total).replace(',', '.'));
    }

    private void navigateToTicket() {
        List<String> sorted = new ArrayList<>(selectedSeats);
        java.util.Collections.sort(sorted);
        String seatsStr = String.join(", ", sorted);
        long total = selectedSeats.size() * pricePerSeat;

        Intent intent = new Intent(this, TicketActivity.class);
        intent.putExtra("movie_title",   movieTitle);
        intent.putExtra("theater_name",  theaterName);
        intent.putExtra("showtime_time", showtimeTime);
        intent.putExtra("selected_seats", seatsStr);
        intent.putExtra("total_price",   total);
        startActivity(intent);
    }
}

package com.example.miniproject2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btnLogin;
    private TextView tvAuthStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnHomeLogin);
        tvAuthStatus = findViewById(R.id.tvHomeAuthStatus);
        MaterialButton btnMovies = findViewById(R.id.btnHomeMovies);
        MaterialButton btnTheaters = findViewById(R.id.btnHomeTheaters);
        MaterialButton btnShowtimes = findViewById(R.id.btnHomeShowtimes);

        btnLogin.setOnClickListener(v -> handleAuthButtonClick());
        btnMovies.setOnClickListener(v -> startActivity(new Intent(this, MovieListActivity.class)));
        btnTheaters.setOnClickListener(v -> startActivity(new Intent(this, TheaterActivity.class)));
        btnShowtimes.setOnClickListener(v -> startActivity(new Intent(this, ShowtimeActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAuthUi();
    }

    private void handleAuthButtonClick() {
        if (isLoggedIn()) {
            SharedPreferences prefs = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
            prefs.edit()
                    .putBoolean(LoginActivity.KEY_IS_LOGGED_IN, false)
                    .remove(LoginActivity.KEY_USER_ID)
                    .apply();
            Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
            updateAuthUi();
            return;
        }
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void updateAuthUi() {
        SharedPreferences prefs = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean(LoginActivity.KEY_IS_LOGGED_IN, false);
        int userId = prefs.getInt(LoginActivity.KEY_USER_ID, -1);

        if (loggedIn && userId > 0) {
            btnLogin.setText("Đăng xuất");
            tvAuthStatus.setText("Đã đăng nhập • userId: " + userId);
        } else {
            btnLogin.setText("Đăng nhập");
            tvAuthStatus.setText("Bạn chưa đăng nhập");
        }
    }

    private boolean isLoggedIn() {
        SharedPreferences prefs = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
        return prefs.getBoolean(LoginActivity.KEY_IS_LOGGED_IN, false);
    }
}
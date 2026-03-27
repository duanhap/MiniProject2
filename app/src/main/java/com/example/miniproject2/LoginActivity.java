package com.example.miniproject2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject2.dal.AppDatabase;
import com.example.miniproject2.entities.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "movie_booking_prefs";
    public static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    public static final String KEY_USER_ID = "userId";
    public static final String EXTRA_SHOWTIME_ID = "extra_showtime_id";

    private TextInputLayout tilUsername, tilPassword;
    private TextInputEditText etUsername, etPassword;
    private MaterialButton btnLogin;
    private int pendingShowtimeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pendingShowtimeId = getIntent().getIntExtra(EXTRA_SHOWTIME_ID, -1);

        initViews();
        setupListeners();
    }

    private void initViews() {
        tilUsername = findViewById(R.id.tilUsername);
        tilPassword = findViewById(R.id.tilPassword);
        etUsername  = findViewById(R.id.etUsername);
        etPassword  = findViewById(R.id.etPassword);
        btnLogin    = findViewById(R.id.btnLogin);
    }

    private void setupListeners() {
        btnLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        // Clear errors
        tilUsername.setError(null);
        tilPassword.setError(null);

        String username = etUsername.getText() != null
                ? etUsername.getText().toString().trim() : "";
        String password = etPassword.getText() != null
                ? etPassword.getText().toString().trim() : "";

        boolean valid = true;

        if (TextUtils.isEmpty(username)) {
            tilUsername.setError("Vui lòng nhập tên đăng nhập");
            valid = false;
        }

        if (TextUtils.isEmpty(password)) {
            tilPassword.setError("Vui lòng nhập mật khẩu");
            valid = false;
        } else if (password.length() < 4) {
            tilPassword.setError("Mật khẩu ít nhất 4 ký tự");
            valid = false;
        }

        if (!valid) return;

        AppDatabase.databaseWriteExecutor.execute(() -> {
            User user = AppDatabase.getInstance(this).userDAO().login(username, password);
            runOnUiThread(() -> {
                if (user == null) {
                    Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveLoginState(user.getId());
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                navigateAfterLogin();
            });
        });
    }

    private void saveLoginState(int userId) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit()
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .putInt(KEY_USER_ID, userId)
                .apply();
    }

    private void navigateAfterLogin() {
        Intent intent;
        if (pendingShowtimeId != -1) {
            intent = new Intent(this, SeatSelectionActivity.class);
            intent.putExtra("showtimeId", pendingShowtimeId);
        } else {
            intent = new Intent(this, MovieListActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

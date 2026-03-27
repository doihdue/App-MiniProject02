package com.example.miniproject02;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject02.data.AppDatabase;
import com.example.miniproject02.data.model.UserEntity;
import com.example.miniproject02.session.SessionManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());

        TextInputEditText edtUsername = findViewById(R.id.edtUsername);
        TextInputEditText edtPassword = findViewById(R.id.edtPassword);
        MaterialButton btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText() == null ? "" : edtUsername.getText().toString().trim();
            String password = edtPassword.getText() == null ? "" : edtPassword.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            UserEntity user = AppDatabase.getInstance(this).userDao().login(username, password);
            if (user == null) {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                return;
            }

            new SessionManager(this).login(user.id, user.username);
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

            int showtimeId = getIntent().getIntExtra(ShowtimesActivity.EXTRA_SHOWTIME_ID, -1);
            if (showtimeId > 0) {
                Intent intent = new Intent(this, BookTicketActivity.class);
                intent.putExtra(ShowtimesActivity.EXTRA_SHOWTIME_ID, showtimeId);
                startActivity(intent);
            }
            finish();
        });
    }
}

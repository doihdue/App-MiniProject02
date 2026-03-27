package com.example.miniproject02;

import android.os.Bundle;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject02.session.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private MaterialTextView tvWelcome;
    private MaterialButton btnAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnAuth = findViewById(R.id.btnAuth);

        MaterialCardView cardMovies = findViewById(R.id.cardMovies);
        MaterialCardView cardTheaters = findViewById(R.id.cardTheaters);
        MaterialCardView cardShowtimes = findViewById(R.id.cardShowtimes);
        MaterialCardView cardTickets = findViewById(R.id.cardTickets);

        cardMovies.setOnClickListener(v -> startActivity(new Intent(this, MoviesActivity.class)));
        cardTheaters.setOnClickListener(v -> startActivity(new Intent(this, TheatersActivity.class)));
        cardShowtimes.setOnClickListener(v -> startActivity(new Intent(this, ShowtimesActivity.class)));
        cardTickets.setOnClickListener(v -> startActivity(new Intent(this, MyTicketsActivity.class)));

        btnAuth.setOnClickListener(v -> {
            if (sessionManager.isLoggedIn()) {
                sessionManager.logout();
                renderSession();
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderSession();
    }

    private void renderSession() {
        if (sessionManager.isLoggedIn()) {
            tvWelcome.setText("Xin chao, " + sessionManager.getCurrentUsername());
            btnAuth.setText("Dang xuat");
        } else {
            tvWelcome.setText("Chao mung ban den voi Cinema Booking");
            btnAuth.setText("Dang nhap");
        }
    }
}
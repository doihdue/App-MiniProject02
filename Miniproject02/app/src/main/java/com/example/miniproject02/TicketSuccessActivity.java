package com.example.miniproject02;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject02.util.FormatUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class TicketSuccessActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_TITLE = "extra_movie_title";
    public static final String EXTRA_THEATER_INFO = "extra_theater_info";
    public static final String EXTRA_SHOWTIME = "extra_showtime";
    public static final String EXTRA_SEATS = "extra_seats";
    public static final String EXTRA_BOOKED_AT = "extra_booked_at";
    public static final String EXTRA_TOTAL_PRICE = "extra_total_price";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_success);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());

        String movieTitle = getIntent().getStringExtra(EXTRA_MOVIE_TITLE);
        String theaterInfo = getIntent().getStringExtra(EXTRA_THEATER_INFO);
        String showtime = getIntent().getStringExtra(EXTRA_SHOWTIME);
        String seats = getIntent().getStringExtra(EXTRA_SEATS);
        String bookedAt = getIntent().getStringExtra(EXTRA_BOOKED_AT);
        int totalPrice = getIntent().getIntExtra(EXTRA_TOTAL_PRICE, 0);

        MaterialTextView tvMovie = findViewById(R.id.tvMovie);
        MaterialTextView tvTheater = findViewById(R.id.tvTheater);
        MaterialTextView tvShowtime = findViewById(R.id.tvShowtime);
        MaterialTextView tvSeats = findViewById(R.id.tvSeats);
        MaterialTextView tvBookedAt = findViewById(R.id.tvBookedAt);
        MaterialTextView tvTotalPrice = findViewById(R.id.tvTotalPrice);

        tvMovie.setText(TextUtils.isEmpty(movieTitle) ? "Movie" : movieTitle);
        tvTheater.setText(TextUtils.isEmpty(theaterInfo) ? "Theater" : theaterInfo);
        tvShowtime.setText(TextUtils.isEmpty(showtime) ? "--" : showtime);
        tvSeats.setText(TextUtils.isEmpty(seats) ? "--" : seats);
        tvBookedAt.setText(TextUtils.isEmpty(bookedAt) ? "--" : bookedAt);
        tvTotalPrice.setText(FormatUtils.toVnd(totalPrice));

        MaterialButton btnViewMyTickets = findViewById(R.id.btnViewMyTickets);
        MaterialButton btnBackHome = findViewById(R.id.btnBackHome);

        btnViewMyTickets.setOnClickListener(v -> {
            startActivity(new Intent(this, MyTicketsActivity.class));
            finish();
        });

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}

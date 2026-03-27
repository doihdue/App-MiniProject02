package com.example.miniproject02;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject02.data.AppDatabase;
import com.example.miniproject02.data.model.SeatItem;
import com.example.miniproject02.data.model.ShowtimeCard;
import com.example.miniproject02.data.model.ShowtimeEntity;
import com.example.miniproject02.data.model.TicketEntity;
import com.example.miniproject02.session.SessionManager;
import com.example.miniproject02.ui.adapter.SeatAdapter;
import com.example.miniproject02.util.FormatUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class BookTicketActivity extends AppCompatActivity {

    private static final int SEAT_COLUMNS = 8;
    private static final int MIN_TOTAL_SEATS = 24;
    private static final int MAX_TOTAL_SEATS = 96;

    private AppDatabase db;
    private SessionManager sessionManager;
    private ShowtimeCard showtimeCard;
    private final Set<String> selectedSeatLabels = new LinkedHashSet<>();
    private MaterialTextView tvSeats;
    private MaterialTextView tvSelectedSeat;
    private MaterialTextView tvSelectionMeta;
    private MaterialTextView tvTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);

        db = AppDatabase.getInstance(this);
        sessionManager = new SessionManager(this);

        if (!sessionManager.isLoggedIn()) {
            int showtimeId = getIntent().getIntExtra(ShowtimesActivity.EXTRA_SHOWTIME_ID, -1);
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.putExtra(ShowtimesActivity.EXTRA_SHOWTIME_ID, showtimeId);
            startActivity(loginIntent);
            finish();
            return;
        }

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());

        int showtimeId = getIntent().getIntExtra(ShowtimesActivity.EXTRA_SHOWTIME_ID, -1);
        if (showtimeId <= 0) {
            Toast.makeText(this, "Invalid showtime", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        showtimeCard = db.showtimeDao().getShowtimeCardById(showtimeId);
        if (showtimeCard == null) {
            Toast.makeText(this, "Showtime not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        MaterialTextView tvMovie = findViewById(R.id.tvMovie);
        MaterialTextView tvTheater = findViewById(R.id.tvTheater);
        MaterialTextView tvTime = findViewById(R.id.tvTime);
        MaterialTextView tvPrice = findViewById(R.id.tvPrice);
        tvSeats = findViewById(R.id.tvSeats);
        tvSelectedSeat = findViewById(R.id.tvSelectedSeat);
        tvSelectionMeta = findViewById(R.id.tvSelectionMeta);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        RecyclerView rvSeats = findViewById(R.id.rvSeats);
        MaterialButton btnBook = findViewById(R.id.btnBook);

        tvMovie.setText(showtimeCard.movieTitle);
        tvTheater.setText(showtimeCard.theaterName + "  •  " + showtimeCard.room);
        tvTime.setText(showtimeCard.startTime);
        tvPrice.setText(FormatUtils.toVnd(showtimeCard.price));

        setupSeatGrid(rvSeats);

        btnBook.setOnClickListener(v -> {
            if (selectedSeatLabels.isEmpty()) {
                Toast.makeText(this, "Please select at least one seat", Toast.LENGTH_SHORT).show();
                return;
            }

            ShowtimeEntity showtimeEntity = db.showtimeDao().findById(showtimeCard.showtimeId);
            if (showtimeEntity == null || showtimeEntity.availableSeats <= 0) {
                Toast.makeText(this, "No seat available", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedSeatLabels.size() > showtimeEntity.availableSeats) {
                Toast.makeText(this, "Not enough available seats", Toast.LENGTH_SHORT).show();
                return;
            }

            for (String seatLabel : selectedSeatLabels) {
                int seatUsed = db.ticketDao().countTicketByShowtimeAndSeat(showtimeCard.showtimeId, seatLabel);
                if (seatUsed > 0) {
                    Toast.makeText(this, "Some seats are already booked, please re-select", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            String bookedAt = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
            for (String seatLabel : selectedSeatLabels) {
                TicketEntity ticket = new TicketEntity();
                ticket.userId = sessionManager.getCurrentUserId();
                ticket.showtimeId = showtimeCard.showtimeId;
                ticket.seatNumber = seatLabel;
                ticket.bookedAt = bookedAt;

                db.ticketDao().insert(ticket);
            }

            showtimeEntity.availableSeats = showtimeEntity.availableSeats - selectedSeatLabels.size();
            db.showtimeDao().update(showtimeEntity);

            List<String> seats = new ArrayList<>(selectedSeatLabels);
            Collections.sort(seats);

            Intent intent = new Intent(this, TicketSuccessActivity.class);
            intent.putExtra(TicketSuccessActivity.EXTRA_MOVIE_TITLE, showtimeCard.movieTitle);
            intent.putExtra(TicketSuccessActivity.EXTRA_THEATER_INFO, showtimeCard.theaterName + "  •  " + showtimeCard.room);
            intent.putExtra(TicketSuccessActivity.EXTRA_SHOWTIME, showtimeCard.startTime);
            intent.putExtra(TicketSuccessActivity.EXTRA_SEATS, TextUtils.join(", ", seats));
            intent.putExtra(TicketSuccessActivity.EXTRA_BOOKED_AT, bookedAt);
            intent.putExtra(TicketSuccessActivity.EXTRA_TOTAL_PRICE, showtimeCard.price * seats.size());
            startActivity(intent);
            finish();
        });
    }

    private void setupSeatGrid(RecyclerView rvSeats) {
        List<String> bookedSeats = db.ticketDao().getBookedSeatNumbers(showtimeCard.showtimeId);
        int totalSeats = showtimeCard.availableSeats + bookedSeats.size();
        totalSeats = Math.max(MIN_TOTAL_SEATS, totalSeats);
        totalSeats = Math.min(MAX_TOTAL_SEATS, totalSeats);

        List<SeatItem> seatItems = buildSeatItems(totalSeats, bookedSeats);
        tvSeats.setText("Available: " + showtimeCard.availableSeats + " / " + totalSeats);

        SeatAdapter seatAdapter = new SeatAdapter(seatItems, selectedSeats -> {
            selectedSeatLabels.clear();
            selectedSeatLabels.addAll(selectedSeats);
            if (selectedSeatLabels.isEmpty()) {
                tvSelectedSeat.setText("Ban chua chon ghe");
                tvSelectionMeta.setText("0 ghe da chon");
                tvTotalAmount.setText("Tong tam tinh: " + FormatUtils.toVnd(0));
            } else {
                tvSelectedSeat.setText("Seats selected (" + selectedSeatLabels.size() + "): " + TextUtils.join(", ", selectedSeatLabels));
                tvSelectionMeta.setText(selectedSeatLabels.size() + " ghe da chon");
                tvTotalAmount.setText("Tong tam tinh: " + FormatUtils.toVnd(showtimeCard.price * selectedSeatLabels.size()));
            }
        });

        rvSeats.setLayoutManager(new GridLayoutManager(this, SEAT_COLUMNS));
        rvSeats.setAdapter(seatAdapter);
    }

    private List<SeatItem> buildSeatItems(int totalSeats, List<String> bookedSeats) {
        Set<String> occupiedSeatSet = new HashSet<>();
        for (String seat : bookedSeats) {
            occupiedSeatSet.add(seat.toUpperCase(Locale.ROOT));
        }

        List<SeatItem> result = new ArrayList<>();
        for (int i = 0; i < totalSeats; i++) {
            int rowIndex = i / SEAT_COLUMNS;
            int seatIndex = (i % SEAT_COLUMNS) + 1;
            String seatLabel = String.valueOf((char) ('A' + rowIndex)) + seatIndex;
            result.add(new SeatItem(seatLabel, occupiedSeatSet.contains(seatLabel)));
        }
        return result;
    }
}

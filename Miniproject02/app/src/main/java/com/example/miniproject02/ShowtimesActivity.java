package com.example.miniproject02;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject02.data.AppDatabase;
import com.example.miniproject02.data.model.ShowtimeCard;
import com.example.miniproject02.ui.adapter.ShowtimeAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class ShowtimesActivity extends AppCompatActivity {

    public static final String EXTRA_SHOWTIME_ID = "extra_showtime_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtimes);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.rvShowtimes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ShowtimeCard> items = AppDatabase.getInstance(this).showtimeDao().getAllShowtimeCards();
        ShowtimeAdapter adapter = new ShowtimeAdapter(items, item -> {
            Intent intent = new Intent(this, BookTicketActivity.class);
            intent.putExtra(EXTRA_SHOWTIME_ID, item.showtimeId);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }
}

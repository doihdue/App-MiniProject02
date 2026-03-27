package com.example.miniproject02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject02.data.AppDatabase;
import com.example.miniproject02.data.model.TicketCard;
import com.example.miniproject02.session.SessionManager;
import com.example.miniproject02.ui.adapter.TicketAdapter;
import com.example.miniproject02.util.FormatUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class MyTicketsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());

        SessionManager sessionManager = new SessionManager(this);
        if (!sessionManager.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        RecyclerView recyclerView = findViewById(R.id.rvTickets);
        MaterialTextView emptyView = findViewById(R.id.tvEmpty);
        MaterialTextView tvTicketCount = findViewById(R.id.tvTicketCount);
        MaterialTextView tvTotalSpent = findViewById(R.id.tvTotalSpent);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<TicketCard> tickets = AppDatabase.getInstance(this)
                .ticketDao()
                .getTicketsForUser(sessionManager.getCurrentUserId());

        int totalSpent = 0;
        for (TicketCard ticket : tickets) {
            totalSpent += ticket.price;
        }
        tvTicketCount.setText(String.valueOf(tickets.size()));
        tvTotalSpent.setText(FormatUtils.toVnd(totalSpent));

        if (tickets.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
        recyclerView.setAdapter(new TicketAdapter(tickets));
    }
}

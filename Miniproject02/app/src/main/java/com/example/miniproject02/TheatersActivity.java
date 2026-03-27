package com.example.miniproject02;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject02.data.AppDatabase;
import com.example.miniproject02.data.model.TheaterEntity;
import com.example.miniproject02.ui.adapter.TheaterAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class TheatersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theaters);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.rvTheaters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<TheaterEntity> theaters = AppDatabase.getInstance(this).theaterDao().getAll();
        recyclerView.setAdapter(new TheaterAdapter(theaters));
    }
}

package com.example.miniproject02;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject02.data.AppDatabase;
import com.example.miniproject02.data.model.MovieEntity;
import com.example.miniproject02.ui.adapter.MovieAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class MoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.rvMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<MovieEntity> movies = AppDatabase.getInstance(this).movieDao().getAll();
        recyclerView.setAdapter(new MovieAdapter(movies));
    }
}

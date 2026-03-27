package com.example.miniproject02.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject02.R;
import com.example.miniproject02.data.model.TheaterEntity;

import java.util.List;

public class TheaterAdapter extends RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder> {

    private final List<TheaterEntity> items;

    public TheaterAdapter(List<TheaterEntity> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public TheaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theater, parent, false);
        return new TheaterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheaterViewHolder holder, int position) {
        TheaterEntity theater = items.get(position);
        holder.tvName.setText(theater.name);
        holder.tvAddress.setText(theater.address);
        holder.tvCity.setText(theater.city);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class TheaterViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvAddress;
        TextView tvCity;

        TheaterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvTheaterName);
            tvAddress = itemView.findViewById(R.id.tvTheaterAddress);
            tvCity = itemView.findViewById(R.id.tvTheaterCity);
        }
    }
}

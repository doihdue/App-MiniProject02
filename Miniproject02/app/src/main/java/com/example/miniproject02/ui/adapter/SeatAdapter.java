package com.example.miniproject02.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject02.R;
import com.example.miniproject02.data.model.SeatItem;
import com.google.android.material.card.MaterialCardView;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {

    public interface OnSeatSelectedListener {
        void onSeatSelectionChanged(Set<String> selectedSeats);
    }

    private final List<SeatItem> items;
    private final OnSeatSelectedListener listener;
    private final Set<String> selectedSeats = new LinkedHashSet<>();

    public SeatAdapter(List<SeatItem> items, OnSeatSelectedListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        SeatItem item = items.get(position);
        Context context = holder.itemView.getContext();
        boolean isSelected = selectedSeats.contains(item.seatLabel);

        holder.tvSeatLabel.setText(item.seatLabel);

        if (item.occupied) {
            holder.cardSeat.setCardBackgroundColor(ContextCompat.getColor(context, R.color.seat_occupied));
            holder.tvSeatLabel.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.itemView.setAlpha(0.75f);
            holder.itemView.setOnClickListener(null);
        } else if (isSelected) {
            holder.cardSeat.setCardBackgroundColor(ContextCompat.getColor(context, R.color.seat_selected));
            holder.tvSeatLabel.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.itemView.setAlpha(1f);
            holder.itemView.setOnClickListener(v -> {
                selectedSeats.remove(item.seatLabel);
                notifyDataSetChanged();
                listener.onSeatSelectionChanged(new LinkedHashSet<>(selectedSeats));
            });
        } else {
            holder.cardSeat.setCardBackgroundColor(ContextCompat.getColor(context, R.color.seat_available));
            holder.tvSeatLabel.setTextColor(ContextCompat.getColor(context, R.color.text_primary));
            holder.itemView.setAlpha(1f);
            holder.itemView.setOnClickListener(v -> {
                selectedSeats.add(item.seatLabel);
                notifyDataSetChanged();
                listener.onSeatSelectionChanged(new LinkedHashSet<>(selectedSeats));
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class SeatViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardSeat;
        TextView tvSeatLabel;

        SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            cardSeat = itemView.findViewById(R.id.cardSeat);
            tvSeatLabel = itemView.findViewById(R.id.tvSeatLabel);
        }
    }
}

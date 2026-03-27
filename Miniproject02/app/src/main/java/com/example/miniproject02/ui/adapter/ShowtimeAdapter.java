package com.example.miniproject02.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject02.R;
import com.example.miniproject02.data.model.ShowtimeCard;
import com.example.miniproject02.util.FormatUtils;

import java.util.List;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ShowtimeViewHolder> {

    public interface OnShowtimeClickListener {
        void onShowtimeClick(ShowtimeCard item);
    }

    private final List<ShowtimeCard> items;
    private final OnShowtimeClickListener listener;

    public ShowtimeAdapter(List<ShowtimeCard> items, OnShowtimeClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShowtimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showtime, parent, false);
        return new ShowtimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowtimeViewHolder holder, int position) {
        ShowtimeCard item = items.get(position);
        holder.tvMovieTitle.setText(item.movieTitle);
        holder.tvTheaterInfo.setText(item.theaterName + "  •  " + item.room);
        holder.tvStartTime.setText(item.startTime);
        holder.tvPrice.setText(FormatUtils.toVnd(item.price));
        holder.tvSeats.setText("Available: " + item.availableSeats);
        holder.itemView.setOnClickListener(v -> listener.onShowtimeClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ShowtimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieTitle;
        TextView tvTheaterInfo;
        TextView tvStartTime;
        TextView tvPrice;
        TextView tvSeats;

        ShowtimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tvShowtimeMovieTitle);
            tvTheaterInfo = itemView.findViewById(R.id.tvShowtimeTheaterInfo);
            tvStartTime = itemView.findViewById(R.id.tvShowtimeStartTime);
            tvPrice = itemView.findViewById(R.id.tvShowtimePrice);
            tvSeats = itemView.findViewById(R.id.tvShowtimeSeats);
        }
    }
}

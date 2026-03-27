package com.example.miniproject02.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject02.R;
import com.example.miniproject02.data.model.TicketCard;
import com.example.miniproject02.util.FormatUtils;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private final List<TicketCard> items;

    public TicketAdapter(List<TicketCard> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        TicketCard item = items.get(position);
        holder.tvStatus.setText("ACTIVE");
        holder.tvMovieTitle.setText(item.movieTitle);
        holder.tvInfo.setText(item.theaterName + "  •  " + item.startTime);
        holder.tvSeat.setText("Seat: " + item.seatNumber);
        holder.tvPrice.setText(FormatUtils.toVnd(item.price));
        holder.tvBookedAt.setText("Booked at: " + item.bookedAt);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView tvStatus;
        TextView tvMovieTitle;
        TextView tvInfo;
        TextView tvSeat;
        TextView tvPrice;
        TextView tvBookedAt;

        TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.tvTicketStatus);
            tvMovieTitle = itemView.findViewById(R.id.tvTicketMovieTitle);
            tvInfo = itemView.findViewById(R.id.tvTicketInfo);
            tvSeat = itemView.findViewById(R.id.tvTicketSeat);
            tvPrice = itemView.findViewById(R.id.tvTicketPrice);
            tvBookedAt = itemView.findViewById(R.id.tvTicketBookedAt);
        }
    }
}

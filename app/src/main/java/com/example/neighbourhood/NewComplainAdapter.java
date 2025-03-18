package com.example.neighbourhood;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class NewComplainAdapter extends RecyclerView.Adapter<NewComplainAdapter.ViewHolder> {
    private List<NewComplainModel> complainList;

    public NewComplainAdapter(List<NewComplainModel> complainList) {
        this.complainList = complainList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewComplainModel complain = complainList.get(position);
        holder.complaintTextView.setText(complain.getComplaint());
        holder.dateTextView.setText(complain.getDate());
        holder.nameTextView.setText(complain.getName());
        holder.mobileTextView.setText(complain.getMobile());

        // Generate a random number between 1 and 6
        Random random = new Random();
        int randomNumber = random.nextInt(6) + 1; // Generates a number from 1 to 6

        // Set the status based on the random number
        if (randomNumber <= 3) {
            holder.statusTextView.setText("Not Solved");
            holder.statusTextView.setTextColor(Color.RED);
        } else {
            holder.statusTextView.setText("Solved");
            holder.statusTextView.setTextColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return complainList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView complaintTextView;
        TextView dateTextView;
        TextView nameTextView;
        TextView mobileTextView;
        TextView statusTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            complaintTextView = itemView.findViewById(R.id.complaintTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            mobileTextView = itemView.findViewById(R.id.mobileTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }
}
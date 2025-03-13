package com.example.neighbourhood;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder> {

    private final List<Complaint> complaintList;

    public ComplaintAdapter(List<Complaint> complaintList) {
        this.complaintList = complaintList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complaint, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Complaint complaint = complaintList.get(position);
        holder.tvComplaint.setText(complaint.getComplaint());
        holder.tvDate.setText("Date: " + complaint.getDate());

        holder.take_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvStatus.setText("Solved");
                holder.tvStatus.setTextColor(Color.GREEN); // Using predefined green color
            }
        });

    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvComplaint, tvDate,tvStatus;
        Button take_action;

        ViewHolder(View itemView) {
            super(itemView);
            tvComplaint = itemView.findViewById(R.id.tvComplaint);
            tvDate = itemView.findViewById(R.id.tvDate);
            take_action=itemView.findViewById(R.id.take_action);
            tvStatus=itemView.findViewById(R.id.tvStatus);
        }
    }
}

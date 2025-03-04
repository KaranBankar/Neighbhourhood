package com.example.neighbourhood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MaintenanceAdapter extends RecyclerView.Adapter<MaintenanceAdapter.ViewHolder> {

    private Context context;
    private List<MaintenanceModel> maintenanceList;

    public MaintenanceAdapter(Context context, List<MaintenanceModel> maintenanceList) {
        this.context = context;
        this.maintenanceList = maintenanceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_maintenance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MaintenanceModel maintenance = maintenanceList.get(position);
        holder.tvName.setText("Name: " + maintenance.getName());
        holder.tvDate.setText("Date: " + maintenance.getDate());
        holder.tvAmount.setText("Amount: ₹" + maintenance.getAmount());
        holder.tvDeadline.setText("Deadline: " + maintenance.getDeadlineDate());
    }

    @Override
    public int getItemCount() {
        return maintenanceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate, tvAmount, tvDeadline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDeadline = itemView.findViewById(R.id.tvDeadline);
        }
    }
}

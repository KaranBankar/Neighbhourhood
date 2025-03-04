package com.example.neighbourhood;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserMaintenanceAdapter extends RecyclerView.Adapter<UserMaintenanceAdapter.ViewHolder> {

    private Context context;
    private List<UserMaintenanceModel> maintenanceList;

    public UserMaintenanceAdapter(Context context, List<UserMaintenanceModel> maintenanceList) {
        this.context = context;
        this.maintenanceList = maintenanceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_maintenance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserMaintenanceModel maintenance = maintenanceList.get(position);
        holder.tvName.setText("Name: " + maintenance.getName());
        holder.tvDate.setText("Date: " + maintenance.getDate());
        holder.tvAmount.setText("Amount: ₹" + maintenance.getAmount());
        holder.tvDeadline.setText("Deadline: " + maintenance.getDeadlineDate());

        holder.btnPay.setOnClickListener(v -> {
            String amount = maintenance.getAmount();  // Amount to be paid
            String upiId = "9207879687@ybl";  // Receiver's UPI ID (Ensure it's valid)
            String payeeName = "Maintenance Payment";
            String transactionNote = "Maintenance Fees";

            Uri uri = Uri.parse("upi://pay")
                    .buildUpon()
                    .appendQueryParameter("pa", upiId)  // Payee UPI ID
                    .appendQueryParameter("pn", payeeName)  // Payee Name
                    .appendQueryParameter("tn", transactionNote)  // Transaction Note
                    .appendQueryParameter("am", amount)  // Amount
                    .appendQueryParameter("cu", "INR")  // Currency
                    .appendQueryParameter("tr", "TXN" + System.currentTimeMillis())  // Unique Transaction ID (optional)
                    .build();

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            Intent chooser = Intent.createChooser(intent, "Pay with UPI");

            try {
                context.startActivity(chooser);
            } catch (Exception e) {
                Toast.makeText(context, "No UPI app found!", Toast.LENGTH_SHORT).show();
            }
        });





    }

    @Override
    public int getItemCount() {
        return maintenanceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate, tvAmount, tvDeadline;
        Button btnPay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDeadline = itemView.findViewById(R.id.tvDeadline);
            btnPay = itemView.findViewById(R.id.btnPay);
        }
    }
}

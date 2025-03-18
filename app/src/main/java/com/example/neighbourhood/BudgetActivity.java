package com.example.neighbourhood;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;
import java.util.Random;

public class BudgetActivity extends AppCompatActivity {

    private TextView totalAmountTextView;
    private TextView spendOnEventsTextView;
    private TextView spendOnMaintenanceTextView;
    private TextView remainingAmountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budget);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize TextViews
        totalAmountTextView = findViewById(R.id.totalAmountTextView);
        spendOnEventsTextView = findViewById(R.id.spendOnEventsTextView);
        spendOnMaintenanceTextView = findViewById(R.id.spendOnMaintenanceTextView);
        remainingAmountTextView = findViewById(R.id.remainingAmountTextView);

        displayBudgetData();
    }

    private void displayBudgetData() {
        // Generate random budget data
        Random random = new Random();
        int totalAmount = 10000; // Total amount in the society
        int spendOnEvents = random.nextInt(totalAmount / 2); // Random spend on events (up to 50% of total)
        int spendOnMaintenance = random.nextInt(totalAmount - spendOnEvents); // Random spend on maintenance
        int remainingAmount = totalAmount - spendOnEvents - spendOnMaintenance; // Remaining amount

        // Display the data in TextViews
        totalAmountTextView.setText("Total Amount: " + totalAmount);
        spendOnEventsTextView.setText("Spend on Events: " + spendOnEvents);
        spendOnMaintenanceTextView.setText("Spend on Maintenance: " + spendOnMaintenance);
        remainingAmountTextView.setText("Remaining Amount: " + remainingAmount);
    }
}
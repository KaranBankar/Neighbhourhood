package com.example.neighbourhood;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserComplainActivity extends AppCompatActivity {

    private FloatingActionButton fabAddComplaint;
    private RecyclerView recyclerViewComplaints;
    private DatabaseReference databaseComplaints;
    private ComplaintAdapter adapter;
    private List<Complaint> complaintList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_complain);

        fabAddComplaint = findViewById(R.id.fabAddComplaint);
        recyclerViewComplaints = findViewById(R.id.recyclerViewComplaints);
        recyclerViewComplaints.setLayoutManager(new LinearLayoutManager(this));

        databaseComplaints = FirebaseDatabase.getInstance().getReference("complaints");

        complaintList = new ArrayList<>();
        adapter = new ComplaintAdapter(complaintList);
        recyclerViewComplaints.setAdapter(adapter);

        fabAddComplaint.setOnClickListener(view -> showAddComplaintDialog());

        fetchComplaints();
    }

    private void showAddComplaintDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_complaint, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.et_complaint_name);
        EditText etMobile = dialogView.findViewById(R.id.et_complaint_mobile);
        EditText etDate = dialogView.findViewById(R.id.et_complaint_date);
        EditText etComplaint = dialogView.findViewById(R.id.et_complaint);

        etDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year1, month1, dayOfMonth) -> etDate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                    year, month, day);
            datePickerDialog.show();
        });

        builder.setPositiveButton("Submit", (dialog, which) -> {
            String name = etName.getText().toString().trim();
            String mobile = etMobile.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String complaintText = etComplaint.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(date) || TextUtils.isEmpty(complaintText)) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            String id = databaseComplaints.push().getKey();
            Complaint complaint = new Complaint(id, name, mobile, date, complaintText);

            assert id != null;
            databaseComplaints.child(id).setValue(complaint).addOnSuccessListener(aVoid ->
                    Toast.makeText(UserComplainActivity.this, "Complaint submitted", Toast.LENGTH_SHORT).show()
            );
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void fetchComplaints() {
        databaseComplaints.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaintList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Complaint complaint = postSnapshot.getValue(Complaint.class);
                    complaintList.add(complaint);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserComplainActivity.this, "Failed to load complaints", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

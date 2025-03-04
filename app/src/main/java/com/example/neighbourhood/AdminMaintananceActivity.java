package com.example.neighbourhood;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class AdminMaintananceActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private MaintenanceAdapter adapter;
    private ArrayList<MaintenanceModel> maintenanceList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintanance);

        fab = findViewById(R.id.fab_add);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        maintenanceList = new ArrayList<>();
        adapter = new MaintenanceAdapter(this, maintenanceList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Maintenance");

        fab.setOnClickListener(view -> showAddMaintenanceDialog());

        fetchData();
    }

    private void showAddMaintenanceDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_maintenance);

        EditText etName = dialog.findViewById(R.id.etName);
        EditText etDate = dialog.findViewById(R.id.etDate);
        EditText etAmount = dialog.findViewById(R.id.etAmount);
        EditText etDeadlineDate = dialog.findViewById(R.id.etDeadlineDate);
        Button btnSave = dialog.findViewById(R.id.btnSave);

        etDate.setOnClickListener(view -> showDatePicker(etDate));
        etDeadlineDate.setOnClickListener(view -> showDatePicker(etDeadlineDate));

        btnSave.setOnClickListener(view -> {
            String name = etName.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String amount = etAmount.getText().toString().trim();
            String deadlineDate = etDeadlineDate.getText().toString().trim();

            if (name.isEmpty() || date.isEmpty() || amount.isEmpty() || deadlineDate.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            String id = databaseReference.push().getKey();
            MaintenanceModel maintenance = new MaintenanceModel(id, name, date, amount, deadlineDate);
            databaseReference.child(id).setValue(maintenance)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to Save", Toast.LENGTH_SHORT).show());
        });

        dialog.show();
    }

    private void showDatePicker(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            editText.setText(selectedDate);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void fetchData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                maintenanceList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    MaintenanceModel maintenance = data.getValue(MaintenanceModel.class);
                    maintenanceList.add(maintenance);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminMaintananceActivity.this, "Failed to Load Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

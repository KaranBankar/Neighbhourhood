package com.example.neighbourhood;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdminEventsActivity extends AppCompatActivity {

    private FloatingActionButton fabAddEvent;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<EventModel> eventList = new ArrayList<>();
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private Uri imageUri; // Stores selected image URI
    private ActivityResultLauncher<Intent> imagePickerLauncher; // For image selection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_events);

        fabAddEvent = findViewById(R.id.fabAddEvent);
        recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference("Events");
        storageReference = FirebaseStorage.getInstance().getReference("EventImages");

        eventAdapter = new EventAdapter(this, eventList);
        recyclerView.setAdapter(eventAdapter);

        fabAddEvent.setOnClickListener(v -> openAddEventDialog());

        fetchEvents(); // Load events from Firebase

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                    }
                }
        );
    }

    private void openAddEventDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_event, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        EditText etEventName = view.findViewById(R.id.etEventName);
        EditText etEventDate = view.findViewById(R.id.etEventDate);
        EditText etEventTime = view.findViewById(R.id.etEventTime);
        EditText etEventDescription = view.findViewById(R.id.etEventDescription);
        //ImageView ivEventImage = view.findViewById(R.id.ivEventImage);
        Button btnSaveEvent = view.findViewById(R.id.btnSaveEvent);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        etEventDate.setOnClickListener(v -> showDatePicker(etEventDate));
        etEventTime.setOnClickListener(v -> showTimePicker(etEventTime));

        //ivEventImage.setOnClickListener(v -> selectImage());

        btnSaveEvent.setOnClickListener(v -> {
            String name = etEventName.getText().toString().trim();
            String date = etEventDate.getText().toString().trim();
            String time = etEventTime.getText().toString().trim();
            String description = etEventDescription.getText().toString().trim();

            if (name.isEmpty() || date.isEmpty() || time.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            // Generate unique event ID
            String eventId = databaseReference.push().getKey();

            // Create EventModel object without image URL
            EventModel event = new EventModel(eventId, name, date, time, description, "");

            // Save event to Firebase Database
            databaseReference.child(eventId).setValue(event)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        dialog.dismiss();
                        Toast.makeText(this, "Event added successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Failed to add event: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

    }

    private void fetchEvents() {
        databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    EventModel event = data.getValue(EventModel.class);
                    eventList.add(event);
                }
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminEventsActivity.this, "Failed to load events", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePicker(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (DatePicker view, int year, int month, int dayOfMonth) -> {
            editText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showTimePicker(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (TimePicker view, int hourOfDay, int minute) -> {
            editText.setText(hourOfDay + ":" + minute);
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        timePickerDialog.show();
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }
}

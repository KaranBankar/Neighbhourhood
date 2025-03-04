package com.example.neighbourhood;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class AdminComplaintActivity extends AppCompatActivity {

    private RecyclerView adminRecyclerView;
    private ComplaintAdapter complaintAdapter;
    private List<Complaint> complaintList = new ArrayList<>();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_complaint);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adminRecyclerView = findViewById(R.id.adminRecyclerView);
        adminRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        complaintAdapter = new ComplaintAdapter(complaintList);
        adminRecyclerView.setAdapter(complaintAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("complaints");

        fetchComplaints();
    }

    private void fetchComplaints() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaintList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Complaint complaint = dataSnapshot.getValue(Complaint.class);
                    if (complaint != null) {
                        complaintList.add(complaint);
                    }
                }
                complaintAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read complaints", error.toException());
            }
        });
    }
}

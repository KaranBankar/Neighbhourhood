package com.example.neighbourhood;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class UserComplainNewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NewComplainAdapter adapter;
    private List<NewComplainModel> complainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_complain_new);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        complainList = new ArrayList<>();
        adapter = new NewComplainAdapter(complainList);
        recyclerView.setAdapter(adapter);
        fetchComplaints();
    }

    private void fetchComplaints() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("complaints");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                complainList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NewComplainModel complain = snapshot.getValue(NewComplainModel.class);
                    if (complain != null) {
                        complainList.add(complain);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }
}
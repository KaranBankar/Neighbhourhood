package com.example.neighbourhood;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.neighbourhood.EmergencyContactAdapter;
import com.example.neighbourhood.EmergencyContact;
import java.util.ArrayList;
import java.util.List;

public class UserHelpdeskActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<EmergencyContact> contactList;
    private EmergencyContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_helpdesk);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load contacts
        loadEmergencyContacts();
    }

    private void loadEmergencyContacts() {
        contactList = new ArrayList<>();
        contactList.add(new EmergencyContact("Police", "100"));
        contactList.add(new EmergencyContact("Ambulance", "108"));
        contactList.add(new EmergencyContact("Fire Brigade", "101"));
        contactList.add(new EmergencyContact("Women Helpline", "1091"));
        contactList.add(new EmergencyContact("Disaster Helpline", "1078"));

        adapter = new EmergencyContactAdapter(this, contactList);
        recyclerView.setAdapter(adapter);
    }
}

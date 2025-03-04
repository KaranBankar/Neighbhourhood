package com.example.neighbourhood;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserHomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private Toolbar toolbar;
    private MaterialCardView mainteinance, events, helpdesk, complain;
    private TextView tvNotice;
    private DatabaseReference noticeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_home); // Ensure this matches your XML file name

        // Initialize views
        mainteinance = findViewById(R.id.maintainance);
        events = findViewById(R.id.events);
        helpdesk = findViewById(R.id.helpdesk);
        complain = findViewById(R.id.complain);
        tvNotice = findViewById(R.id.tv_notice); // Make sure your TextView ID matches in XML

        // Firebase reference
        noticeRef = FirebaseDatabase.getInstance().getReference("Notice");

        // Fetch notice from Firebase
        fetchNoticeFromFirebase();

        // Set click listeners
        mainteinance.setOnClickListener(v -> startActivity(new Intent(UserHomeActivity.this, UserMaintenanceActivity.class)));
        events.setOnClickListener(v -> startActivity(new Intent(UserHomeActivity.this, UserEventsActivity.class)));
        helpdesk.setOnClickListener(v -> startActivity(new Intent(UserHomeActivity.this, UserHelpdeskActivity.class)));
        complain.setOnClickListener(v -> startActivity(new Intent(UserHomeActivity.this, UserComplainActivity.class)));

        drawerLayout = findViewById(R.id.main);
        toolbar = findViewById(R.id.toolbar);
        navView = findViewById(R.id.nav_view);

        setupToolbar();
        setupNavigationDrawer();
    }

    private void fetchNoticeFromFirebase() {
        noticeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String noticeText = dataSnapshot.getValue(String.class);
                    tvNotice.setText(noticeText);
                } else {
                    tvNotice.setText("No Notice Available");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserHomeActivity.this, "Failed to load notice!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupNavigationDrawer() {
        navView.setNavigationItemSelectedListener(menuItem -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

package com.example.neighbourhood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
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

    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

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
        setContentView(R.layout.activity_user_home);

        // Initialize views
        mainteinance = findViewById(R.id.maintainance);
        events = findViewById(R.id.events);
        helpdesk = findViewById(R.id.helpdesk);
        complain = findViewById(R.id.complain);
        tvNotice = findViewById(R.id.tv_notice);

        // Firebase reference
        noticeRef = FirebaseDatabase.getInstance().getReference("Notice");

        // Fetch notice from Firebase
        fetchNoticeFromFirebase();

        // Set click listeners
        mainteinance.setOnClickListener(v -> startActivity(new Intent(UserHomeActivity.this, UserMaintenanceActivity.class)));
        events.setOnClickListener(v -> startActivity(new Intent(UserHomeActivity.this, UserEventsActivity.class)));
        helpdesk.setOnClickListener(v -> startActivity(new Intent(UserHomeActivity.this, UserHelpdeskActivity.class)));
        complain.setOnClickListener(v -> startActivity(new Intent(UserHomeActivity.this, UserComplainNewActivity.class)));

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
            int id = menuItem.getItemId();

            if (id == R.id.nav_logout) {
                logoutUser();
            }
            if(id==R.id.nav_aboutus){
                Intent i=new Intent(UserHomeActivity.this,AboutUSActivity.class);
                startActivity(i);
            }if(id==R.id.nav_Budget){
                Intent i=new Intent(UserHomeActivity.this,BudgetActivity.class);
                startActivity(i);
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void logoutUser() {
        // Clear login state
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Redirect to LoginActivity
        Intent intent = new Intent(UserHomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
        startActivity(intent);
        finish();
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

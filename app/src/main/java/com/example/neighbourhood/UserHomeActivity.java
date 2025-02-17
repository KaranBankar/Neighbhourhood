package com.example.neighbourhood;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;

public class UserHomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private Toolbar toolbar;

    private MaterialCardView mainteinance,events,helpdesk,complain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_home); // Ensure this matches your XML file name

        mainteinance=findViewById(R.id.maintainance);
        events=findViewById(R.id.events);
        helpdesk=findViewById(R.id.helpdesk);
        complain=findViewById(R.id.complain);

        mainteinance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i=new Intent(UserHomeActivity.this,UserMaintenanceActivity.class);
                startActivity(i);
            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i=new Intent(UserHomeActivity.this, UserEventsActivity.class);
                startActivity(i);
            }
        });

        helpdesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i=new Intent(UserHomeActivity.this,UserHelpdeskActivity.class);
                startActivity(i);
            }
        });

        complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i=new Intent(UserHomeActivity.this,UserComplainActivity.class);
                startActivity(i);
            }
        });
        // Initialize views
        drawerLayout = findViewById(R.id.main);
        toolbar = findViewById(R.id.toolbar);
        navView = findViewById(R.id.nav_view);

        setupToolbar();
        setupNavigationDrawer();
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
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Handle menu item clicks here (e.g., open new activities)

                // Close drawer after selection
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
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

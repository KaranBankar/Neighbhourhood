package com.example.neighbourhood;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminHomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private Toolbar toolbar;
    private ImageView add_member;
    private EditText etNotice;
    private DatabaseReference noticeRef;
    private MaterialCardView manage_complains,add_maintance,admin_helpdesk;

    private MaterialCardView admin_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);

        drawerLayout = findViewById(R.id.main);
        toolbar = findViewById(R.id.toolbar);
        navView = findViewById(R.id.nav_view);
        etNotice = findViewById(R.id.et_notice);
        manage_complains=findViewById(R.id.manage_complains);
        add_maintance=findViewById(R.id.add_Maintanace);
        admin_event=findViewById(R.id.admin_evets);
        admin_helpdesk=findViewById(R.id.admin_helpdesk);


        // Firebase reference
        noticeRef = FirebaseDatabase.getInstance().getReference("Notice");

        setupToolbar();
        setupNavigationDrawer();

        add_member = findViewById(R.id.add_member);
        add_member.setOnClickListener(v -> {
            Intent i = new Intent(AdminHomeActivity.this, MemberSignupActivity.class);
            startActivity(i);
        });

        add_maintance.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this, AdminMaintananceActivity.class)));
        manage_complains.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this, AdminComplaintActivity.class)));
        admin_event.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this, AdminEventsActivity.class)));

        admin_helpdesk.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this, UserHelpdeskActivity.class)));
        // Auto-save notice whenever the admin types
        etNotice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveNoticeToFirebase(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });

    }

    private void saveNoticeToFirebase(String noticeText) {
        if (!noticeText.isEmpty()) {
            noticeRef.setValue(noticeText).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    //Toast.makeText(AdminHomeActivity.this, "Notice updated!", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(AdminHomeActivity.this, "Failed to update notice!", Toast.LENGTH_SHORT).show();
                }
            });
        }
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

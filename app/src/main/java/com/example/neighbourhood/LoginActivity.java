package com.example.neighbourhood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText etMobile, etPassword;
    private CardView btn_login;
    private TextView donthaveaccount;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Initialize views
        etMobile = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        btn_login = findViewById(R.id.btnLogin);
        donthaveaccount = findViewById(R.id.donthaveacc);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Members");

        donthaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, AdminUserActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = etMobile.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (!validateInputs(mobile, password)) {
                    return;
                }

                    if (mobile.equals("9307879687") && password.equals("Pass@123")) {
                        startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
                        finish();
                        return;
                    }
                else {
                        loginUser();
                    }
            }
        });
    }

    private void loginUser() {


        // Check if admin credentials match
        String mobile = etMobile.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Check in Firebase Database
        databaseReference.child(mobile).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String storedPassword = dataSnapshot.child("password").getValue(String.class);
                    if (storedPassword != null && storedPassword.equals(password)) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, UserHomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInputs(String mobile, String password) {
        if (mobile.isEmpty() || !mobile.matches("^\\d{10}$")) {
            etMobile.setError("Enter a valid 10-digit mobile number.");
            return false;
        }
        if (password.isEmpty() || password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters long.");
            return false;
        }
        return true;
    }
}
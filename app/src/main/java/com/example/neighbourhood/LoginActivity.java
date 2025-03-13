package com.example.neighbourhood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_TYPE = "userType";
    private static final String KEY_MOBILE = "mobile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Initialize views
        etMobile = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        btn_login = findViewById(R.id.btnLogin);
        //donthaveaccount = findViewById(R.id.donthaveacc);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Members");

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Check if the user is already logged in
        if (sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)) {
            redirectToHome();
        }

//        donthaveaccount.setOnClickListener(v -> {
//            Intent i = new Intent(LoginActivity.this, AdminUserActivity.class);
//            startActivity(i);
//            finish();
//        });

        btn_login.setOnClickListener(v -> {
            String mobile = etMobile.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (!validateInputs(mobile, password)) {
                return;
            }

            if (mobile.equals("9307879687") && password.equals("Pass@123")) {
                saveLoginState("admin", mobile);
                startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
                finish();
            } else {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String mobile = etMobile.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        databaseReference.child(mobile).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String storedPassword = dataSnapshot.child("password").getValue(String.class);
                    if (storedPassword != null && storedPassword.equals(password)) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        // Save login state
                        saveLoginState("user", mobile);

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

    private void saveLoginState(String userType, String mobile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USER_TYPE, userType);
        editor.putString(KEY_MOBILE, mobile);
        editor.apply();
    }

    private void redirectToHome() {
        String userType = sharedPreferences.getString(KEY_USER_TYPE, "");
        if ("admin".equals(userType)) {
            startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
        } else {
            startActivity(new Intent(LoginActivity.this, UserHomeActivity.class));
        }
        finish();
    }
}

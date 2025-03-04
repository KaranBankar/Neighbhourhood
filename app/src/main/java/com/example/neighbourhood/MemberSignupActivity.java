package com.example.neighbourhood;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MemberSignupActivity extends AppCompatActivity {

    private EditText etName, etMobile, etEmail, etMemberId, etPassword;
    private Button btnSubmit;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_signup);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Members");

        // Initialize UI components
        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        etEmail = findViewById(R.id.etEmail);
        etMemberId = findViewById(R.id.etAddress);  // Assuming this is for Member ID
        etPassword = findViewById(R.id.etPassword);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Handle Submit Button Click
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMemberData();
            }
        });
    }

    private void saveMemberData() {
        String name = etName.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String memberId = etMemberId.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validate Input
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(memberId) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a Member Object
        Member member = new Member(name, mobile, email, memberId, password);

        // Save data to Firebase
        databaseReference.child(memberId).setValue(member)
                .addOnSuccessListener(aVoid -> Toast.makeText(MemberSignupActivity.this, "Member added successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(MemberSignupActivity.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent i=new Intent(MemberSignupActivity.this,AdminSignupActivity.class);
//        startActivity(i);
//        finish();
//
//    }
}


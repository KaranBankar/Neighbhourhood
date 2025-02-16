package com.example.neighbourhood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class AdminSignupActivity extends AppCompatActivity {

    MaterialCardView signupbtn;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_signup);

        signupbtn=findViewById(R.id.signupbtn);
        tvLogin=findViewById(R.id.tvLoginPage);


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminSignupActivity.this,AdminHomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminSignupActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
}
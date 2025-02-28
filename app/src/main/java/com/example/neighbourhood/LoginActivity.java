package com.example.neighbourhood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class LoginActivity extends AppCompatActivity {

    private EditText etMobile, etPassword;
    private CardView btn_login;

    TextView donthaveaccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Initialize views
        etMobile = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        btn_login=findViewById(R.id.btnLogin);
        donthaveaccount=findViewById(R.id.donthaveacc);

        donthaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,AdminUserActivity.class);
                //startActivity(i);
                //finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInputs();
                if(validateInputs()==true){

                }
                String mobile = etMobile.getText().toString();
                String password = etPassword.getText().toString();
                if(mobile.equals("9307879687") && password.equals("Pass@123")){
                    Intent i=new Intent(LoginActivity.this,AdminHomeActivity.class);
                    startActivity(i);
                    finish();
                }
                //Toast.makeText(this,"Hello",Toast.LENGTH_LONG).
            }
        });
    }

    private boolean validateInputs() {
        String mobile = etMobile.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (mobile.isEmpty() || !mobile.matches("^\\d{10}$")) {
            etMobile.setError("Enter a valid 10-digit mobile number.");
            return false;
        }

        if (password.isEmpty() || !isValidPassword(password)) {
            etPassword.setError("Password must be at least 8 characters with 1 uppercase, 1 number, and 1 special character.");
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#\\$%^&+=]).{8,}$";
        return password.matches(passwordPattern);
    }

    private void adminLogin(){

    }
}

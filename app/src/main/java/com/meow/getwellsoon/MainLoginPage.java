package com.meow.getwellsoon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainLoginPage extends AppCompatActivity {

    Button patientLogin, doctorLogin, hospitalLogin, adminLogin, register;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login_page);

        patientLogin = findViewById(R.id.patient_login);
        doctorLogin = findViewById(R.id.doctor_login);
//        hospitalLogin = findViewById(R.id.hospital_login);
//        adminLogin = findViewById(R.id.admin_login);
        register = findViewById(R.id.register);

        patientLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainLoginPage.this, PatientLogin.class));
            }
        });

        doctorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainLoginPage.this, DoctorLogin.class));
            }
        });

//        hospitalLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                startActivity(new Intent(MainLoginPage.this, PatientLogin.class));
//            }
//        });
//
//        adminLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                startActivity(new Intent(MainLoginPage.this, PatientLogin.class));
//            }
//        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainLoginPage.this, MainRegisterPage.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(MainLoginPage.this, PatientDashboard.class));
            finish();
        }
    }
}
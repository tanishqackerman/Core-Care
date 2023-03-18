package com.meow.getwellsoon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.meow.getwellsoon.databinding.ActivityMainRegisterPageBinding;

public class MainRegisterPage extends AppCompatActivity {

    private ActivityMainRegisterPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainRegisterPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.patientLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainRegisterPage.this, PatientSignUp.class));
            }
        });

        binding.doctorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainRegisterPage.this, DoctorSignUp.class));
            }
        });

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainRegisterPage.this, MainLoginPage.class));
            }
        });
    }
}
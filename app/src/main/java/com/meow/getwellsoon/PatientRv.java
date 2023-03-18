package com.meow.getwellsoon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.meow.getwellsoon.databinding.ActivityPatientRvBinding;

import java.util.ArrayList;

public class PatientRv extends AppCompatActivity {

    private ActivityPatientRvBinding binding;
    private String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace("@gmail.com", "").replaceAll("\\.", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientRvBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        if (type == 0) {
            ArrayList<Model> hehe = new ArrayList<>();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(currentUser + "/prescription");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        Model model = dataSnapshot.getValue(Model.class);
                        hehe.add(model);
                    }
                    binding.patientRv.setAdapter(new DoctorPrescriptionAdapter(PatientRv.this, hehe, new ItemClickListener() {
                        @Override
                        public void onItemClick(int position, int check) {
                            if (check == 0) {
                                startActivity(new Intent(PatientRv.this, ViewPDF.class).putExtra("pdf", hehe.get(position)));
                            }
                        }
                    }));
                    binding.patientRv.setLayoutManager(new LinearLayoutManager(PatientRv.this));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else if (type == 1) {
            ArrayList<Model> hehe = new ArrayList<>();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(currentUser + "/report");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        Model model = dataSnapshot.getValue(Model.class);
                        hehe.add(model);
                    }
                    binding.patientRv.setAdapter(new DoctorPrescriptionAdapter(PatientRv.this, hehe, new ItemClickListener() {
                        @Override
                        public void onItemClick(int position, int check) {
                            if (check == 0) {
                                startActivity(new Intent(PatientRv.this, ViewPDF.class).putExtra("pdf", hehe.get(position)));
                            }
                        }
                    }));
                    binding.patientRv.setLayoutManager(new LinearLayoutManager(PatientRv.this));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
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
import com.meow.getwellsoon.databinding.ActivityDoctorUploadBinding;

import java.util.ArrayList;

public class DoctorPatientRV extends AppCompatActivity {

    private ActivityDoctorUploadBinding binding;
    private String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace("@gmail.com", "").replaceAll("\\.", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorUploadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Model model = (Model) getIntent().getSerializableExtra("model");

        ArrayList<Model> hehe = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(currentUser + "/prescription");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Model modell = dataSnapshot.getValue(Model.class);
                    if (modell.getEmail().equals(model.getEmail())) hehe.add(modell);
                }
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference(currentUser + "/report");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            Model modell = dataSnapshot.getValue(Model.class);
                            if (modell.getEmail().equals(model.getEmail())) hehe.add(modell);
                        }
                        binding.listRv.setAdapter(new DoctorPrescriptionAdapter(DoctorPatientRV.this, hehe, new ItemClickListener() {
                            @Override
                            public void onItemClick(int position, int check) {
                                startActivity(new Intent(DoctorPatientRV.this, ViewPDF.class).putExtra("pdf", hehe.get(position)));
                            }
                        }, 1));
                        binding.listRv.setLayoutManager(new LinearLayoutManager(DoctorPatientRV.this));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
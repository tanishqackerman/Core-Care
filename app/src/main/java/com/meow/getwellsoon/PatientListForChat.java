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
import com.meow.getwellsoon.databinding.ActivityDoctorListForChatBinding;

import java.util.ArrayList;

public class PatientListForChat extends AppCompatActivity {


    private ActivityDoctorListForChatBinding binding;
    private String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace("@gmail.com", "").replaceAll("\\.", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorListForChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Model> hehe = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(currentUser + "/prescription");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Model model = dataSnapshot.getValue(Model.class);
                    hehe.add(model);
                }
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference(currentUser + "/report");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Model model = dataSnapshot.getValue(Model.class);
                            hehe.add(model);
                        }
                        binding.chatRv.setAdapter(new DoctorPrescriptionAdapter(PatientListForChat.this, hehe, new ItemClickListener() {
                            @Override
                            public void onItemClick(int position, int check) {
                                if (check == 0) {
                                    startActivity(new Intent(PatientListForChat.this, ChatPage.class).putExtra("model", hehe.get(position)).putExtra("data", new ChatList(hehe.get(position).getEmail())));
                                }
                            }
                        }));
                        binding.chatRv.setLayoutManager(new LinearLayoutManager(PatientListForChat.this));
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
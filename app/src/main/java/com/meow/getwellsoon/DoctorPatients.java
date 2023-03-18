package com.meow.getwellsoon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.meow.getwellsoon.databinding.ActivityDoctorPatientsBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class DoctorPatients extends AppCompatActivity {

    private ActivityDoctorPatientsBinding binding;
    Uri imageuri = null;
    private String username = "", patientName = "", doctorName = "", diseaseName = "";
    private String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace("@gmail.com", "").replaceAll("\\.", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorPatientsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.uploadReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DoctorPatients.this);
                LinearLayout lila1= new LinearLayout(DoctorPatients.this);
                lila1.setOrientation(LinearLayout.VERTICAL);
                builder.setTitle("Enter Details of Report");
                final EditText input = new EditText(DoctorPatients.this);
                final EditText patientInput = new EditText(DoctorPatients.this);
                final EditText doctorInput = new EditText(DoctorPatients.this);
                final EditText diseaseInput = new EditText(DoctorPatients.this);
                input.setHint("Enter Email of Patient");
                patientInput.setHint("Enter Name of Patient");
                doctorInput.setHint("Enter Name of Doctor");
                diseaseInput.setHint("Enter Name of Disease");
                input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                lila1.addView(input);
                lila1.addView(patientInput);
                lila1.addView(doctorInput);
                lila1.addView(diseaseInput);
                builder.setView(lila1);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        username = input.getText().toString().replace("@gmail.com", "").replaceAll("\\.", "");
                        patientName = patientInput.getText().toString();
                        doctorName = doctorInput.getText().toString();
                        diseaseName = diseaseInput.getText().toString();
                        Intent galleryIntent = new Intent();
                        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                        galleryIntent.setType("application/pdf");
                        startActivityForResult(galleryIntent, 1);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        binding.uploadPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DoctorPatients.this);
                LinearLayout lila1= new LinearLayout(DoctorPatients.this);
                lila1.setOrientation(LinearLayout.VERTICAL);
                builder.setTitle("Enter Details of Prescription");
                final EditText input = new EditText(DoctorPatients.this);
                final EditText patientInput = new EditText(DoctorPatients.this);
                final EditText doctorInput = new EditText(DoctorPatients.this);
                final EditText diseaseInput = new EditText(DoctorPatients.this);
                input.setHint("Enter Email of Patient");
                patientInput.setHint("Enter Name of Patient");
                doctorInput.setHint("Enter Name of Doctor");
                diseaseInput.setHint("Enter Name of Disease");
                input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                lila1.addView(input);
                lila1.addView(patientInput);
                lila1.addView(doctorInput);
                lila1.addView(diseaseInput);
                builder.setView(lila1);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        username = input.getText().toString().replace("@gmail.com", "").replaceAll("\\.", "");
                        patientName = patientInput.getText().toString();
                        doctorName = doctorInput.getText().toString();
                        diseaseName = diseaseInput.getText().toString();
                        Intent galleryIntent = new Intent();
                        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                        galleryIntent.setType("application/pdf");
                        startActivityForResult(galleryIntent, 2);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        ArrayList<Model> hehe = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(currentUser + "/prescription");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Model model = dataSnapshot.getValue(Model.class);
                    hehe.add(model);
                }
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference(currentUser + "/report");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            Model model = dataSnapshot.getValue(Model.class);
                            hehe.add(model);
                        }
                        binding.doctorPatientsRv.setAdapter(new DoctorPrescriptionAdapter(DoctorPatients.this, hehe, new ItemClickListener() {
                            @Override
                            public void onItemClick(int position, int check) {
                                if (check == 0) {
                                    startActivity(new Intent(DoctorPatients.this, DoctorPatientRV.class).putExtra("model", hehe.get(position)));
                                }
                            }
                        }));
                        binding.doctorPatientsRv.setLayoutManager(new LinearLayoutManager(DoctorPatients.this));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            imageuri = data.getData();
            String timestamp = "" + System.currentTimeMillis();
            String messagePushID = timestamp;
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("report/" + username + messagePushID);
            storageReference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uri.isComplete());
                    String myurl = uri.getResult().toString();
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference(currentUser + "/report/" + username + messagePushID);
                    Model model = new Model(username, myurl, patientName, doctorName, diseaseName);
                    databaseReference.setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                    databaseReference = FirebaseDatabase.getInstance().getReference(username + "/report/" + currentUser + messagePushID);
                    databaseReference.setValue(new Model(currentUser, myurl, patientName, doctorName, diseaseName)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } else if (resultCode == RESULT_OK && requestCode == 2) {
            imageuri = data.getData();
            String timestamp = "" + System.currentTimeMillis();
            String messagePushID = timestamp;
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("prescriptions/" + username + messagePushID);
            storageReference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uri.isComplete());
                    String myurl = uri.getResult().toString();
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference(currentUser + "/prescription/" + username + messagePushID);
                    Model model = new Model(username, myurl, patientName, doctorName, diseaseName);
                    databaseReference.setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                    databaseReference = FirebaseDatabase.getInstance().getReference(username + "/prescription/" + currentUser + messagePushID);
                    databaseReference.setValue(new Model(currentUser, myurl, patientName, doctorName, diseaseName)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }
}
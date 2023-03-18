package com.meow.getwellsoon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorPrescriptionAdapter extends RecyclerView.Adapter<DoctorPrescriptionViewHolder> {

    private Context context;
    private ArrayList<Model> patientList;
    private ItemClickListener itemClickListener;
    int hehe = -1;

    public DoctorPrescriptionAdapter(Context context, ArrayList<Model> patientList, ItemClickListener itemClickListener, int hehe) {
        this.context = context;
        this.patientList = patientList;
        this.itemClickListener = itemClickListener;
        this.hehe = hehe;
    }
    public DoctorPrescriptionAdapter(Context context, ArrayList<Model> patientList, ItemClickListener itemClickListener) {
        this.context = context;
        this.patientList = patientList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public DoctorPrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_patient_item, parent, false);
        DoctorPrescriptionViewHolder doctorPrescriptionViewHolder = new DoctorPrescriptionViewHolder(v);
        doctorPrescriptionViewHolder.patientName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(doctorPrescriptionViewHolder.getAdapterPosition(), 0);
            }
        });
        return doctorPrescriptionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorPrescriptionViewHolder holder, int position) {
        if (hehe == -1) holder.patientName.setText(patientList.get(position).getPatientName());
        else holder.patientName.setText(patientList.get(position).getDiseaseName());
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }
}

class DoctorPrescriptionViewHolder extends RecyclerView.ViewHolder {

    public TextView patientName;
    public DoctorPrescriptionViewHolder(@NonNull View itemView) {
        super(itemView);
        patientName = itemView.findViewById(R.id.patient_name);
    }
}
package com.meow.getwellsoon;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Model implements Serializable {

    String email, pdf, patientName, doctorName, diseaseName;

    public Model() {
    }

    public Model(String email, String pdf, String patientName, String doctorName, String diseaseName) {
        this.email = email;
        this.pdf = pdf;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.diseaseName = diseaseName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }
}

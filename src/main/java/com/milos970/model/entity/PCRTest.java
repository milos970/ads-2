package com.milos970.model.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PCRTest {

    private LocalDateTime dateTime;
    private String patientId;
    private int id;
    private int districtId;
    private int regionId;
    private int workplaceId;
    private boolean result;
    private double value;
    private String note;
    private Patient patient;

    // ✅ Konštruktor
    public PCRTest(LocalDateTime dateTime, String patientId, int id, int districtId,
                   int regionId, int workplaceId, boolean result, double value,
                   String note) {
        this.dateTime = dateTime;
        this.patientId = patientId;
        this.id = id;
        this.districtId = districtId;
        this.regionId = regionId;
        this.workplaceId = workplaceId;
        this.result = result;
        this.value = value;
        this.note = note;
    }

    // ✅ Gettre
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getPatientId() {
        return patientId;
    }

    public int getId() {
        return id;
    }

    public int getDistrictId() {
        return districtId;
    }

    public int getRegionId() {
        return regionId;
    }

    public int getWorkplaceId() {
        return workplaceId;
    }

    public boolean isResult() {
        return result;
    }

    public double getValue() {
        return value;
    }

    public String getNote() {
        return note;
    }

    public Patient getPatient() {
        return patient;
    }

    // ✅ Setter iba pre patient (ostatné sa nemenia)
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    // ✅ Pekný formátovaný výpis
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDate = dateTime != null ? dateTime.format(formatter) : "";
        return  "************************************\n" +
                "Test ID: " + id +
                "\nPatient ID: " + patientId +
                "\nDistrict ID: " + districtId +
                "\nRegion ID: " + regionId +
                "\nWorkplace ID: " + workplaceId +
                "\nResult: " + (result ? "Positive" : "Negative") +
                "\nValue: " + value +
                "\nDate: " + formattedDate +
                "\nNote: " + (note == null || note.isBlank() ? "—" : note) +
                "\n************************************";
    }
}


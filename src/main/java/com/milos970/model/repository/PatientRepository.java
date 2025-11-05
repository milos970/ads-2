package com.milos970.model.repository;

import com.milos970.model.entity.Patient;
import com.milos970.structure.InMemoryDatabase;

import java.util.Optional;

public final class PatientRepository {
    private final InMemoryDatabase database;


    public PatientRepository(InMemoryDatabase database) {
        this.database = database;
    }

    public Optional<Patient> findById(String pk) {
        return this.database.findPatientByPk(pk);
    }

    public Patient removeById(String id) {
        return this.database.deletePatientByPk(id);
    }


    public void save(Patient patient) {
        this.database.insertIntoTablePatients(patient);
    }


}

package com.milos970.model.repository;

import com.milos970.model.entity.Patient;
import com.milos970.structure.InMemoryDatabase;

import java.util.Optional;

public final class PatientRepository {
    private final InMemoryDatabase database;


    public PatientRepository(InMemoryDatabase database) {
        this.database = database;
    }

    public Optional<Patient> findById(String id) {
        return this.database.findPatientByPk(id);
    }


}

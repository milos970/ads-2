package com.milos970.repository;

import com.milos970.model.Patient;
import com.milos970.structure.BSTree;

import java.util.Optional;

public class PatientRepository {


    private final BSTree<String, Patient> patientsById;

    public PatientRepository() {
        this.patientsById = new BSTree<>();
    }

    public Patient deleteById(String id) {
        return this.patientsById.delete(id);
    }


    public Optional<Patient> findById(String id) {
        return this.patientsById.find(id);
    }
}

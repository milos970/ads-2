package com.milos970.model.repository;

import com.milos970.model.entity.Patient;
import com.milos970.structure.AVLTree;
import com.milos970.structure.BSTree;

import java.util.List;
import java.util.Optional;

public class InMemoryPatientRepository implements PatientRepository {

    private final BSTree<String, Patient> patientsById;


    public InMemoryPatientRepository()
    {
        this.patientsById = new AVLTree<>();
    }

    @Override
    public void save(Patient patient) {
        this.patientsById.insert(patient.id(), patient);
    }

    @Override
    public Optional<Patient> findById(String id) {
        return patientsById.find(id);
    }

    @Override
    public Optional<Patient> deleteById(String id) {
        return patientsById.delete(id);
    }

    @Override
    public List<Patient> findAll() {
        return patientsById.inOrderValues();
    }
}

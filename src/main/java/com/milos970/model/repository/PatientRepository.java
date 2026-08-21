package com.milos970.model.repository;

import com.milos970.model.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository
{
    public void save(Patient patient);
    public Optional<Patient> findById(String id);
    public Optional<Patient> deleteById(String id);
    public List<Patient> findAll();
}

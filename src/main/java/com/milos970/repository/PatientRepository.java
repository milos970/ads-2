package com.milos970.repository;

import com.milos970.model.Patient;

import java.time.LocalDate;
import java.util.List;

public class PatientRepository implements Repository<Patient> {

    @Override
    public void save(Patient entity) {
        //poukladam do jednotlivych stromov
    }

    @Override
    public void removeById(int id) {

    }

    @Override
    public Patient findById(int id) {
        return null;
    }

    @Override
    public List<Patient> findByDistrictId(int id) {
        return null;
    }

    @Override
    public List<Patient> findByRegionId(int id) {
        return null;
    }

    @Override
    public List<Patient> findByDateBetween(LocalDate from, LocalDate to) {
        return null;
    }
}

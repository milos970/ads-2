package com.milos970.model.repository;

import com.milos970.model.entity.PCRTest;
import com.milos970.model.entity.Patient;
import com.milos970.model.service.DateTestKey;
import com.milos970.structure.DistrictDateKey;
import com.milos970.structure.InMemoryDatabase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public final class PatientRepository {
    private final InMemoryDatabase database;


    public PatientRepository(InMemoryDatabase database) {
        this.database = database;
    }






}

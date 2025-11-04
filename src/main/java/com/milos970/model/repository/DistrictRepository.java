package com.milos970.model.repository;

import com.milos970.model.entity.District;
import com.milos970.structure.InMemoryDatabase;

public class DistrictRepository
{
    private final InMemoryDatabase database;


    public DistrictRepository(InMemoryDatabase database) {
        this.database = database;
    }


    public void save(District district) {
        this.database.insertIntoTableDistricts(district.id(), district);
    }
}

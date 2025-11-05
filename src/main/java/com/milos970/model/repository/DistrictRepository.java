package com.milos970.model.repository;

import com.milos970.model.entity.District;
import com.milos970.structure.InMemoryDatabase;

import java.util.List;
import java.util.Optional;

public class DistrictRepository
{
    private final InMemoryDatabase database;


    public DistrictRepository(InMemoryDatabase database) {
        this.database = database;
    }

    public Optional<District> findById(int pk) {
        return this.database.findDistrictByPk(pk);
    }

    public District removeById(int id) {
        return this.database.deleteDistrictByPkFromDistrictTable(id);
    }


    public void save(District district) {
        this.database.insertIntoTableDistricts(district.id(), district);
    }

    public List<District> findAll() {
        return this.database.findAllDistricts();
    }
}

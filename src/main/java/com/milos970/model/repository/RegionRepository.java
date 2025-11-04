package com.milos970.model.repository;

import com.milos970.model.entity.Region;
import com.milos970.structure.InMemoryDatabase;

public class RegionRepository
{
    private final InMemoryDatabase database;


    public RegionRepository(InMemoryDatabase database) {
        this.database = database;
    }


    public void save(Region region) {
        this.database.insertIntoTableRegions(region.id(), region);
    }
}

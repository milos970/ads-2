package com.milos970.model.repository;

import com.milos970.model.entity.Region;
import com.milos970.structure.InMemoryDatabase;import java.util.Optional;

public class RegionRepository
{
    private final InMemoryDatabase database;


    public RegionRepository(InMemoryDatabase database) {
        this.database = database;
    }


    public Optional<Region> findById(int pk) {
        return this.database.findRegionByPk(pk);
    }

    public Region removeById(int id) {
        return this.database.deleteRegionByPkFromRegionTable(id);
    }


    public void save(Region region) {
        this.database.insertIntoTableRegions(region.id(), region);
    }
}

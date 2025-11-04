package com.milos970.model.repository;

import com.milos970.model.entity.District;
import com.milos970.model.entity.Workplace;
import com.milos970.structure.InMemoryDatabase;

public class WorkplaceRepository
{
    private final InMemoryDatabase database;


    public WorkplaceRepository(InMemoryDatabase database) {
        this.database = database;
    }


    public void save(Workplace workplace) {
        this.database.insertIntoTableWorkplaces(workplace.id(), workplace);
    }
}

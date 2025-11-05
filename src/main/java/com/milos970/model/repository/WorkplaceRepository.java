package com.milos970.model.repository;

import com.milos970.model.entity.Workplace;
import com.milos970.structure.InMemoryDatabase;

import java.util.Optional;

public class WorkplaceRepository
{
    private final InMemoryDatabase database;


    public WorkplaceRepository(InMemoryDatabase database) {
        this.database = database;
    }

    public Optional<Workplace> findById(int pk) {
        return this.database.findWorkplaceByPk(pk);
    }

    public Workplace removeById(int id) {
        return this.database.deleteWorkplaceByPkFromWorkplaceTable(id);
    }


    public void save(Workplace workplace) {
        this.database.insertIntoTableWorkplaces(workplace.id(), workplace);
    }
}

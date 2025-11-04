package com.milos970.model.entity;

import com.milos970.structure.BSTree;

public class District
{
    private final int id;
    private final BSTree<Integer, Workplace> workplaces;

    public District(int id) {
        this.id = id;
        this.workplaces = new BSTree<>();
    }

    public int id() {
        return id;
    }

    public BSTree<Integer, Workplace> getWorkplaces() {
        return workplaces;
    }
}

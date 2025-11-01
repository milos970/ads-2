package com.milos970.repository;

import com.milos970.structure.BSTree;

public class District
{
    private final int id;
    private final BSTree<Integer, Workplace> workplaces;

    public District(int id, BSTree<Integer, Workplace> workplaces) {
        this.id = id;
        this.workplaces = workplaces;
    }

    public BSTree<Integer, Workplace> getWorkplaces() {
        return workplaces;
    }
}

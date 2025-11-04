package com.milos970.model.entity;

import com.milos970.structure.BSTree;

public class Region
{
    private final int id;
    private final BSTree<Integer, District> districts;

    public Region(int id) {
        this.id = id;
        this.districts = new BSTree<>();
    }

    public int id() {
        return this.id;
    }

    public BSTree<Integer, District> getDistricts() {
        return districts;
    }
}

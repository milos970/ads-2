package com.milos970.model.entity;

import com.milos970.structure.BSTree;

public class Region
{
    private final int id;
    private final BSTree<Integer, District> districts;

    public Region(int id, BSTree<Integer, District> districts) {
        this.id = id;
        this.districts = districts;
    }

    public BSTree<Integer, District> getDistricts() {
        return districts;
    }
}

package com.milos970.model.service;

public enum RegionSearchType
{
    BY_INFECTED_PATIENTS_AND_PERIOD("By infected patients between dates"); //16

    private final String name;

    RegionSearchType(String name) {
        this.name = name;
    }
}

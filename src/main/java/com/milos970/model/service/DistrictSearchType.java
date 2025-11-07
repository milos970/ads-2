package com.milos970.model.service;

public enum DistrictSearchType
{
    BY_INFECTED_PATIENTS_AND_PERIOD("By infected patients between dates"); //15

    private final String name;

    DistrictSearchType(String name) {
        this.name = name;
    }
}

package com.milos970.model.service;

public enum PatientSearchType
{
    BY_DISTRICT_AND_POSITIVE_TEST_AND_PERIOD("Regions sorted by infected count at a given date"), //10,11?
    BY_PERIOD("Regions sorted by infected count at a given date"), //13
    BY_PERIOD_AND_DISTRICTS("Regions sorted by infected count at a given date"); //14;

    private final String name;

    PatientSearchType(String name) {
        this.name = name;
    }
}

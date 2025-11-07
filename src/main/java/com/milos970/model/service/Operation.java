package com.milos970.model.service;

public enum Operation
{

    BY_DISTRICT("By district"),
    BY_LOCATION("By location"),
    BY_DATE("By date"),
    BY_PATIENT_AND_TEST_ID("By patient and test ID"),
    BY_PATIENT_ID("By patient ID"),
    BY_TEST_ID("By test ID"),
    BY_WORKPLACE_AND_PERIOD("By workplace ID and period"),
    BY_REGION_INFECTED_COUNT_AT_DATE("Regions sorted by infected count at a given date"),
    BY_DISTRICT_INFECTED_COUNT_AT_DATE("Districts sorted by infected count at a given date"),
    BY_DISTRICT_INFECTED_COUNT_AT_DATE("Districts sorted by infected count at a given date");
    private final String name;

    Operation(String name) {
        this.name = name;
    }
}

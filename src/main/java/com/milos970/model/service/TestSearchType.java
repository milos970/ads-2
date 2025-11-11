package com.milos970.model.service;

public enum TestSearchType {

    BY_ID("By ID",18),
    BY_TIME_PERIOD("By time period",8),
    POSITIVE_BY_TIME_PERIOD("Positive by time period",8),
    BY_REGION_ID_AND_TIME_PERIOD("By region ID and time period",6),
    POSITIVE_BY_REGION_ID_AND_TIME_PERIOD("Positive by region ID and time period",7),
    BY_DISTRICT_ID_AND_TIME_PERIOD("By district ID and time period",4),
    POSITIVE_BY_DISTRICT_ID_AND_TIME_PERIOD("Positive by district ID and time period",5),
    BY_PATIENT_ID("By patient ID",3),
    BY_PATIENT_AND_TEST_ID("By patient ID and test ID",2),
    BY_WORKPLACE_AND_TIME_PERIOD("By workplace ID and time period",17),
    DELETE_BY_ID("Delete by ID",20);

    private final String name;
    private final int index;

    TestSearchType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public static TestSearchType fromName(String name) {
        for (TestSearchType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with name " + name);
    }
}

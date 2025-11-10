package com.milos970.model.service;

public enum PatientSearchType
{
    BY_DISTRICT_ID_AND_TIME_PERIOD("By district ID and time period",10), //10,11
    BY_REGION_ID_AND_TIME_PERIOD("By region ID and time period",12), //12,13
    BY_TIME_PERIOD("By time period",14); //14

    private final String name;
    private final int index;

    PatientSearchType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public static PatientSearchType fromName(String name) {
        for (PatientSearchType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with name " + name);
    }
}

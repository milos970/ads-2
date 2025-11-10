package com.milos970.model.service;

public enum DistrictSearchType
{
    BY_TIME_PERIOD("By time period",15);


    private final String name;
    private final int index;

    DistrictSearchType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public static DistrictSearchType fromName(String name) {
        for (DistrictSearchType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with name " + name);
    }
}

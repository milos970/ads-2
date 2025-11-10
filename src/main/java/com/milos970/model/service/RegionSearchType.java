package com.milos970.model.service;

public enum RegionSearchType
{
    BY_TIME_PERIOD("By time period",16);


    private final String name;
    private final int index;

    RegionSearchType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }


    public static RegionSearchType fromName(String name) {
        for (RegionSearchType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with name " + name);
    }
}

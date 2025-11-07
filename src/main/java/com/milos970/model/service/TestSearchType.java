package com.milos970.model.service;

public enum TestSearchType {

    BY_WORKPLACE_AND_PERIOD("By infected patients between dates"), //17
    BY_POSITIVE_TEST_AND_PERIOD("By infected patients between dates"), //8
    BY_PERIOD("By infected patients between dates"), //9
    BY_REGION_AND_PERIOD("By infected patients between dates"), //7
    BY_POSITIVE_TEST_AND_REGION_AND_PERIOD("By infected patients between dates"), //6

    BY_DISTRICT_AND_PERIOD("By infected patients between dates"), //5
    BY_POSITIVE_TEST_AND_DISTRICT_AND_PERIOD("By infected patients between dates"), //4

    BY_PATIENT_AND_TEST_ID("By infected patients between dates"), //3
    BY_PATIENT("By infected patients between dates"),
    BY_ID("By ID");//1

    private final String name;

    TestSearchType(String name) {
        this.name = name;
    }


}

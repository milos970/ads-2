package com.milos970.model.repository;

import com.milos970.model.entity.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TestRepository {

    // Positive tests – DateTestKey
    void savePositiveTest(Test test);
    List<Test> findPositiveTestsBetween(LocalDateTime from, LocalDateTime to);
    Optional<Test> deletePositiveTest(Test test);

    // All tests – DateTestKey
    void saveTestByDate(Test test);
    Optional<Test> deleteTestByDate(Test test);
    List<Test> findTestsBetween(LocalDateTime from, LocalDateTime to);

    // Tests by patient + date
    void saveTestByPatientDate(Test test);
    List<Test> findTestsByPatientBetweenDates(LocalDateTime from, LocalDateTime to, String patientId);
    void deleteTestByPatientDate(Test test);

    // Tests by patient
    void saveTestByPatient(Test test);
    List<Test> findTestsByPatient(String patientId);
    Optional<Test> findTestByPatient(String patientId, int testId);
    Optional<Test> deleteTestByPatient(String patientId, int testId);

    // Tests by workplace
    void saveTestByWorkplace(Test test);
    List<Test> findTestsByWorkplaceBetween(LocalDateTime from, LocalDateTime to, int workplaceId);
    void deleteTestByWorkplace(Test test);

    // Tests by region
    void saveTestByRegion(Test test);
    List<Test> findTestsByRegionBetween(LocalDateTime from, LocalDateTime to, int regionId);
    Optional<Test> deleteTestByRegion(Test test);

    // Positive tests by region
    void savePositiveTestByRegion(Test test);
    List<Test> findPositiveTestsByRegionBetween(LocalDateTime from, LocalDateTime to, int regionId);
    Optional<Test> deletePositiveTestByRegion(Test test);

    // Tests by district
    void saveTestByDistrict(Test test);
    List<Test> findTestsByDistrictBetween(LocalDateTime from, LocalDateTime to, int districtId);
    void deleteTestByDistrict(Test test);

    // Positive tests by district
    void savePositiveTestByDistrict(Test test);
    List<Test> findPositiveTestsByDistrictBetween(LocalDateTime from, LocalDateTime to, int districtId);
    Optional<Test> deletePositiveTestByDistrict(Test test);

    // Tests by ID
    void save(Test test);
    Optional<Test> findById(int id);
    Optional<Test> deleteById(int id);


    List<Test> findAll();
}

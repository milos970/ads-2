package com.milos970.model.repository;

import com.milos970.model.entity.Test;
import com.milos970.model.entity.Patient;
import com.milos970.model.entity.Workplace;
import com.milos970.structure.AVLTree;
import com.milos970.structure.BSTree;
import com.milos970.structure.key.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class InMemoryTestRepository implements TestRepository {
    private final BSTree<DistrictDateKey, Test> testsByDistrictDateKey;
    private final BSTree<RegionDateKey, Test> testsByRegionDateKey;
    private final BSTree<DistrictDateKey, Test> positiveTestsByDistrictDateKey;
    private final BSTree<RegionDateKey, Test> positiveTestsByRegionDateKey;
    private final BSTree<WorkplaceDateKey, Test> testsByWorkplaceDateKey;
    private final BSTree<Integer, Test> testsById;
    private final BSTree<DateTestKey, Test> testsByDateTestKey;
    private final BSTree<DateTestKey, Test> positiveTestsByDateTestKey;
    private final BSTree<PatientTestKey, Test> testsByPatientTestKey;
    private final BSTree<PatientDateTestKey, Test> testsByPatientDateTestKey;

    public InMemoryTestRepository() {
        this.testsByDistrictDateKey = new AVLTree<>();
        this.testsByRegionDateKey = new AVLTree<>();
        this.positiveTestsByDistrictDateKey = new AVLTree<>();
        this.positiveTestsByRegionDateKey = new AVLTree<>();
        this.testsByWorkplaceDateKey = new AVLTree<>();

        this.testsById = new AVLTree<>();

        this.testsByDateTestKey = new AVLTree<>();
        this.positiveTestsByDateTestKey = new AVLTree<>();

        this.testsByPatientTestKey = new AVLTree<>();
        this.testsByPatientDateTestKey = new AVLTree<>();

    }


    // ------------------------------------------------------------
    // Positive tests – DateTestKey
    // ------------------------------------------------------------
    @Override
    public void savePositiveTest(Test test) {
        DateTestKey key = new DateTestKey(test.getDateTime(), test.getId());
        positiveTestsByDateTestKey.insert(key, test);
    }

    @Override
    public List<Test> findPositiveTestsBetween(LocalDateTime from, LocalDateTime to) {
        DateTestKey fromKey = new DateTestKey(from, Integer.MIN_VALUE);
        DateTestKey toKey = new DateTestKey(to, Integer.MAX_VALUE);
        return positiveTestsByDateTestKey.intervalSearch(fromKey, toKey);
    }

    @Override
    public Optional<Test> deletePositiveTest(Test test) {
        DateTestKey key = new DateTestKey(test.getDateTime(), test.getId());
        return positiveTestsByDateTestKey.delete(key);
    }


    // ------------------------------------------------------------
    // All tests – DateTestKey
    // ------------------------------------------------------------
    @Override
    public void saveTestByDate(Test test) {
        DateTestKey key = new DateTestKey(test.getDateTime(), test.getId());
        testsByDateTestKey.insert(key, test);
    }

    @Override
    public Optional<Test> deleteTestByDate(Test test) {
        DateTestKey key = new DateTestKey(test.getDateTime(), test.getId());
        return testsByDateTestKey.delete(key);
    }

    @Override
    public List<Test> findTestsBetween(LocalDateTime from, LocalDateTime to) {
        DateTestKey fromKey = new DateTestKey(from, Integer.MIN_VALUE);
        DateTestKey toKey = new DateTestKey(to, Integer.MAX_VALUE);
        return testsByDateTestKey.intervalSearch(fromKey, toKey);
    }


    // ------------------------------------------------------------
    // Tests by patient + date
    // ------------------------------------------------------------
    @Override
    public void saveTestByPatientDate(Test test) {
        PatientDateTestKey key = new PatientDateTestKey(test.getPatientId(),test.getDateTime(),test.getId());
        testsByPatientDateTestKey.insert(key, test);
    }

    @Override
    public List<Test> findTestsByPatientBetweenDates(LocalDateTime from, LocalDateTime to, String patientId) {
        PatientDateTestKey fromKey = new PatientDateTestKey(patientId,from, Integer.MIN_VALUE);
        PatientDateTestKey toKey = new PatientDateTestKey(patientId, to, Integer.MAX_VALUE);
        return testsByPatientDateTestKey.intervalSearch(fromKey, toKey);
    }

    @Override
    public void deleteTestByPatientDate(Test test) {
        PatientDateTestKey key = new PatientDateTestKey(test.getPatientId(),test.getDateTime(),test.getId());
        testsByPatientDateTestKey.delete(key);
    }


    // ------------------------------------------------------------
    // Tests by patient
    // ------------------------------------------------------------
    @Override
    public void saveTestByPatient(Test test) {
        PatientTestKey key = new PatientTestKey(test.getPatientId(), test.getId());
        testsByPatientTestKey.insert(key, test);
    }

    @Override
    public List<Test> findTestsByPatient(String patientId)
    {
        PatientTestKey fromKey = new PatientTestKey(patientId, Integer.MIN_VALUE);
        PatientTestKey toKey = new PatientTestKey(patientId, Integer.MAX_VALUE);
        return testsByPatientTestKey.intervalSearch(fromKey, toKey);
    }

    @Override
    public Optional<Test> findTestByPatient(String patientId, int testId) {
        return testsByPatientTestKey.find(new PatientTestKey(patientId, testId));
    }


    @Override
    public Optional<Test> deleteTestByPatient(String patientId, int testId) {
        return testsByPatientTestKey.delete(new PatientTestKey(patientId, testId));
    }

    // ------------------------------------------------------------
    // Tests by workplace
    // ------------------------------------------------------------
    @Override
    public void saveTestByWorkplace(Test test) {
        WorkplaceDateKey key = new WorkplaceDateKey(test.getDateTime(),test.getId(), test.getWorkplaceId());
        testsByWorkplaceDateKey.insert(key, test);
    }

    @Override
    public List<Test> findTestsByWorkplaceBetween(LocalDateTime from, LocalDateTime to, int workplaceId) {
        WorkplaceDateKey fromKey = new WorkplaceDateKey(from, Integer.MIN_VALUE, workplaceId);
        WorkplaceDateKey toKey = new WorkplaceDateKey(to, Integer.MAX_VALUE, workplaceId);
        return testsByWorkplaceDateKey.intervalSearch(fromKey, toKey);
    }

    @Override
    public void deleteTestByWorkplace(Test test) {
        WorkplaceDateKey key = new WorkplaceDateKey(test.getDateTime(),test.getId(), test.getWorkplaceId());
        testsByWorkplaceDateKey.delete(key);
    }


    // ------------------------------------------------------------
    // Tests by region
    // ------------------------------------------------------------
    @Override
    public void saveTestByRegion(Test test) {
        RegionDateKey key = new RegionDateKey(test.getRegionId(),test.getDateTime(), test.getId());
        testsByRegionDateKey.insert(key, test);
    }

    @Override
    public List<Test> findTestsByRegionBetween(LocalDateTime from, LocalDateTime to, int regionId)
    {
        RegionDateKey fromKey = new RegionDateKey(regionId, from, Integer.MIN_VALUE);
        RegionDateKey toKey = new RegionDateKey(regionId, to, Integer.MAX_VALUE);
        return testsByRegionDateKey.intervalSearch(fromKey, toKey);
    }

    @Override
    public Optional<Test> deleteTestByRegion(Test test) {
        RegionDateKey key = new RegionDateKey(test.getRegionId(),test.getDateTime(), test.getId());
        return testsByRegionDateKey.delete(key);
    }


    // ------------------------------------------------------------
    // Positive tests by region
    // ------------------------------------------------------------
    @Override
    public void savePositiveTestByRegion(Test test) {
        RegionDateKey key = new RegionDateKey(test.getRegionId(),test.getDateTime(), test.getId());
        positiveTestsByRegionDateKey.insert(key, test);
    }

    @Override
    public List<Test> findPositiveTestsByRegionBetween(LocalDateTime from, LocalDateTime to, int regionId) {
        RegionDateKey fromKey = new RegionDateKey(regionId, from, Integer.MIN_VALUE);
        RegionDateKey toKey = new RegionDateKey(regionId, to, Integer.MAX_VALUE);
        return positiveTestsByRegionDateKey.intervalSearch(fromKey, toKey);
    }

    @Override
    public Optional<Test> deletePositiveTestByRegion(Test test) {
        RegionDateKey key = new RegionDateKey(test.getRegionId(),test.getDateTime(), test.getId());
        return positiveTestsByRegionDateKey.delete(key);
    }


    // ------------------------------------------------------------
    // Tests by district
    // ------------------------------------------------------------
    @Override
    public void saveTestByDistrict(Test test) {
        DistrictDateKey key = new DistrictDateKey(test.getDistrictId(), test.getDateTime(), test.getId());
        testsByDistrictDateKey.insert(key, test);
    }

    @Override
    public List<Test> findTestsByDistrictBetween(LocalDateTime from, LocalDateTime to, int districtId) {
        DistrictDateKey fromKey = new DistrictDateKey(districtId, from, Integer.MIN_VALUE);
        DistrictDateKey toKey = new DistrictDateKey(districtId, to, Integer.MAX_VALUE);
        return testsByDistrictDateKey.intervalSearch(fromKey, toKey);
    }

    @Override
    public void deleteTestByDistrict(Test test) {
        DistrictDateKey key = new DistrictDateKey(test.getDistrictId(), test.getDateTime(), test.getId());
        testsByDistrictDateKey.delete(key);
    }

    // ------------------------------------------------------------
    // Positive tests by district
    // ------------------------------------------------------------
    @Override
    public void savePositiveTestByDistrict(Test test) {
        DistrictDateKey key = new DistrictDateKey(test.getDistrictId(), test.getDateTime(), test.getId());
        positiveTestsByDistrictDateKey.insert(key, test);
    }

    @Override
    public List<Test> findPositiveTestsByDistrictBetween(LocalDateTime from, LocalDateTime to, int districtId) {
        DistrictDateKey fromKey = new DistrictDateKey(districtId, from, Integer.MIN_VALUE);
        DistrictDateKey toKey = new DistrictDateKey(districtId, to, Integer.MAX_VALUE);
        return positiveTestsByDistrictDateKey.intervalSearch(fromKey, toKey);
    }

    @Override
    public Optional<Test> deletePositiveTestByDistrict(Test test) {
        DistrictDateKey key = new DistrictDateKey(test.getDistrictId(), test.getDateTime(), test.getId());
        return positiveTestsByDistrictDateKey.delete(key);
    }


    // ------------------------------------------------------------
    // Tests by ID
    // ------------------------------------------------------------
    @Override
    public void save(Test test) {
        testsById.insert(test.getId(), test);
    }

    @Override
    public Optional<Test> findById(int id) {
        return testsById.find(id);
    }

    @Override
    public Optional<Test> deleteById(int id) {
        return testsById.delete(id);
    }


    @Override
    public List<Test> findAll() {
        return testsById.inOrderValues();
    }


}





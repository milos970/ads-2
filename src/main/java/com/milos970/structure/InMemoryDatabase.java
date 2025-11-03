package com.milos970.structure;

import com.milos970.model.entity.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public final class InMemoryDatabase
{
    private final BSTree<Integer, Region> regions;
    private final BSTree<Integer, District> districts;
    private final BSTree<Integer, Workplace> workplaces;

    private final BSTree<Integer, PCRTest> testsById;
    private final BSTree<LocalDateTime, PCRTest> testsByDate;
    private final BSTree<LocalDateTime, PCRTest> positiveTestsByDate;

    private final BSTree<String, BSTree<Integer,PCRTest>> testsByPatientPk;
    private final BSTree<String, BSTree<LocalDateTime,PCRTest>> testsByPatientPkSortedByDate;

    private final BSTree<String, Patient> patientsById;

    public InMemoryDatabase()
    {
        this.regions = new AVLTree<>();
        this.districts = new AVLTree<>();
        this.workplaces = new AVLTree<>();

        this.testsById = new BSTree<>();
        this.testsByDate = new BSTree<>();

        this.testsByPatientPk = new AVLTree<>();
        this.positiveTestsByDate = new BSTree<>();

        this.testsByPatientPkSortedByDate = new AVLTree<>();

        this.patientsById = new AVLTree<>();
    }

    public void insertIntoTableRegions(int pk, Region region) {
        this.regions.insert(pk, region);
    }

    public void insertIntoTableDistricts(int pk, District district) {
        this.districts.insert(pk, district);
    }

    public void insertIntoTableWorkplaces(int pk, Workplace workplace) {
        this.workplaces.insert(pk, workplace);
    }

    public void insertIntoTablePCRTests(PCRTest test) {
        this.testsById.insert(test.id(), test);
    }

    public void insertIntoTablePCRTestByDate(PCRTest test) {
        this.testsById.insert(test.id(), test);
    }
    public void insertIntoTablePositivePCRTestByDate(PCRTest test) {
        this.testsById.insert(test.id(), test);
    }

    public List<District> findAllDistricts() {
        return this.districts.inOrderValues();
    }

    public List<Region> findAllRegions() {
        return this.regions.inOrderValues();
    }

    public Optional<Patient> findPatientByPk(String pk) {
        return this.patientsById.find(pk);
    }

    public Optional<Region> findRegionByPk(int pk) {
        return this.regions.find(pk);
    }

    public Optional<District> findDistrictByPk(int pk) {
        return this.districts.find(pk);
    }

    public Optional<Workplace> findWorkplaceByPk(int pk) {
        return this.workplaces.find(pk);
    }

    public Optional<PCRTest> findPCRTestByPk(int pk) {
        return this.testsById.find(pk);
    }

    public Optional<PCRTest> findTestByPatientPk(String patientPk, int testPk) {
        var tests = this.testsByPatientPk.find(patientPk).orElseThrow(NoClassDefFoundError::new);
        return tests.find(testPk);

    }

    public List<PCRTest> findAllTestsByPatientPk(String patientPk) {
        var tests = this.testsByPatientPkSortedByDate.find(patientPk).orElseThrow(NoClassDefFoundError::new);
        return tests.inOrderValues();

    }

    public List<PCRTest> findAllTestsByDate(LocalDateTime from, LocalDateTime to) {
        return this.testsByDate.intervalSearch(from, to);
    }

    public List<PCRTest> findAllPositiveTestsByDate(LocalDateTime from, LocalDateTime to) {
        return this.positiveTestsByDate.intervalSearch(from, to);
    }

    public void insertIntoTablePCRTestsByPatientId(PCRTest test) {
        var tests = this.testsByPatientPk.find(test.patientId()).orElseThrow(NoClassDefFoundError::new);
        tests.insert(test.id(), test);
    }

    public void insertIntoTablePCRTestsByPatientIdByDate(PCRTest test) {
        var tests = this.testsByPatientPkSortedByDate.find(test.patientId()).orElseThrow(NoClassDefFoundError::new);
        tests.insert(test.date(), test);
    }



}

package com.milos970.structure;

import com.milos970.model.entity.*;
import com.milos970.model.service.DateTestKey;
import com.milos970.model.service.PatientTestDateKey;
import com.milos970.model.service.PatientTestKey;

import java.util.List;
import java.util.Optional;

public final class InMemoryDatabase
{
    private final BSTree<DistrictDateKey, PCRTest> testsByDistrictAndDate;
    private final BSTree<RegionDateKey, PCRTest> testsByRegionAndDate;
    private final BSTree<DistrictDateKey, PCRTest> positiveTestsByDistrictAndDate;
    private final BSTree<RegionDateKey, PCRTest> positiveTestsByRegionAndDate;
    private final BSTree<WorkplaceDateKey, PCRTest> testsByWorkplaceAndDate;
    private final BSTree<Integer, PCRTest> testsById;
    private final BSTree<String, Patient> patientsById;
    private final BSTree<DateTestKey, PCRTest> testsByDate;
    private final BSTree<DateTestKey, PCRTest> positiveTestsByDate;


    private final BSTree<PatientTestKey, PCRTest> testsByPatientAndTest;
    private final BSTree<PatientTestDateKey, PCRTest> testsByPatientAndTestAndDate;

    public InMemoryDatabase()
    {
        this.testsByDistrictAndDate = new AVLTree<>();
        this.testsByRegionAndDate = new AVLTree<>();
        this.positiveTestsByDistrictAndDate = new AVLTree<>();
        this.positiveTestsByRegionAndDate = new AVLTree<>();

        this.testsByWorkplaceAndDate = new AVLTree<>();

        this.testsById = new AVLTree<>();
        this.patientsById = new AVLTree<>();

        this.testsByPatientAndTest = new AVLTree<>();
        this.testsByPatientAndTestAndDate = new AVLTree<>();

        this.testsByDate = new AVLTree<>();
        this.positiveTestsByDate = new AVLTree<>();
    }




        public void insertIntoPositiveTestsByDateKey(DateTestKey key, PCRTest test) {
            this.positiveTestsByDate.insert(key, test);
        }

        public void deleteFromPositiveTestsByDateKey(DateTestKey key) {
            this.positiveTestsByDate.delete(key);
        }

        public List<PCRTest> findPositiveTestsByDateAndId(DateTestKey keyA, DateTestKey keyB) {
            return this.positiveTestsByDate.intervalSearch(keyA, keyB);
        }


        public void insertIntoTestsByDateAndId(DateTestKey key, PCRTest test) {
            this.testsByDate.insert(key, test);
        }

        public void deleteFromTestsByDateAndId(DateTestKey key) {
            this.testsByDate.delete(key);
        }

        public List<PCRTest> findTestsByDateAndId(DateTestKey keyA, DateTestKey keyB) {
            return this.testsByDate.intervalSearch(keyA, keyB);
        }

        // --- Pacient + Test + Dátum ---
        public void insertIntoTestsByPatientAndTestAndDate(PatientTestDateKey key, PCRTest test) {
            this.testsByPatientAndTestAndDate.insert(key, test);
        }

        public void deleteFromTestsByPatientAndTestAndDate(PatientTestDateKey key) {
            this.testsByPatientAndTestAndDate.delete(key);
        }

        public List<PCRTest> findByPatientAndTestAndDate(PatientTestDateKey key) {
            return this.testsByPatientAndTestAndDate.find(key);
        }

        // --- Pacient + Test ---
        public void insertIntoTestsPatientAndTest(PatientTestKey key, PCRTest test) {
            this.testsByPatientAndTest.insert(key, test);
        }

        public void deleteFromTestsPatientAndTest(PatientTestKey key) {
            this.testsByPatientAndTest.delete(key);
        }

        public Optional<PCRTest> findByPatientAndTest(PatientTestKey key) {
            return this.testsByPatientAndTest.find(key);
        }

    public List<PCRTest> findTestsByPatientAndTest(PatientTestKey keyA, PatientTestKey keyB) {
        return this.testsByPatientAndTest.intervalSearch(keyA,keyB);
    }

        // --- Okres + Dátum ---
        public void insertIntoTestsByDistrictAndDate(DistrictDateKey key, PCRTest test) {
            this.testsByDistrictAndDate.insert(key, test);
        }

        public void deleteFromTestsByDistrictAndDate(DistrictDateKey key) {
            this.testsByDistrictAndDate.delete(key);
        }

        public void insertIntoPositiveTestsByDistrictAndDate(DistrictDateKey key, PCRTest test) {
            this.positiveTestsByDistrictAndDate.insert(key, test);
        }

        public void deleteFromPositiveTestsByDistrictAndDate(DistrictDateKey key) {
            this.positiveTestsByDistrictAndDate.delete(key);
        }

        // --- Región + Dátum ---
        public void insertIntoTestsByRegionAndDate(RegionDateKey key, PCRTest test) {
            this.testsByRegionAndDate.insert(key, test);
        }

        public void deleteFromTestsByRegionAndDate(RegionDateKey key) {
            this.testsByRegionAndDate.delete(key);
        }

        public void insertIntoPositiveTestsByRegionAndDate(RegionDateKey key, PCRTest test) {
            this.positiveTestsByRegionAndDate.insert(key, test);
        }

        public void deleteFromPositiveTestsByRegionAndDate(RegionDateKey key) {
            this.positiveTestsByRegionAndDate.delete(key);
        }

        // --- Workplace + Dátum ---
        public void insertIntoPTestsByWorkplaceAndDate(WorkplaceDateKey key, PCRTest test) {
            this.testsByWorkplaceAndDate.insert(key, test);
        }

        public void deleteFromTestsByWorkplaceAndDate(WorkplaceDateKey key) {
            this.testsByWorkplaceAndDate.delete(key);
        }

        // --- ID testu ---
        public void insertIntoTestsById(int id, PCRTest test) {
            this.testsById.insert(id, test);
        }

        public Optional<PCRTest> deleteFromTestsById(int id) {
            return this.testsById.delete(id);
        }

        // --- Pacient podľa ID ---
        public void insertIntoPatientsById(String id, Patient patient) {
            this.patientsById.insert(id, patient);
        }

        public Optional<Patient> deleteFromPatientsById(String id) {
            return this.patientsById.delete(id);
        }

        // --- FIND metódy ---
        public List<PCRTest> findTestsByDistrictAndDate(DistrictDateKey from, DistrictDateKey to) {
            return this.testsByDistrictAndDate.intervalSearch(from, to);
        }

        public List<PCRTest> findPositiveTestsByDistrictAndDate(DistrictDateKey from, DistrictDateKey to) {
            return this.positiveTestsByDistrictAndDate.intervalSearch(from, to);
        }

        public List<PCRTest> findTestsByRegionAndDate(RegionDateKey from, RegionDateKey to) {
            return this.testsByRegionAndDate.intervalSearch(from, to);
        }

        public List<PCRTest> findPositiveTestsByRegionAndDate(RegionDateKey from, RegionDateKey to) {
            return this.positiveTestsByRegionAndDate.intervalSearch(from, to);
        }

        public List<PCRTest> findTestsByWorkplaceAndDate(WorkplaceDateKey from, WorkplaceDateKey to) {
            return this.testsByWorkplaceAndDate.intervalSearch(from, to);
        }

        public Optional<PCRTest> findTestById(int id) {
            return this.testsById.find(id);
        }

        public Optional<Patient> findPatientById(String id) {
            return this.patientsById.find(id);
        }






}

package com.milos970.structure;

import com.milos970.model.entity.*;
import com.milos970.model.service.DateTestKey;
import com.milos970.model.service.PatientTestDateKey;
import com.milos970.model.service.PatientTestKey;

import java.util.List;
import java.util.Optional;

public final class InMemoryDatabase
{
    private final BSTree<DistrictDateKey, PCRTest> testsByDistrictDateKey;
    private final BSTree<RegionDateKey, PCRTest> testsByRegionDateKey;
    private final BSTree<DistrictDateKey, PCRTest> positiveTestsByDistrictDateKey;
    private final BSTree<RegionDateKey, PCRTest> positiveTestsByRegionDateKey;
    private final BSTree<WorkplaceDateKey, PCRTest> testsByWorkplaceDateKey;
    private final BSTree<Integer, PCRTest> testsById;
    private final BSTree<String, Patient> patientsById;
    private final BSTree<DateTestKey, PCRTest> testsByDateTestKey;
    private final BSTree<DateTestKey, PCRTest> positiveTestsByDateTestKey;
    private final BSTree<PatientTestKey, PCRTest> testsByPatientTestKey;
    private final BSTree<PatientTestDateKey, PCRTest> testsByPatientTestDateKey;

    public InMemoryDatabase()
    {
        this.testsByDistrictDateKey = new AVLTree<>();
        this.testsByRegionDateKey = new AVLTree<>();
        this.positiveTestsByDistrictDateKey = new AVLTree<>();
        this.positiveTestsByRegionDateKey = new AVLTree<>();

        this.testsByWorkplaceDateKey = new AVLTree<>();

        this.testsById = new AVLTree<>();
        this.patientsById = new AVLTree<>();

        this.testsByPatientTestKey = new AVLTree<>();
        this.testsByPatientTestDateKey = new AVLTree<>();

        this.testsByDateTestKey = new AVLTree<>();
        this.positiveTestsByDateTestKey = new AVLTree<>();
    }




        public void insertIntoPositiveTestsByDateTestKey(DateTestKey key, PCRTest test) {
            this.positiveTestsByDateTestKey.insert(key, test);
        }
        public Iterable<PCRTest> findPositiveTestsByDateTestKey(DateTestKey keyA, DateTestKey keyB) {
            return this.positiveTestsByDateTestKey.intervalSearch(keyA, keyB);
        }
        public Optional<PCRTest> deleteFromPositiveTestsByDateTestKey(DateTestKey key) {
            return this.positiveTestsByDateTestKey.delete(key);
        }




        public void insertIntoTestsByDateTestKey(DateTestKey key, PCRTest test) {
            this.testsByDateTestKey.insert(key, test);
        }
        public Optional<PCRTest> deleteFromTestsByDateTestKey(DateTestKey key) {
            return this.testsByDateTestKey.delete(key);
        }
        public List<PCRTest> findTestsByDateTestKey(DateTestKey keyA, DateTestKey keyB) {
            return this.testsByDateTestKey.intervalSearch(keyA, keyB);
        }

        public void insertIntoTestsByPatientTestDateKey(PatientTestDateKey key, PCRTest test) {
            this.testsByPatientTestDateKey.insert(key, test);
        }
        public List<PCRTest> findTestsByPatientTestDateKey(PatientTestDateKey keyA, PatientTestDateKey keyB) {
            return this.testsByPatientTestDateKey.intervalSearch(keyA, keyB);
        }
        public void deleteFromTestsByPatientTestDateKey(PatientTestDateKey key) {
            this.testsByPatientTestDateKey.delete(key);
        }


        public void insertIntoTestsByPatientTestKey(PatientTestKey key, PCRTest test) {
            this.testsByPatientTestKey.insert(key, test);
        }
        public List<PCRTest> findTestsByPatientTestKey(PatientTestKey keyA, PatientTestKey keyB) {
            return this.testsByPatientTestKey.intervalSearch(keyA,keyB);
        }
        public Optional<PCRTest> findTestByPatientTestKey(PatientTestKey key) {
            return this.testsByPatientTestKey.find(key);
        }


        public void insertIntoTestsByWorkplaceDateKey(WorkplaceDateKey key, PCRTest test) {
            this.testsByWorkplaceDateKey.insert(key, test);
        }
        public List<PCRTest> findTestsByWorkplaceDateKey(WorkplaceDateKey from, WorkplaceDateKey to) {
            return this.testsByWorkplaceDateKey.intervalSearch(from, to);
        }
        public void deleteFromTestsByWorkplaceDateKey(WorkplaceDateKey key) {
            this.testsByWorkplaceDateKey.delete(key);
        }

        public void insertIntoTestsByRegionDateKey(RegionDateKey key, PCRTest test) {
            this.testsByRegionDateKey.insert(key, test);
        }
        public List<PCRTest> findTestsByRegionDateKey(RegionDateKey from, RegionDateKey to) {
            return this.testsByRegionDateKey.intervalSearch(from, to);
        }
        public Optional<PCRTest> deleteFromTestsByRegionAndDate(RegionDateKey key) {
            return this.testsByRegionDateKey.delete(key);
        }

        public void insertIntoPositiveTestsByRegionDateKey(RegionDateKey key, PCRTest test) {
            this.positiveTestsByRegionDateKey.insert(key, test);
        }
        public List<PCRTest> findPositiveTestsByRegionDateKey(RegionDateKey from, RegionDateKey to) {
            return this.positiveTestsByRegionDateKey.intervalSearch(from, to);
        }
        public Optional<PCRTest> deleteFromPositiveTestsByRegionDateKey(RegionDateKey key) {
            return this.positiveTestsByRegionDateKey.delete(key);
        }


        public void insertIntoPositiveTestsByDistrictDateKey(DistrictDateKey key, PCRTest test) {this.positiveTestsByDistrictDateKey.insert(key, test);}
        public List<PCRTest> findPositiveTestsByDistrictDateKey(DistrictDateKey from, DistrictDateKey to) {return this.positiveTestsByDistrictDateKey.intervalSearch(from, to);}
        public Optional<PCRTest> deleteFromPositiveTestsByDistrictDateKey(DistrictDateKey key) {return this.positiveTestsByDistrictDateKey.delete(key);}

        public void insertIntoTestsByDistrictDateKey(DistrictDateKey key, PCRTest test) {this.testsByDistrictDateKey.insert(key, test);}
        public List<PCRTest> findTestsByDistrictDateKey(DistrictDateKey from, DistrictDateKey to) {return this.testsByDistrictDateKey.intervalSearch(from, to);}
        public void deleteFromTestsByDistrictDateKey(DistrictDateKey key) {
            this.testsByDistrictDateKey.delete(key);
        }



        public void insertIntoPatientsById(String id, Patient patient) {
            this.patientsById.insert(id, patient);
        }
        public Optional<Patient> findPatientById(String id) {
        return this.patientsById.find(id);
    }
        public Optional<Patient> deleteFromPatientsById(String id) {
            return this.patientsById.delete(id);
        }


        public void insertIntoTestsById(int id, PCRTest test) {
        this.testsById.insert(id, test);
    }
        public Optional<PCRTest> findTestById(int id) {
            return this.testsById.find(id);
        }
        public Optional<PCRTest> deleteFromTestsById(int id) {
        return this.testsById.delete(id);
    }

    public List<PCRTest> findAllTests() {
        return this.testsById.inOrderValues();
    }

    public List<Patient> findAllPatients() {
        return this.patientsById.inOrderValues();
    }







}

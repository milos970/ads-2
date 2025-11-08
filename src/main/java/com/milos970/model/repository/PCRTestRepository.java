package com.milos970.model.repository;

import com.milos970.model.entity.PCRTest;
import com.milos970.model.entity.Patient;
import com.milos970.model.service.DateTestKey;
import com.milos970.model.service.PatientTestDateKey;
import com.milos970.model.service.PatientTestKey;
import com.milos970.structure.DistrictDateKey;
import com.milos970.structure.InMemoryDatabase;
import com.milos970.structure.RegionDateKey;
import com.milos970.structure.WorkplaceDateKey;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public final class PCRTestRepository {
    private final InMemoryDatabase database;

    public PCRTestRepository(InMemoryDatabase database) {

        this.database = database;
    }

    //1
    public void saveTest(PCRTest test) {
        this.database.insertIntoTestsById(test.id(), test);
        this.database.insertIntoPositiveTestsByDistrictAndDate(new DistrictDateKey(test.date(),test.id()), test);
        this.database.insertIntoTestsByDistrictAndDate(new DistrictDateKey(test.date(),test.id()), test);
        this.database.insertIntoTestsByRegionAndDate(new RegionDateKey(test.date(),test.id()), test);
        this.database.insertIntoPositiveTestsByRegionAndDate(new RegionDateKey(test.date(),test.id()), test);
    }


    //2
    public Optional<PCRTest> findByIdAndPatientId(PCRTest test) {
        return this.database.findByPatientAndTest(new PatientTestKey(test.patientId(), test.id()));
    }

    //3
    public List<PCRTest> findByPatientAndDate(PCRTest test) {
        //strom v strome
    }

    //4
    public List<PCRTest> findPositiveTestsByDistrictAndPeriod(int districtId, LocalDateTime from, LocalDateTime to) {
        return this.database.findPositiveTestsByDistrictAndDate(new DistrictDateKey(from, districtId), new DistrictDateKey(to,districtId));
    }

    //5
    public List<PCRTest> findTestsByDistrictAndPeriod(int districtId, LocalDateTime from, LocalDateTime to) {
        return this.database.findTestsByDistrictAndDate(new DistrictDateKey(from, districtId), new DistrictDateKey(to,districtId));
    }

    //6
    public List<PCRTest> findPositiveTestsByRegionAndPeriod(int regionId, LocalDateTime from, LocalDateTime to) {
        return this.database.findPositiveTestsByRegionAndDate(new RegionDateKey(from, regionId), new RegionDateKey(to,regionId));
    }

    //7
    public List<PCRTest> findTestsByRegionAndPeriod(int regionId, LocalDateTime from, LocalDateTime to) {
        return this.database.findTestsByRegionAndDate(new RegionDateKey(from, regionId), new RegionDateKey(to,regionId));
    }

    //8
    public List<PCRTest> findPositiveTestsByPeriod(LocalDateTime from, LocalDateTime to) {
        return this.database.findTestsByDateAndId(new DateTestKey(from, Integer.MIN_VALUE), new DateTestKey(to,Integer.MAX_VALUE));
    }

    //9
    public List<PCRTest> findTestsByPeriod(int regionId, LocalDateTime from, LocalDateTime to) {
        return this.database.findTestsByDateAndId(new DateTestKey(from, Integer.MIN_VALUE), new DateTestKey(to,Integer.MAX_VALUE)); //positivne
    }

    //10,11
    public List<PCRTest> findPatientByDistrict(int districtId, LocalDateTime from, LocalDateTime to) {
        return this.database.findPositiveTestsByDistrictAndDate(new DistrictDateKey(from,districtId), new DistrictDateKey(to,districtId));
    }

    //12
    public List<PCRTest> findPatientByRegion(int regionId, LocalDateTime from, LocalDateTime to) {
        return this.database.findPositiveTestsByRegionAndDate(new RegionDateKey(from,regionId), new RegionDateKey(to,regionId));
    }

    //13
    public List<PCRTest> findPatientByPeriod(LocalDateTime from, LocalDateTime to) {
        return this.database.findPositiveTestsByDateAndId(new DateTestKey(from,Integer.MIN_VALUE), new DateTestKey(to,Integer.MAX_VALUE));
    }

    //14,15
    public List<PCRTest> findPatientByDistrict(LocalDateTime from, LocalDateTime to) {
        return this.database.findPositiveTestsByDistrictAndDate(new DistrictDateKey(from,Integer.MIN_VALUE), new DistrictDateKey(to,Integer.MAX_VALUE));
    }

    //16
    public List<PCRTest> findPatientByRegion(LocalDateTime from, LocalDateTime to) {
        return this.database.findPositiveTestsByRegionAndDate(new RegionDateKey(from,Integer.MIN_VALUE), new RegionDateKey(to,Integer.MAX_VALUE));
    }

    //17
    public List<PCRTest> findPatientByWorkplace(int workplace, LocalDateTime from, LocalDateTime to) {
        return this.database.findTestsByWorkplaceAndDate(new WorkplaceDateKey(from,workplace), new WorkplaceDateKey(to,workplace));
    }

    //18
    public Optional<PCRTest> findTestById(int testId) {
        return this.database.findTestById(testId);
    }

    //19
    public void savePatient(Patient patient) {
        this.database.insertIntoPatientsById(patient.id(), patient);
    }

    //20
    public void deletePCRTest(int id) {
        PCRTest test = this.database.findTestById(id).orElseThrow(NoSuchElementException::new);
        this.database.deleteFromPositiveTestsByDistrictAndDate(new DistrictDateKey(test.date(), test.districtId()));
        this.database.deleteFromPositiveTestsByRegionAndDate(new RegionDateKey(test.date(), test.regionId()));
        this.database.deleteFromTestsByDistrictAndDate(new DistrictDateKey(test.date(), test.districtId()));
        this.database.deleteFromTestsByRegionAndDate(new RegionDateKey(test.date(), test.regionId()));
        this.database.deleteFromPositiveTestsByDateAndId(new DateTestKey(test.date(), test.id()));
        this.database.deleteFromTestsByDateAndId(new DateTestKey(test.date(), test.id()));
        this.database.deleteFromTestsById(test.id());
        this.database.deleteFromTestsByWorkplaceAndDate(new WorkplaceDateKey(test.date(), test.id()));
        this.database.deleteFromTestsPatientAndTest(new PatientTestKey(test.patientId(), test.id()));
        this.database.deleteFromTestsByPatientAndTestAndDate(new PatientTestDateKey(test.patientId(), test.date(), test.id()));
    }

    //21
    public void deletePatient(String id) {
        this.database.deleteFromPatientsById(id);

        List<PCRTest> testList = this.database.findTestsByPatientAndTest(new PatientTestKey(id, Integer.MIN_VALUE), new PatientTestKey(id, Integer.MAX_VALUE));
        for (var test : testList) {
            this.deletePCRTest(test.id());
        }
    }

    public Optional<Patient> findPatientById(String id) {
        return this.database.findPatientById(id);
    }








}


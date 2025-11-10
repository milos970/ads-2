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
        this.database.insertIntoPositiveTestsByDistrictDateKey(new DistrictDateKey(test.dateTime(),test.id(), test.districtId()), test);
        this.database.insertIntoTestsByDistrictDateKey(new DistrictDateKey(test.dateTime(),test.id(), test.districtId()), test);
        this.database.insertIntoPositiveTestsByRegionDateKey(new RegionDateKey(test.dateTime(),test.id(), test.regionId()), test);
        this.database.insertIntoTestsByRegionDateKey(new RegionDateKey(test.dateTime(),test.id(), test.regionId()), test);
        this.database.insertIntoTestsByWorkplaceDateKey(new WorkplaceDateKey(test.dateTime(),test.id(), test.workplaceId()), test);

        this.database.insertIntoTestsByDateTestKey(new DateTestKey(test.dateTime(), test.id()), test);
        this.database.insertIntoPositiveTestsByDateTestKey(new DateTestKey(test.dateTime(), test.id()), test);
        this.database.insertIntoTestsByPatientTestKey(new PatientTestKey(test.patientId(), test.id()), test);
        this.database.insertIntoTestsByPatientTestDateKey(new PatientTestDateKey(test.patientId(), test.dateTime(), test.id()), test);
    }


    //2
    public Optional<PCRTest> findByTestPatientIdAndTestId(String patientId, int testId) {
        return this.database.findTestByPatientTestKey(new PatientTestKey(patientId, testId));
    }

    //3
    public List<PCRTest> findTestsByPatientId(String patientId) {
        return this.database.findTestsByPatientTestDateKey(new PatientTestDateKey(patientId,LocalDateTime.MIN, Integer.MIN_VALUE),new PatientTestDateKey(patientId,LocalDateTime.MAX, Integer.MAX_VALUE));
    }

    //4,10,11
    public List<PCRTest> findPositiveTestsByDistrictIdAndPeriod(int districtId, LocalDateTime from, LocalDateTime to) {
        return this.database.findPositiveTestsByDistrictDateKey(new DistrictDateKey(from,Integer.MIN_VALUE, Integer.MIN_VALUE), new DistrictDateKey(to,Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    //5
    public List<PCRTest> findTestsByDistrictIdAndPeriod(int districtId, LocalDateTime from, LocalDateTime to) {
        return this.database.findTestsByDistrictDateKey(new DistrictDateKey(from, Integer.MIN_VALUE, districtId), new DistrictDateKey(to, Integer.MAX_VALUE, districtId));
    }

    //6,12
    public List<PCRTest> findPositiveTestsByRegionIdAndPeriod(int regionId, LocalDateTime from, LocalDateTime to) {
        return this.database.findPositiveTestsByRegionDateKey(new RegionDateKey(from, Integer.MIN_VALUE, regionId), new RegionDateKey(to, Integer.MAX_VALUE, regionId));
    }

    //7
    public List<PCRTest> findTestsByRegionIdAndPeriod(int regionId, LocalDateTime from, LocalDateTime to) {
        return this.database.findTestsByRegionDateKey(new RegionDateKey(from, Integer.MIN_VALUE, regionId), new RegionDateKey(to, Integer.MAX_VALUE,regionId));
    }

    //8,13
    public Iterable<PCRTest> findPositiveTestsByTimePeriod(LocalDateTime from, LocalDateTime to) {
        return this.database.findPositiveTestsByDateTestKey(new DateTestKey(from, Integer.MIN_VALUE), new DateTestKey(to,Integer.MAX_VALUE));
    }

    //9
    public Iterable<PCRTest> findTestsByTimePeriod(LocalDateTime from, LocalDateTime to) {
        return this.database.findTestsByDateTestKey(new DateTestKey(from, Integer.MIN_VALUE), new DateTestKey(to,Integer.MAX_VALUE));
    }

    //12
    public List<PCRTest> findPatientByRegion(int regionId, LocalDateTime from, LocalDateTime to) {
        return this.database.findTestsByRegionDateKey(new RegionDateKey(from,Integer.MIN_VALUE,regionId), new RegionDateKey(to,Integer.MAX_VALUE,regionId));
    }

    //15
    public List<PCRTest> findPatientByDistrict(LocalDateTime from, LocalDateTime to) {
        return this.database.findPositiveTestsByDistrictDateKey(new DistrictDateKey(from,Integer.MIN_VALUE, Integer.MIN_VALUE), new DistrictDateKey(to,Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    //16
    public List<PCRTest> findPatientByRegion(LocalDateTime from, LocalDateTime to) {
        return this.database.findPositiveTestsByRegionDateKey(new RegionDateKey(from,Integer.MIN_VALUE, Integer.MIN_VALUE), new RegionDateKey(to,Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    //17)
    public List<PCRTest> findPatientByWorkplace(int workplaceId, LocalDateTime from, LocalDateTime to) {
        return this.database.findTestsByWorkplaceDateKey(new WorkplaceDateKey(from, Integer.MIN_VALUE,workplaceId), new WorkplaceDateKey(to,workplaceId,Integer.MAX_VALUE));
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
        /*this.database.deleteFromPositiveTestsByDistrictAndDate(new DistrictDateKey(test.date(), test.districtId()));
        this.database.deleteFromPositiveTestsByRegionAndDate(new RegionDateKey(test.date(), test.regionId()));
        this.database.deleteFromTestsByDistrictAndDate(new DistrictDateKey(test.date(), test.districtId()));
        this.database.deleteFromTestsByRegionAndDate(new RegionDateKey(test.date(), test.regionId()));
        this.database.deleteFromPositiveTestsByDateAndId(new DateTestKey(test.date(), test.id()));
        this.database.deleteFromTestsByDateAndId(new DateTestKey(test.date(), test.id()));
        this.database.deleteFromTestsById(test.id());
        this.database.deleteFromTestsByWorkplaceAndDate(new WorkplaceDateKey(test.date(), test.id()));
        this.database.deleteFromTestsPatientAndTest(new PatientTestKey(test.patientId(), test.id()));
        this.database.deleteFromTestsByPatientAndTestAndDate(new PatientTestDateKey(test.patientId(), test.date(), test.id()));*/
    }

    //21
    public Optional<Patient> deletePatient(String id) {
        return Optional.ofNullable(this.database.deleteFromPatientsById(id).orElseThrow(NoSuchElementException::new));
    }

    public Optional<Patient> findPatientById(String id) {
        return this.database.findPatientById(id);
    }

}


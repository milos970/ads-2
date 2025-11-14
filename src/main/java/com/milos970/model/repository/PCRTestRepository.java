package com.milos970.model.repository;

import com.milos970.model.entity.PCRTest;
import com.milos970.model.entity.Patient;
import com.milos970.structure.key.DateTestKey;
import com.milos970.structure.key.PatientTestDateKey;
import com.milos970.structure.key.PatientTestKey;
import com.milos970.structure.key.DistrictDateKey;
import com.milos970.structure.InMemoryDatabase;
import com.milos970.structure.key.RegionDateKey;
import com.milos970.structure.key.WorkplaceDateKey;

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

        this.database.insertIntoTestsById(test.getId(), test);
        this.database.insertIntoPositiveTestsByDistrictDateKey(new DistrictDateKey(test.getDateTime(),test.getId(), test.getDistrictId()), test);
        this.database.insertIntoTestsByDistrictDateKey(new DistrictDateKey(test.getDateTime(),test.getId(), test.getDistrictId()), test);
        this.database.insertIntoPositiveTestsByRegionDateKey(new RegionDateKey(test.getDateTime(),test.getId(), test.getRegionId()), test);
        this.database.insertIntoTestsByRegionDateKey(new RegionDateKey(test.getDateTime(),test.getId(), test.getRegionId()), test);
        this.database.insertIntoTestsByWorkplaceDateKey(new WorkplaceDateKey(test.getDateTime(),test.getId(), test.getWorkplaceId()), test);

        this.database.insertIntoTestsByDateTestKey(new DateTestKey(test.getDateTime(), test.getId()), test);
        this.database.insertIntoPositiveTestsByDateTestKey(new DateTestKey(test.getDateTime(), test.getId()), test);
        this.database.insertIntoTestsByPatientTestKey(new PatientTestKey(test.getPatientId(), test.getId()), test);
        this.database.insertIntoTestsByPatientTestDateKey(new PatientTestDateKey(test.getPatientId(), test.getDateTime(), test.getId()), test);
    }


    //2
    public Optional<PCRTest> findTestByPatientIdAndTestId(String patientId, int testId) {
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
    public List<PCRTest> findPositiveTestsByTimePeriod(LocalDateTime from, LocalDateTime to) {
        return this.database.findPositiveTestsByDateTestKey(new DateTestKey(from, Integer.MIN_VALUE), new DateTestKey(to,Integer.MAX_VALUE));
    }

    //9
    public List<PCRTest> findTestsByTimePeriod(LocalDateTime from, LocalDateTime to) {
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
        this.database.deleteFromTestsByDistrictDateKey(new DistrictDateKey(test.getDateTime(), id, test.getDistrictId()));
        this.database.deleteFromPositiveTestsByRegionDateKey(new RegionDateKey(test.getDateTime(), id, test.getDistrictId()));
        this.database.deleteFromPositiveTestsByDistrictDateKey(new DistrictDateKey(test.getDateTime(), id, test.getDistrictId()));
        this.database.deleteFromTestsByRegionAndDate(new RegionDateKey(test.getDateTime(), id, test.getRegionId()));
        this.database.deleteFromPositiveTestsByDateTestKey(new DateTestKey(test.getDateTime(), test.getId()));
        this.database.deleteFromTestsByDateTestKey(new DateTestKey(test.getDateTime(), test.getId()));
        this.database.deleteFromTestsById(test.getId());
        this.database.deleteFromTestsByWorkplaceDateKey(new WorkplaceDateKey(test.getDateTime(),  test.getId(), test.getWorkplaceId()));
       // this.database.deleteFromPa(new PatientTestKey(test.patientId(), test.id()));
        this.database.deleteFromTestsByPatientTestDateKey(new PatientTestDateKey(test.getPatientId(), test.getDateTime(), test.getId()));
    }

    //21
    public Optional<Patient> deletePatient(String id) {
        return Optional.ofNullable(this.database.deleteFromPatientsById(id).orElseThrow(NoSuchElementException::new));
    }

    public Optional<Patient> findPatientById(String id) {
        return this.database.findPatientById(id);
    }

    public List<Patient> findAllPatients() {
        return this.database.findAllPatients();
    }

    public List<PCRTest> findAllPCRTests() {
        return this.database.findAllTests();
    }

}


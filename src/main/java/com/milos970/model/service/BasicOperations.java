package com.milos970.model.service;

import com.milos970.model.entity.*;
import com.milos970.model.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class BasicOperations {

    private final PCRTestRepository pcrTestRepository;
    private final PatientRepository patientRepository;

    public BasicOperations(PCRTestRepository pcrTestRepository, PatientRepository patientRepository) {
        this.pcrTestRepository = pcrTestRepository;
        this.patientRepository = patientRepository;
    }

    //19
    public void createPatient(String id, String name, String surname, LocalDate birthday) {
        this.pcrTestRepository.savePatient(new Patient(id,name,surname,birthday));
    }

    public void one(LocalDateTime dateTime, String patientId,  int id, int districtId, int regionId, int workplaceId, double value, boolean result, String note) {
        Patient patient = this.pcrTestRepository.findPatientById(patientId).get();
        PCRTest test = new PCRTest(dateTime, patientId, id, districtId, regionId, workplaceId, result, value, note);
        test.setPatient(patient);
        this.pcrTestRepository.saveTest(test);
    }

    public Optional<PCRTest> two(int pcrTestId, String patientId) {
        return this.pcrTestRepository.findTestByPatientIdAndTestId(patientId, pcrTestId);
    }

    public List<PCRTest> three(String patientId) {
        return this.pcrTestRepository.findTestsByPatientId(patientId);
    }

    public List<PCRTest> four(int districtId, LocalDateTime from, LocalDateTime to) {
        return this.pcrTestRepository.findPositiveTestsByDistrictIdAndPeriod(districtId, from, to);
    }

    public List<PCRTest> five(int districtId, LocalDateTime from, LocalDateTime to) {
        return this.pcrTestRepository.findTestsByDistrictIdAndPeriod(districtId,from,to);
    }

    public List<PCRTest> six(int regionId, LocalDateTime from, LocalDateTime to) {
        return this.pcrTestRepository.findPositiveTestsByRegionIdAndPeriod(regionId, from, to);
    }

    public List<PCRTest> seven(int regionId, LocalDateTime from, LocalDateTime to) {
        return this.pcrTestRepository.findTestsByRegionIdAndPeriod(regionId, from, to);
    }

    public List<PCRTest> eight(LocalDateTime from, LocalDateTime to) {
        return this.pcrTestRepository.findPositiveTestsByTimePeriod(from,to);
    }

    public List<PCRTest> nine(LocalDateTime from, LocalDateTime to) {
        return this.pcrTestRepository.findTestsByTimePeriod(from,to);
    }

    public List<PCRTest> teen(int districtId, LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        return this.pcrTestRepository.findPositiveTestsByDistrictIdAndPeriod(districtId,from,to);
    }

    public List<PCRTest> eleven(int districtId, LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        Map<String, Integer> map = new HashMap<>();
        Iterable<PCRTest>  tests = this.pcrTestRepository.findPositiveTestsByDistrictIdAndPeriod(districtId,from, to);

        for (var test : tests) {
            String patientId = test.getPatientId();
            if (map.containsKey(patientId)) {
                map.put(patientId, map.get(patientId) + 1);
            } else {
                map.put(patientId, 1);
            }
        }

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
        return null;
    }

    public List<PCRTest> twelve(int regionId,LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        return this.pcrTestRepository.findPositiveTestsByRegionIdAndPeriod(regionId,from, to);
    }

    public List<PCRTest> thirteen(LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        return this.pcrTestRepository.findPositiveTestsByTimePeriod(from, to);
    }

    public List<Patient> fourteen(LocalDateTime from, int x) {
        return null;
    }

    public List<Map.Entry<Integer, Integer>> fifteen(LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        Map<Integer, Integer> map = new HashMap<>();
        Iterable<PCRTest>  tests = this.pcrTestRepository.findTestsByTimePeriod(from, to);

        for (var test : tests) {
            int districtId = test.getDistrictId();
            if (map.containsKey(districtId)) {
                map.put(districtId, map.get(districtId) + 1);
            } else {
                map.put(districtId, 1);
            }
        }

        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Map.Entry.<Integer, Integer>comparingByValue().reversed());

        return entries;
    }

    public List<Map.Entry<Integer, Integer>> sixteen(LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        Map<Integer, Integer> map = new HashMap<>();
        Iterable<PCRTest>  tests = this.pcrTestRepository.findTestsByTimePeriod(from, to);

        for (var test : tests) {
            int regionId = test.getRegionId();
            if (map.containsKey(regionId)) {
                map.put(regionId, map.get(regionId) + 1);
            } else {
                map.put(regionId, 1);
            }
        }

        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Map.Entry.<Integer, Integer>comparingByValue().reversed());

        return entries;
    }

    public List<PCRTest> seventeen(int workplaceId, LocalDateTime from, LocalDateTime to) {
        return this.pcrTestRepository.findPatientByWorkplace(workplaceId, from, to);
    }

    public Optional<PCRTest> eighteen(int testId) {
        return this.pcrTestRepository.findTestById(testId);
    }

    public void nineteen(String name, String surname, LocalDate birthday, String id) {
        this.pcrTestRepository.savePatient(new Patient(id, name, surname, birthday));
    }

    public void twenty(int testId) {
        this.pcrTestRepository.deletePCRTest(testId);
    }


    public void twentyOne(String patientId) {
        Patient patient = this.pcrTestRepository.deletePatient(patientId).get();

        List<PCRTest> testList = this.pcrTestRepository.findTestsByPatientId(patientId);
        for (var test : testList) {
            this.pcrTestRepository.deletePCRTest(test.getId());
        }
    }

    public void importTests(List<PCRTest> tests) {
        for (var test : tests) {
            this.one(test.getDateTime(),test.getPatientId(),test.getId(),test.getDistrictId(),test.getRegionId(),test.getWorkplaceId(),test.getValue(),test.isResult(),test.getNote());
        }
    }

    public void importPatients(List<Patient> patients) {
        for (var patient : patients) {
            this.createPatient(patient.id(),patient.name(),patient.surname(),patient.birthday());

        }
    }

    public List<PCRTest> getAllTests() {
        return this.pcrTestRepository.findAllPCRTests();
    }

    public List<Patient> getAllPatients() {
        return this.pcrTestRepository.findAllPatients();
    }









}

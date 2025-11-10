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
        Patient patient = this.pcrTestRepository.findPatientById(patientId).get(); //upravit
        this.pcrTestRepository.saveTest(new PCRTest(dateTime, patientId, id, districtId, regionId, workplaceId, result, value, note, patient ));
    }

    public Optional<PCRTest> two(int pcrTestId, String patientId) {
        return null;
    }

    public Iterable<PCRTest> three(String patientId) {
        return null;
    }

    public Iterable<PCRTest> four(int districtId, LocalDateTime from, LocalDateTime to) {
        return null;
    }

    public Iterable<PCRTest> five(int districtId, LocalDateTime from, LocalDateTime to) {
        return null;
    }

    public Iterable<PCRTest> six(int regionId, LocalDateTime from, LocalDateTime to) {
        Region region = null; //this.pcrTestRepository.findRegionById(regionId).
        return null; //this.pcrTestRepository.findAllPositiveByRegion(region, from, to);
    }

    public Iterable<PCRTest> seven(int regionId, LocalDateTime from, LocalDateTime to) {
        Region region = null; //this.regionRepository(regionId).
        return null; //this.pcrTestRepository.findAllByRegion(region, from, to);
    }


    public Iterable<PCRTest> eight(LocalDateTime from, LocalDateTime to) {
        return null;
    }

    public Iterable<PCRTest> nine(LocalDateTime from, LocalDateTime to) {
        return null;
    }

    public Iterable<Patient> teen(int districtId, LocalDateTime from, int x) {

        return null;
    }

    public Iterable<PCRTest> eleven(int districtId, LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        Map<Integer, Integer> map = new HashMap<>();
        Iterable<PCRTest>  tests = this.pcrTestRepository.findTestsByDistrictIdAndPeriod(districtId,from, to);


        return null;
    }

    public Iterable<PCRTest> twelve(LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        return this.pcrTestRepository.findPositiveTestsByTimePeriod(from, to);
    }

    public Iterable<PCRTest> thirteen(LocalDateTime from, int x) {
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
            int districtId = test.districtId();
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
            int regionId = test.regionId();
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

    public Iterable<PCRTest> seventeen(int workplaceId, LocalDateTime from, LocalDateTime to) {
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
            this.pcrTestRepository.deletePCRTest(test.id());
        }
    }









}

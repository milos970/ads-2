package com.milos970.model.service;

import com.milos970.model.CreatePatient;
import com.milos970.model.entity.*;
import com.milos970.model.repository.*;

import java.time.LocalDateTime;
import java.util.*;

public class BasicOperations {

    private final TestRepository testRepository;
    private final PatientRepository patientRepository;

    public BasicOperations(TestRepository testRepository, PatientRepository patientRepository) {
        this.testRepository = testRepository;
        this.patientRepository = patientRepository;
    }


    public void createPatient(CreatePatient createPatient) {
        Patient patient = new Patient(createPatient.id(), createPatient.name(), createPatient.surname(), createPatient.birthday());
        this.patientRepository.save(patient);
    }

    public void one(LocalDateTime dateTime, String patientId,  int id, int districtId, int regionId, int workplaceId, double value, boolean result, String note) {
        Patient patient = this.patientRepository.findById(patientId).get();
        Test test = new Test(dateTime, patientId, id, districtId, regionId, workplaceId, result, value, note);
        test.setPatient(patient);
        this.testRepository.save(test);
    }

    public Optional<Test> two(int pcrTestId, String patientId) {
        return this.testRepository.findTestByPatient(patientId, pcrTestId);
    }

    public List<Test> three(String patientId, LocalDateTime from, LocalDateTime to) {
        return this.testRepository.findTestsByPatientBetweenDates(from, to, patientId);
    }

    public List<Test> four(int districtId, LocalDateTime from, LocalDateTime to) {
        return this.testRepository.findPositiveTestsByDistrictBetween(from, to, districtId);
    }

    public List<Test> five(int districtId, LocalDateTime from, LocalDateTime to) {
        return this.testRepository.findTestsByDistrictBetween(from, to, districtId);
    }

    public List<Test> six(int regionId, LocalDateTime from, LocalDateTime to) {
        return this.testRepository.findPositiveTestsByRegionBetween(from, to, regionId);
    }

    public List<Test> seven(int regionId, LocalDateTime from, LocalDateTime to) {
        return this.testRepository.findTestsByRegionBetween(from, to, regionId);
    }

    public List<Test> eight(LocalDateTime from, LocalDateTime to) {
        return this.testRepository.findPositiveTestsBetween(from,to);
    }

    public List<Test> nine(LocalDateTime from, LocalDateTime to) {
        return this.testRepository.findTestsBetween(from,to);
    }

    public List<Patient> teen(int districtId, LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        List<Test> tests = this.testRepository.findPositiveTestsByDistrictBetween(from, to, districtId);
        return tests.stream()
                .map(Test::getPatient)
                .distinct()
                .toList();
    }

    public List<Patient> eleven(int districtId, LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);

        Map<Patient, Integer> positiveCount = new HashMap<>();
        List<Test> tests = this.testRepository.findPositiveTestsByDistrictBetween(from, to, districtId);


        for (Test test : tests) {
            Patient p = test.getPatient();
            positiveCount.merge(p, 1, Integer::sum);
        }

        List<Map.Entry<Patient, Integer>> sorted =
                new ArrayList<>(positiveCount.entrySet());
        sorted.sort(Map.Entry.<Patient, Integer>comparingByValue().reversed());

        List<Patient> result = new ArrayList<>();
        for (var entry : sorted) {
            result.add(entry.getKey());
        }

        return result;
    }

    public List<Patient> twelve(int regionId, LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        List<Test> tests = this.testRepository.findPositiveTestsByRegionBetween(from, to, regionId);
        return tests.stream()
                .map(Test::getPatient)
                .distinct()
                .toList();
    }

    public List<Patient> thirteen(LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        List<Test> tests = this.testRepository.findPositiveTestsBetween(from, to);
        return tests.stream()
                .map(Test::getPatient)
                .distinct()
                .toList();
    }

    public List<Patient> fourteen(LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        List<Test> tests = this.testRepository.findTestsBetween(from, to);

        Map<Integer, Test> map = new HashMap<>();

        for (Test test : tests) {
            int districtId = test.getDistrictId();

            if (map.containsKey(districtId)) {
                Test t = map.get(districtId);
                if (Double.compare(test.getValue(), t.getValue()) > 0) {
                    map.put(districtId, test);
                }
            } else {
                map.put(districtId, test);
            }
        }

        return map.values()
                .stream()
                .map(Test::getPatient)
                .toList();
    }


    public List<Map.Entry<Integer, Integer>> fifteen(LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        Map<Integer, Integer> map = new HashMap<>();
        Iterable<Test>  tests = this.testRepository.findTestsBetween(from, to);

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
        Iterable<Test>  tests = this.testRepository.findTestsBetween(from, to);

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

    public List<Patient> seventeen(int workplaceId, LocalDateTime from, LocalDateTime to) {
        List<Test> tests = this.testRepository.findTestsByWorkplaceBetween(from, to, workplaceId);
        return tests.stream().map(Test::getPatient).toList();
    }

    public Optional<Test> eighteen(int testId) {
        return this.testRepository.findById(testId);
    }

    public void nineteen(CreatePatient createPatient) {
        this.patientRepository.save(new Patient(createPatient.id(), createPatient.name(), createPatient.surname(), createPatient.birthday()));
    }

    public void twenty(int testId) {
        Test test = this.testRepository.deleteById(testId).get();
        this.testRepository.deletePositiveTest(test);
        this.testRepository.deleteTestByDate(test);
        this.testRepository.deleteTestByDistrict(test);
        this.testRepository.deleteTestByRegion(test);
        this.testRepository.deletePositiveTestByRegion(test);
        this.testRepository.deletePositiveTestByDistrict(test);
        this.testRepository.deleteTestByWorkplace(test);
        this.testRepository.deleteTestByPatientDate(test);
        this.testRepository.deleteTestByPatient(test.getPatientId(), test.getId());
    }

    public void twentyOne(String patientId) {
        Patient patient = this.patientRepository.deleteById(patientId).get();

        List<Test> testList = this.testRepository.findTestsByPatient(patientId);
        for (var test : testList) {
            this.twenty(test.getId());
        }
    }

    public void importTests(List<Test> tests) {
        for (var test : tests) {
            this.one(test.getDateTime(),test.getPatientId(),test.getId(),test.getDistrictId(),test.getRegionId(),test.getWorkplaceId(),test.getValue(),test.isResult(),test.getNote());
        }
    }

    public void importPatients(List<Patient> patients) {
        for (var patient : patients) {
            this.createPatient(new CreatePatient(patient.id(),patient.name(),patient.surname(),patient.birthday()));
        }
    }

    public List<Test> getAllTests() {
        return this.testRepository.findAll();
    }

    public List<Patient> getAllPatients() {
        return this.patientRepository.findAll();
    }









}

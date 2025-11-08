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

    public void one(LocalDateTime dateTime, String patientId,  int id, int districtId, int regionId, int workplaceId, double value, boolean result,
                    String note) {
        Patient patient = this.pcrTestRepository.findPatientById(patientId).get(); //upravit
        this.pcrTestRepository.saveTest(new PCRTest(dateTime, patientId, id, districtId, regionId, workplaceId, result, value, note, patient ));
    }

    public Optional<PCRTest> two(int pcrTestId, String patientId) {
        Patient patient = this.patientRepository.findById(patientId).orElseThrow(() -> new NoSuchElementException("Patient not found: " + patientId));
        return patient.getTestsById().find(pcrTestId);
    }


    public Iterable<PCRTest> three(String patientId) {
        Patient patient = this.patientRepository.findById(patientId).orElseThrow(() -> new NoSuchElementException("Patient not found: " + patientId));
        return patient.getTestsByDate().inOrderValues();
    }

    public Iterable<PCRTest> four(int districtId, LocalDateTime from, LocalDateTime to) {
        District district = this.districtRepository.findById(districtId).
                orElseThrow(() -> new NoSuchElementException("District not found: " + districtId));
        return this.pcrTestRepository.findAllPositiveByDistrictId(districtId, from, to);
    }

    public Iterable<PCRTest> five(int districtId, LocalDateTime from, LocalDateTime to) {
        District district = this.districtRepository.findById(districtId).
                orElseThrow(() -> new NoSuchElementException("District not found: " + districtId));
        return null; //this.districtRepository.findById(district, from, to);
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
        return this.pcrTestRepository.findAllPositiveBetweenDates(from,to);
    }

    public Iterable<PCRTest> nine(LocalDateTime from, LocalDateTime to) {
        return this.pcrTestRepository.findAllBetweenDates(from,to);
    }

    public Iterable<Patient> teen(int districtId, LocalDateTime from, int x) {

        District district = this.districtRepository.findById(districtId).
                orElseThrow(() -> new NoSuchElementException("District not found: " + districtId));;

        var workplaces = district.getWorkplaces().inOrderValues();


        List<Patient> sickPatients = new ArrayList<>();

        for (Workplace workplace : workplaces) {
            LocalDateTime to = from.plusDays(x);
            var recentPositives = workplace.getPositiveTests().intervalSearch(from, to);
        }

        return sickPatients;
    }

    public Iterable<PCRTest> eleven(int districtId, LocalDateTime from, int x) {

        District district = null; //this.pcrTestRepository.findDistrictById(districtId).


        var workplaces = district.getWorkplaces().inOrderValues();


        List<PCRTest> sickPatients = new ArrayList<>();

        for (Workplace workplace : workplaces) {
            LocalDateTime to = from.plusDays(x);
            sickPatients.addAll(workplace.getPositiveTests().intervalSearch(from, to));
        }


        return sickPatients.stream().sorted(Comparator.comparingDouble(PCRTest::value)).toList();
    }

    public Iterable<PCRTest> twelve(int regionId, LocalDateTime from, int x) {

        Region region = null; //this.pcrTestRepository.findRegionById(regionId).


        var districts = region.getDistricts().inOrderValues();

        List<PCRTest> sickPatients = new ArrayList<>();

        LocalDateTime to = from.plusDays(x);
        for (District district : districts) {
            var workplaces = district.getWorkplaces().inOrderValues();
            for (Workplace workplace : workplaces) {
                sickPatients.addAll(workplace.getPositiveTests().intervalSearch(from, to));
            }


        }

        return sickPatients;
    }


    public List<Patient> thirteen(LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        Iterable<PCRTest> tests = this.pcrTestRepository.findAllPositiveBetweenDates(from,to);

        List<Patient> patientList = new ArrayList<>();
        for (var test : tests) {
            Patient patient = this.patientRepository.findById(test.patientId()).orElseThrow(NoSuchElementException::new);
            patientList.add(patient);
        }
        //ulozit pacienta v teste
        return patientList;
    }

    public Iterable<Patient> fourteen(LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        Iterable<District> districts = this.districtRepository.findAll();

        List<Patient> patientList = new ArrayList<>();

        for (var district : districts) {
            var workplaces = district.getWorkplaces().inOrderValues();
            PCRTest testCandidate = null;
            for (var workplace : workplaces) {
                var testsInRange = workplace.getPositiveTests().intervalSearch(from, to);

                var bestTest = testsInRange.stream()
                        .max(Comparator.comparingDouble(PCRTest::value))
                        .orElse(null);

                if (bestTest != null && (testCandidate == null || bestTest.value() > testCandidate.value())) {
                    testCandidate = bestTest;
                }
            }
            Patient patient = this.patientRepository.findById(testCandidate.patientId()).orElseThrow(NoSuchElementException::new);
            patientList.add(patient);

        }

        return patientList;
    }

    public Iterable<District> fifteen(LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);

        Iterable<District> districts = this.districtRepository.findAll();

        List<District> patientList = new ArrayList<>();

        Map<District, Integer> map = new HashMap<>();

        for (var district : districts) {
            var workplaces = district.getWorkplaces().inOrderValues();

            int count = 0;
            for (var workplace : workplaces) {
                var testsInRange = workplace.getPositiveTests().intervalSearch(from, to);
                count += testsInRange.size();
            }
            map.put(district, count);
        }

        List<District> result = map.entrySet().stream()
                .sorted(Map.Entry.<District, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();


        return result;
    }

    public void eighteen(int testId) {

    }

    public void nineteen(int testId) {

    }

    public Iterable<PCRTest> twenty(int testId) {
        return null; //this.pcrTestRepository.removeById(testId);
    }


    public void twentyOne(String patientId) {
        Patient patient = this.patientRepository.findById(patientId).orElseThrow(NoSuchElementException::new);

        var testsByid = patient.getTestsById().inOrderValues();

        for (var test : testsByid) {
           // this.pcrTestRepository.re(test.id());
        }
        patient.getTestsById().clear();
        patient.getTestsByDate().clear();
    }









}

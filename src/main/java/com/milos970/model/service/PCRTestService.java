package com.milos970.model.service;

import com.milos970.model.dto.PCRTestRequest;
import com.milos970.model.entity.*;
import com.milos970.model.repository.*;

import java.time.LocalDateTime;
import java.util.*;

public class PCRTestService {

    private final PCRTestRepository pcrTestRepository;
    private final PatientRepository patientRepository;

    public PCRTestService(PCRTestRepository pcrTestRepository, PatientRepository patientRepository) {
        this.pcrTestRepository = pcrTestRepository;
        this.patientRepository = patientRepository;
    }

    public void one(PCRTestRequest pcrTestRequest)
    {
        this.pcrTestRepository.save(null);
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
        District district = this.pcrTestRepository.findDistrictById(districtId).
                orElseThrow(() -> new NoSuchElementException("District not found: " + districtId));
        return this.pcrTestRepository.findAllPositiveByDistrict(district, from, to);
    }

    public Iterable<PCRTest> five(int districtId, LocalDateTime from, LocalDateTime to) {
        District district = this.pcrTestRepository.findDistrictById(districtId).
                orElseThrow(() -> new NoSuchElementException("District not found: " + districtId));
        return this.pcrTestRepository.findAllByDistrict(district, from, to);
    }

    public Iterable<PCRTest> six(int regionId, LocalDateTime from, LocalDateTime to) {
        Region region = this.pcrTestRepository.findRegionById(regionId).
                orElseThrow(() -> new NoSuchElementException("Region not found: " + regionId));
        return this.pcrTestRepository.findAllPositiveByRegion(region, from, to);
    }

    public Iterable<PCRTest> seven(int regionId, LocalDateTime from, LocalDateTime to) {
        Region region = this.pcrTestRepository.findRegionById(regionId).
                orElseThrow(() -> new NoSuchElementException("Region not found: " + regionId));
        return this.pcrTestRepository.findAllByRegion(region, from, to);
    }


    public Iterable<PCRTest> eight(LocalDateTime from, LocalDateTime to) {
        return this.pcrTestRepository.findAllPositiveBetweenDates(from,to);
    }

    public Iterable<PCRTest> nine(LocalDateTime from, LocalDateTime to) {
        return this.pcrTestRepository.findAllBetweenDates(from,to);
    }

    public Iterable<Patient> teen(int districtId, LocalDateTime from, int x) {

        District district = this.pcrTestRepository.findDistrictById(districtId).
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

        District district = this.pcrTestRepository.findDistrictById(districtId).
                orElseThrow(() -> new NoSuchElementException("District not found: " + districtId));;

        var workplaces = district.getWorkplaces().inOrderValues();


        List<PCRTest> sickPatients = new ArrayList<>();

        for (Workplace workplace : workplaces) {
            LocalDateTime to = from.plusDays(x);
            sickPatients.addAll(workplace.getPositiveTests().intervalSearch(from, to));
        }


        return sickPatients.stream().sorted(Comparator.comparingDouble(PCRTest::value)).toList();
    }

    public Iterable<PCRTest> twelve(int regionId, LocalDateTime from, int x) {

        Region region = this.pcrTestRepository.findRegionById(regionId).
                orElseThrow(() -> new NoSuchElementException("Region not found: " + regionId));;

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


    public Iterable<PCRTest> thirteen(LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        return this.pcrTestRepository.findAll(from, to);
    }

    public Iterable<PCRTest> fourteen(LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        return this.pcrTestRepository.findAllByDistrict(from, to);
    }

    public Iterable<District> fifteen(LocalDateTime from, int x) {
        LocalDateTime to = from.plusDays(x);
        return this.pcrTestRepository.findDistrictsBetweenDates(from, to);
    }

    public void eighteen(int testId) {

    }

    public void nineteen(int testId) {

    }

    public Iterable<PCRTest> twenty(int testId) {
        this.pcrTestRepository.removeById(testId);

    }


    public void twentyOne(String patientId) {
        Patient patient = this.patientRepository.deleteById(patientId);

        var testsByid = patient.getTestsById().inOrderValues();

        for (var test : testsByid) {
            this.pcrTestRepository.removeById(test.id());
        }
        patient.getTestsById().clear();
        patient.getTestsByDate().clear();
    }









}

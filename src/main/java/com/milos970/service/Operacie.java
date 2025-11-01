package com.milos970.service;

import com.milos970.model.PCRTest;
import com.milos970.repository.PCRRepository;

import java.time.LocalDate;
import java.util.Comparator;

public class Operacie {

    private final PCRRepository pcrRepository = new PCRRepository();
    private final PatientRepository patientRepository = new PatientRepository();

    public void vlozPCR(LocalDate date, String personId, int id, int district, boolean result, double value, String note) {
        var pcr = new PCRTest(date, personId, id, district, result, value, note);
    }

    public void three(int id) {
        Object Pcr = null;
        this.pcrRepository.findByPatientId(id)
                .stream()
                .sorted(Comparator.comparing(Pcr::date))
                .toList();
    }

    public void four(int id) {
        this.pcrRepository.findByDistrictId(id).stream().filter(m -> m.result());
    }

    public void five(int id, LocalDate from, LocalDate to) {
        this.pcrRepository.findByDistrictId(id).stream().filter(m -> m.date().isAfter(from) && m.date().isBefore(to));
    }

    public void six(int id, LocalDate from, LocalDate to) {
        this.pcrRepository.findByRegionId(id).stream().filter(m -> m.result() && m.date().isAfter(from) && m.date().isBefore(to));
    }

    public void seven(int id, LocalDate from, LocalDate to) {
        this.pcrRepository.findByRegionId(id).stream().filter(m -> m.result() && m.date().isAfter(from) && m.date().isBefore(to));
    }

    public void eight(LocalDate from, LocalDate to) {
        this.pcrRepository.findByDateBetween(from,to).stream().filter(m -> m.result());
    }

    public void nine(LocalDate from, LocalDate to) {
        this.pcrRepository.findByDateBetween(from,to);
    }

    public void ten(int id, LocalDate from, int x) {
        this.pcrRepository.findByDistrictId(id).stream().filter(m -> LocalDate.now().minusDays(x).isBefore(m.date()));
    }









}

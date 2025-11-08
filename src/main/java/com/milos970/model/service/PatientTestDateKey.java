package com.milos970.model.service;

import java.time.LocalDateTime;

public record PatientTestDateKey(String patientId, LocalDateTime testDateTime, Integer testId) implements Comparable<PatientTestDateKey> {


    @Override
    public int compareTo(PatientTestDateKey other) {
        int cmp = this.patientId.compareTo(other.patientId);
        if (cmp == 0) cmp = this.testDateTime.compareTo(other.testDateTime);
        if (cmp == 0) cmp = Integer.compare(this.testId, other.testId);
        return cmp;
    }
}

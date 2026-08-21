package com.milos970.structure.key;

import java.time.LocalDateTime;

public record PatientDateTestKey(String patientId, LocalDateTime testDateTime, int testId) implements Comparable<PatientDateTestKey> {


    @Override
    public int compareTo(PatientDateTestKey other) {
        int cmp = this.patientId.compareTo(other.patientId);
        if (cmp == 0) {
          cmp = this.testDateTime.compareTo(other.testDateTime);
        }
        if (cmp == 0) {
          cmp = Integer.compare(this.testId, other.testId);
        }
        return cmp;
    }
}

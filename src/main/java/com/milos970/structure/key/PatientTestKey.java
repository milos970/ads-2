package com.milos970.structure.key;

public record PatientTestKey(String patientId, int keyId) implements Comparable<PatientTestKey> {



    @Override
    public int compareTo(PatientTestKey other) {
        int cmp = this.patientId.compareTo(other.patientId);
        if (cmp == 0) {
            return Integer.compare(this.keyId, other.keyId);
        }
        return cmp;
    }
}

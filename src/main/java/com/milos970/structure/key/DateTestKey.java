package com.milos970.structure.key;

import java.time.LocalDateTime;

public record DateTestKey(LocalDateTime localDateTime, int id) implements Comparable<DateTestKey> {

    @Override
    public int compareTo(DateTestKey other) {
        int cmp = this.localDateTime.compareTo(other.localDateTime);
        if (cmp == 0) {
            return Integer.compare(this.id, other.id);
        }
        return cmp;
    }
}

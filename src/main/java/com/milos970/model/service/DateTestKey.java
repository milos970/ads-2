package com.milos970.model.service;

import java.time.LocalDateTime;

public record DateTestKey(LocalDateTime localDateTime, Integer id) implements Comparable<DateTestKey> {

    @Override
    public int compareTo(DateTestKey other) {
        int cmp = this.localDateTime.compareTo(other.localDateTime);
        if (cmp == 0) {
            return this.id.compareTo(other.id);
        }
        return cmp;
    }
}

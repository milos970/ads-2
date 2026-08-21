package com.milos970.structure.key;

import java.time.LocalDateTime;

public record IdDateKey(LocalDateTime localDateTime, String id) implements Comparable<IdDateKey>{
    @Override
    public int compareTo(IdDateKey other) {
        int cmp = this.localDateTime.compareTo(other.localDateTime);
        if (cmp == 0) {
            return this.id.compareTo(other.id);
        }
        return cmp;
    }
}

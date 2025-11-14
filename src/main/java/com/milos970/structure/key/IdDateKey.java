package com.milos970.structure.key;

import java.time.LocalDateTime;

public class IdDateKey implements Comparable<IdDateKey>{
    private LocalDateTime localDateTime;
    private String id;

    public IdDateKey(LocalDateTime localDateTime, String id) {
        this.localDateTime = localDateTime;
        this.id = id;
    }

    @Override
    public int compareTo(IdDateKey other) {
        int cmp = this.localDateTime.compareTo(other.localDateTime);
        if (cmp == 0) {
            return this.id.compareTo(other.id);
        }
        return cmp;
    }
}

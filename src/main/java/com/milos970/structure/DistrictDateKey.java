package com.milos970.structure;

import java.time.LocalDateTime;

public class DistrictDateKey implements Comparable<DistrictDateKey>
{
    private LocalDateTime localDateTime;
    private Integer id;

    public DistrictDateKey(LocalDateTime localDateTime, int id) {
        this.localDateTime = localDateTime;
        this.id = id;
    }

    @Override
    public int compareTo(DistrictDateKey other) {
        int cmp = this.localDateTime.compareTo(other.localDateTime);
        if (cmp == 0) {
            return this.id.compareTo(other.id);
        }
        return cmp;
    }
}

package com.milos970.structure;

import java.time.LocalDateTime;

public class RegionDateKey implements Comparable<RegionDateKey>
{
    private LocalDateTime localDateTime;
    private Integer id;

    public RegionDateKey(LocalDateTime localDateTime, Integer id) {
        this.localDateTime = localDateTime;
        this.id = id;
    }

    @Override
    public int compareTo(RegionDateKey other) {
        int cmp = this.localDateTime.compareTo(other.localDateTime);
        if (cmp == 0) {
            return this.id.compareTo(other.id);
        }
        return cmp;
    }
}

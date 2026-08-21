package com.milos970.structure.key;

import java.time.LocalDateTime;

public record RegionDateKey(int regionId, LocalDateTime dateTime, int testId) implements Comparable<RegionDateKey>
{
    @Override
    public int compareTo(RegionDateKey o) {
        int cmp = Integer.compare(this.regionId, o.regionId);
        if (cmp == 0) cmp = this.dateTime.compareTo(o.dateTime);
        if (cmp == 0) cmp = Integer.compare(this.testId, o.testId);
        return cmp;
    }
}

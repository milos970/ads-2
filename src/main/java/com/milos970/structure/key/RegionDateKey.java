package com.milos970.structure.key;

import java.time.LocalDateTime;

public class RegionDateKey implements Comparable<RegionDateKey>
{
    private final int regionId;
    private final LocalDateTime dateTime;
    private final int testId;

    public RegionDateKey(LocalDateTime dateTime, int testId, int regionId) {
        this.dateTime = dateTime;
        this.testId = testId;
        this.regionId = regionId;
    }

    @Override
    public int compareTo(RegionDateKey o) {
        int cmp = Integer.compare(this.regionId, o.regionId);
        if (cmp == 0) cmp = this.dateTime.compareTo(o.dateTime);
        if (cmp == 0) cmp = Integer.compare(this.testId, o.testId);
        return cmp;
    }
}

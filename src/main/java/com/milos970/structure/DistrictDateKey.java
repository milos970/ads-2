package com.milos970.structure;

import java.time.LocalDateTime;

public class DistrictDateKey implements Comparable<DistrictDateKey>
{
    private final int districtId;
    private final LocalDateTime dateTime;
    private final int testId; // optional for uniqueness


    public DistrictDateKey(LocalDateTime dateTime, int testId, int districtId) {
        this.dateTime = dateTime;
        this.testId = testId;
        this.districtId = districtId;
    }

    @Override
    public int compareTo(DistrictDateKey o) {
        int cmp = Integer.compare(this.districtId, o.districtId);
        if (cmp == 0) cmp = this.dateTime.compareTo(o.dateTime);
        if (cmp == 0) cmp = Integer.compare(this.testId, o.testId);
        return cmp;
    }
}

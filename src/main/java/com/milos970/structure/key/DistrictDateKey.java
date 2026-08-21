package com.milos970.structure.key;

import java.time.LocalDateTime;

public record DistrictDateKey (int districtId, LocalDateTime dateTime, int testId) implements Comparable<DistrictDateKey>
{

    @Override
    public int compareTo(DistrictDateKey o) {
        int cmp = Integer.compare(this.districtId, o.districtId);
        if (cmp == 0) cmp = this.dateTime.compareTo(o.dateTime);
        if (cmp == 0) cmp = Integer.compare(this.testId, o.testId);
        return cmp;
    }
}

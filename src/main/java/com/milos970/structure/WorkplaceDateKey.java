package com.milos970.structure;

import java.time.LocalDateTime;

public class WorkplaceDateKey implements Comparable<WorkplaceDateKey>
{
    private LocalDateTime localDateTime;
    private int id;
    private int workplaceId;

    public WorkplaceDateKey(LocalDateTime localDateTime, int id, int workplaceId) {
        this.localDateTime = localDateTime;
        this.id = id;
        this.workplaceId = workplaceId;
    }

    @Override
    public int compareTo(WorkplaceDateKey other) {
        int cmp = Integer.compare(this.workplaceId, other.workplaceId);
        if (cmp == 0) cmp = this.localDateTime.compareTo(other.localDateTime);
        if (cmp == 0) cmp = Integer.compare(this.id, other.id);
        return cmp;
    }
}
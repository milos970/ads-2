package com.milos970.structure.key;

import java.time.LocalDateTime;

public record WorkplaceDateKey(LocalDateTime localDateTime, int id, int workplaceId) implements Comparable<WorkplaceDateKey>
{
    @Override
    public int compareTo(WorkplaceDateKey other) {
        int cmp = Integer.compare(this.workplaceId, other.workplaceId);
        if (cmp == 0) cmp = this.localDateTime.compareTo(other.localDateTime);
        if (cmp == 0) cmp = Integer.compare(this.id, other.id);
        return cmp;
    }
}
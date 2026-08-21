package com.milos970.model;

import java.time.LocalDateTime;

public record CreateTest(LocalDateTime dateTime, String patientId, int id, int districtId,
                         int regionId, int workplaceId, double value,
                         String note) {
}

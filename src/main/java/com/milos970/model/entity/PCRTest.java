package com.milos970.model.entity;

import java.time.LocalDateTime;

public record PCRTest (LocalDateTime dateTime,
                      String patientId,
                      int id, int districtId,
                      int regionId,
                      int workplaceId,
                      boolean result,
                      double value,
                      String note,Patient patient) {
}

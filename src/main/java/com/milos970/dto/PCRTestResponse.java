package com.milos970.dto;

import java.time.LocalDateTime;

public record PCRTestResponse(LocalDateTime time, PatientResponse patientResponse,
                              int id, int workplaceId, int districtId, int regionId, boolean result, double value, String note) {
}

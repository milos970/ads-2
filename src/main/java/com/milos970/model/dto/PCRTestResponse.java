package com.milos970.model.dto;

public record PCRTestResponse(String time, String patientId,
                              int id, int workplaceId, int districtId, int regionId, boolean result, double value, String note) {
}

package com.milos970.model.dto;

import java.time.LocalDate;

public record PatientRequest(String name, String username, LocalDate birthday) {
}

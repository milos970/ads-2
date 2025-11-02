package com.milos970.dto;

import java.time.LocalDate;

public record PatientRequest(String name, String username, LocalDate birthday) {
}

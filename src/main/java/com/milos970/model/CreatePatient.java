package com.milos970.model;

import java.time.LocalDate;

public record CreatePatient(String id, String name, String surname, LocalDate birthday) {
}

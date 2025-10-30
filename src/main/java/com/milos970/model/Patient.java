package com.milos970.model;

import java.time.LocalDate;

public record Patient(String name, String surname, LocalDate birthDay, String id) {
}

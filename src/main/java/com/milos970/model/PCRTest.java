package com.milos970.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PCRTest(LocalDateTime date, String personId, int id, int district, int region, int workplace, boolean result, double value, String note) {
}

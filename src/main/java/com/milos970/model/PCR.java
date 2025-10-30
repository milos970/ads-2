package com.milos970.model;

import java.time.LocalDate;

public record PCR(LocalDate date, String personId, int id, int district, boolean result, double value, String note) {
}

package com.milos970.model;

import java.time.LocalDateTime;

public record PCRTest (LocalDateTime date,
                      Patient patient,
                      int id, int district,
                      int region,
                      int workplace,
                      boolean result,
                      double value,
                      String note) {
}

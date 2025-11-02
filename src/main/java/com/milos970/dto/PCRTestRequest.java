package com.milos970.dto;

import com.milos970.model.Patient;

import java.time.LocalDateTime;

public record PCRTestRequest (LocalDateTime date,
                              Patient patient,
                              int district,
                              int region,
                              int workplace,
                              boolean result,
                              double value,
                              String note)
{};

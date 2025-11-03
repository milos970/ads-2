package com.milos970.model.dto;

import java.time.LocalDateTime;

public record PCRTestRequest (LocalDateTime date,
                              String patientId,
                              int workplace,
                              int district,
                              int region,
                              boolean result,
                              double value,
                              String note)
{};

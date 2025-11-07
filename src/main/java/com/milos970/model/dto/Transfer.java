package com.milos970.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Transfer(String idPatient, int idTest, LocalDate from, LocalDate to,
                       LocalDateTime time, int idDistrict, int idRegion, int idWorkplace, boolean positive)
{

}

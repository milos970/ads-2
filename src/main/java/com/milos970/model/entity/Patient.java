package com.milos970.model.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record Patient(String id, String name, String surname, LocalDate birthday)
{

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedBirthday = birthday != null ? birthday.format(formatter) : "";
        return "Patient ID: " + id +
                "\nName: " + name +
                "\nSurname: " + surname +
                "\nBirthday: " + formattedBirthday;
    }


}

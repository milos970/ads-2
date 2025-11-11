package com.milos970.model.service;

import com.milos970.model.entity.Patient;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientCsvTransfer {

    private static final String PATH_TO_RESOURCE = "src/main/resources/patients.csv";



    public void exportData(List<Patient> list) {
        Path path = Path.of(PATH_TO_RESOURCE);

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            for (Patient patient : list) {
                String line = String.join(",",
                        patient.id(),
                        patient.name(),
                        patient.surname(),
                        patient.birthday().toString()
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error exporting patients", e);
        }
    }



    public List<Patient> importData() {
        Path path = Path.of(PATH_TO_RESOURCE);

        List<Patient> patientList = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");
                if (words.length < 4) {
                    continue;
                }

                String id = words[0].trim();
                String name = words[1].trim();
                String surname = words[2].trim();
                LocalDate birthday = LocalDate.parse(words[3].trim());

                patientList.add(new Patient(id, name, surname, birthday));
            }
        } catch (IOException e) {
            throw new RuntimeException("Chyba pri čítaní súboru: " + path, e);
        }

        return patientList;
    }
}

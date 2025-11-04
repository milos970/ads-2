package com.milos970.model.service;

import com.milos970.model.entity.PCRTest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PCRTestCsvStrategy implements Strategy<PCRTest>  {
    private static final String PATH_TO_RESOURCE = "src/main/resources";

    @Override
    public void exportData(List<PCRTest> list) {
        Path path = Path.of(PATH_TO_RESOURCE, "exports", "tests.csv");

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            for (PCRTest test : list) {
                String line = String.join(",",
                        String.valueOf(test.id()),
                        String.valueOf(test.result()),
                        String.valueOf(test.date()),
                        String.valueOf(test.patientId()),
                        String.valueOf(test.regionId()),
                        String.valueOf(test.districtId()),
                        String.valueOf(test.workplaceId()),
                        String.valueOf(test.value()),
                        String.valueOf(test.note())
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error exporting tests", e);
        }
    }


    @Override
    public List<PCRTest> importData() {
        Path path = Path.of(PATH_TO_RESOURCE, "exports", "tests.csv");
        List<PCRTest> testList = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 9) {
                    continue;
                }

                int id = Integer.parseInt(parts[0].trim());
                boolean result = Boolean.parseBoolean(parts[1].trim());
                LocalDateTime date = LocalDateTime.parse(parts[2].trim());
                String patientId = parts[3].trim();
                int regionId = Integer.parseInt(parts[4].trim());
                int districtId = Integer.parseInt(parts[5].trim());
                int workplaceId = Integer.parseInt(parts[6].trim());
                double value = Double.parseDouble(parts[7].trim());
                String note = parts[8].trim();

                PCRTest test = new PCRTest(date, patientId, id, districtId, regionId, workplaceId,result, value,note);
                testList.add(test);
            }
        } catch (IOException e) {
            throw new RuntimeException("Chyba pri čítaní súboru: " + path, e);
        }

        return testList;
    }

}

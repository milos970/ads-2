package com.milos970.model.service;

import com.milos970.model.entity.PCRTest;
import com.milos970.model.repository.PCRTestRepository;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;

public final class Export
{
    private static final String PATH_TO_RESOURCE = "src/main/resources/";

    public static void exportToCSV(PCRTestRepository repository) throws IOException {
        Path path = Path.of(PATH_TO_RESOURCE + "exports/");
        BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()));

        var tests = repository.findALLById();
        for (var test : tests) {
            var sb = new StringBuilder();
            sb.append(test.id());
            sb.append(",");
            sb.append(test.patientId());
            sb.append(",");
            sb.append(test.regionId());
            sb.append(",");
            sb.append(test.districtId());
            sb.append(",");
            sb.append(test.workplaceId());
            sb.append(",");
            sb.append(test.date());
            sb.append(",");
            sb.append(test.result());
            sb.append(",");
            sb.append(test.value());
            sb.append(",");
            sb.append(test.note());

            writer.write(sb.toString());
        }

        writer.close();
    }

    public static void importToMemory(PCRTestRepository repository, String source) throws IOException {
        Path path = Path.of(PATH_TO_RESOURCE + "imports/");

        BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));

        String line;

        while ((line = reader.readLine()) != null) {
            String[] words = line.split(",");
            int id = Integer.valueOf(words[0]);
            String patientId = words[1];
            int regionId = Integer.valueOf(words[2]);
            int districtId = Integer.valueOf(words[3]);
            int workplaceId = Integer.valueOf(words[4]);
            LocalDateTime localDateTime = LocalDateTime.parse(words[5]);
            boolean result = Boolean.parseBoolean(words[6]);
            double value = Double.valueOf(words[7]);
            String note = words[8];
            repository.save(new PCRTest(localDateTime, patientId,id,districtId,regionId,workplaceId,result,value,note));
        }

        reader.close();
    }
}

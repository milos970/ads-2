package com.milos970.controller;

import com.milos970.model.entity.PCRTest;
import com.milos970.model.entity.Patient;
import com.milos970.model.service.BasicOperations;
import com.milos970.model.service.Generator;
import com.milos970.model.service.PCRTestCsvTransfer;
import com.milos970.model.service.PatientCsvTransfer;
import javafx.fxml.FXML;

import java.util.List;

public class ImportExportController
{
    private BasicOperations basicOperations;
    private final PCRTestCsvTransfer testCsvTransfer = new PCRTestCsvTransfer();
    private final PatientCsvTransfer patientCsvTransfer = new PatientCsvTransfer();


    public void setBasicOperations(BasicOperations basicOperations) {
        this.basicOperations = basicOperations;
    }


    @FXML
    public void importFile() {
        this.basicOperations.importPatients(this.patientCsvTransfer.importData());
        this.basicOperations.importTests(this.testCsvTransfer.importData());
    }

    @FXML
    public void exportFile() {
        this.testCsvTransfer.exportData((List<PCRTest>) this.basicOperations.getAllTests());
        this.patientCsvTransfer.exportData((List<Patient>) this.basicOperations.getAllPatients());
    }


    @FXML
    public void generate() {
        Generator generator = new Generator(74,128,256,120000,254000,this.basicOperations);
        generator.generateAll();
    }
}

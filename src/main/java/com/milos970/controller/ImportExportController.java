package com.milos970.controller;

import com.milos970.model.service.BasicOperations;
import com.milos970.model.service.Generator;
import com.milos970.model.service.TestCsvTransfer;
import com.milos970.model.service.PatientCsvTransfer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class ImportExportController
{
    private BasicOperations basicOperations;
    private final TestCsvTransfer testCsvTransfer = new TestCsvTransfer();
    private final PatientCsvTransfer patientCsvTransfer = new PatientCsvTransfer();

    @FXML
    private Label importErrorLabel;

    @FXML
    private Label exportErrorLabel;

    @FXML
    private Label generateErrorLabel;


    public void setBasicOperations(BasicOperations basicOperations) {
        this.basicOperations = basicOperations;
    }


    @FXML
    public void importFile() {
        try {
            this.basicOperations.importPatients(this.patientCsvTransfer.importData());
            this.basicOperations.importTests(this.testCsvTransfer.importData());
            this.setLabel(this.importErrorLabel, "Data has been imported.", "green");

        } catch (Exception e) {
            this.setLabel(this.importErrorLabel, "Error importing data!", "red");
            e.printStackTrace();
        }
    }

    @FXML
    public void exportFile() {
        try {
            this.testCsvTransfer.exportData(this.basicOperations.getAllTests());
            this.patientCsvTransfer.exportData(this.basicOperations.getAllPatients());
            this.setLabel(this.exportErrorLabel, "Data has been exported.", "green");

        } catch (Exception e) {
            this.setLabel(this.exportErrorLabel, "Error exporting data!", "red");
            e.printStackTrace();
        }
    }


    private void setLabel(Label label, String message, String color) {
        label.setText(message);
        label.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 18px;");
    }


    @FXML
    public void generate() {
        Generator generator = new Generator(74,128,256,120000,254000,this.basicOperations);
        generator.generateAll();
        this.setLabel(this.generateErrorLabel, "Data has been generated.", "white");
    }
}

package com.milos970.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import java.io.IOException;

public class MenuController {

    @FXML private Tab patientTab;
    @FXML private Tab testTab;
    @FXML private Tab filteringTab;
    @FXML private Tab importExportTab;

    private PatientController patientController;
    private PCRTestController testController;
    private FilteringController filteringController;
    private ImportExportController importExportController;



    @FXML
    public void initialize() {
        patientController = loadTab(patientTab, "/presenter/Patient.fxml");
        testController = loadTab(testTab, "/presenter/Test.fxml");
        filteringController = loadTab(filteringTab, "/presenter/Filtering.fxml");
        importExportController = loadTab(importExportTab, "/presenter/ImportExport.fxml");
    }
    private <T> T loadTab(Tab tab, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            tab.setContent(loader.load());
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public ImportExportController getImportExportController() {
        return importExportController;
    }

    public FilteringController getFilteringController() {
        return filteringController;
    }

    public PCRTestController getTestController() {
        return testController;
    }

    public PatientController getPatientController() {
        return patientController;
    }
}

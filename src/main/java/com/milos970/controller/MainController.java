package com.milos970.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import java.io.IOException;

public class MainController {

    @FXML private Tab patientTab;
    @FXML private Tab testTab;
    @FXML private Tab filteringTab;

    @FXML
    public void initialize() {
        loadTab(patientTab, "/presenter/Patient.fxml");
        loadTab(testTab, "/presenter/Filtering.fxml");
        loadTab(filteringTab, "/presenter/Test.fxml");
    }

    private void loadTab(Tab tab, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            tab.setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}

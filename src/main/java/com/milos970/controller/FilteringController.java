package com.milos970.controller;

import com.milos970.model.service.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FilteringController {

    @FXML
    private RadioButton test;
    @FXML
    private RadioButton  district;
    @FXML
    private RadioButton  region;
    @FXML
    private RadioButton  patient;
    @FXML
    private ComboBox combobox;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private ListView listView;
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private TextField xTextField;
    @FXML
    private ComboBox tuComboBox;
    @FXML
    private CheckBox positiveCheckBox;

    private final HashMap<String, List<String>> map = new HashMap<>();

    private BasicOperations basicOperations;


    public void setBasicOperations(BasicOperations basicOperations) {
        this.basicOperations = basicOperations;
    }

    @FXML
    public void initialize() {

        this.map.put("PCRTest",  Arrays.stream(TestSearchType.values())
                .map(Enum::name)
                .toList());

        this.map.put("Patient",  Arrays.stream(PatientSearchType.values())
                .map(Enum::name)
                .toList());

        this.map.put("District",  Arrays.stream(DistrictSearchType.values())
                .map(Enum::name)
                .toList());

        this.map.put("Region",  Arrays.stream(RegionSearchType.values())
                .map(Enum::name)
                .toList());


    }

    @FXML
    private void checkCategory() {
        if (toggleGroup.getSelectedToggle() != null) {
            RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle();
            this.combobox.getItems().clear();
            this.combobox.getItems().addAll(this.map.get(selected.getText()));
        }
    }

    @FXML
    private void checkOperation() {
        String selected = combobox.getValue().toString();
    }


    private void setCategoryDistrict() {
        this.positiveCheckBox.setVisible(false);
        this.toDatePicker.setVisible(false);
    }

    private void setCategoryRegion() {
        this.positiveCheckBox.setVisible(false);
        this.toDatePicker.setVisible(false);
    }

    private void setCategoryWorkplace() {
        this.positiveCheckBox.setVisible(false);
        this.xTextField.setVisible(false);
    }

    private void setCategoryTest() {

    }

    private void setCategoryPatient() {

    }





}

package com.milos970.controller;

import com.milos970.model.service.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class PatientController {
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private TextField uniqueNumberTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private RadioButton addPatientRadioButton;

    @FXML
    private RadioButton removePatientRadioButton;


    private ToggleGroup toggleGroup;

    @FXML
    public void initialize() {

        this.errorLabel.setStyle("-fx-text-fill: red;");

        toggleGroup = new ToggleGroup();

        addPatientRadioButton.setToggleGroup(toggleGroup);
        removePatientRadioButton.setToggleGroup(toggleGroup);
    }

    private BasicOperations basicOperations;


    public void setBasicOperations(BasicOperations basicOperations) {
        this.basicOperations = basicOperations;
    }


    @FXML
    private void save() {

        if (!this.validate()) {
            return;
        }
        this.basicOperations.createPatient(this.uniqueNumberTextField.getText(), this.nameTextField.getText(), this.surnameTextField.getText(), this.birthDatePicker.getValue());
        this.cleanForm();
    }

    private void cleanForm() {
        this.nameTextField.setText("");
        this.surnameTextField.setText("");
        this.uniqueNumberTextField.setText("");
        this.birthDatePicker.setValue(null);
    }

    private boolean validate() {
        String name = this.nameTextField.getText();
        String surname = this.surnameTextField.getText();
        String unique = this.uniqueNumberTextField.getText();
        LocalDate birthday = this.birthDatePicker.getValue();


        if (name.equals("")) {
            this.errorLabel.setText("Neplatne meno");
            this.nameTextField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            return false;
        } else {
            this.nameTextField.setStyle("");
        }

        if (surname.equals("")) {
            this.errorLabel.setText("Neplatne priezvisko");
            this.surnameTextField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            return false;
        }else {
            this.surnameTextField.setStyle("");
        }

        if (birthday == null) {
            this.errorLabel.setText("Nezvolený dátum");
            this.birthDatePicker.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            return false;
        }else {
            this.birthDatePicker.setStyle("");
        }

        if (unique.equals("")) {
            this.errorLabel.setText("Nezvolená unikátna hodnota");
            this.uniqueNumberTextField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            return false;
        }else {
            this.uniqueNumberTextField.setStyle("");
        }

        this.errorLabel.setText("");
        this.errorLabel.setVisible(false);




        return true;
    }


}

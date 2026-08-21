package com.milos970.controller;

import com.milos970.model.CreatePatient;
import com.milos970.model.service.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

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

    @FXML
    private VBox patientVBox;

    @FXML
    private Button removeButton;

    @FXML
    private Button saveButton;



    private ToggleGroup toggleGroup;

    @FXML
    public void initialize() {
        toggleGroup = new ToggleGroup();

        addPatientRadioButton.setToggleGroup(toggleGroup);
        removePatientRadioButton.setToggleGroup(toggleGroup);
        this.addPatientRadioButton.setSelected(true);
        this.addOption();
    }

    private BasicOperations basicOperations;


    public void setBasicOperations(BasicOperations basicOperations) {
        this.basicOperations = basicOperations;
    }

    private void hideAllInputs() {
        patientVBox.getChildren().clear();
    }

    private void showNode(Node node) {
        if (!patientVBox.getChildren().contains(node)) {
            patientVBox.getChildren().add(node);
        }
    }

    @FXML
    private void removeOption() {
        this.hideAllInputs();
        this.showNode(this.addPatientRadioButton);
        this.showNode(this.removePatientRadioButton);
        this.showNode(this.uniqueNumberTextField);
        this.showNode(this.errorLabel);
        this.showNode(this.removeButton);

    }

    @FXML
    private void addOption() {
        this.hideAllInputs();
        this.showNode(this.addPatientRadioButton);
        this.showNode(this.removePatientRadioButton);
        this.showNode(this.nameTextField);
        this.showNode(this.surnameTextField);
        this.showNode(this.birthDatePicker);
        this.showNode(this.uniqueNumberTextField);
        this.showNode(this.errorLabel);
        this.showNode(this.saveButton);
    }




    @FXML
    private void remove() {

        if (!this.validateTextField(this.uniqueNumberTextField, "Unique number is required.")) {
            return;
        }
        String personID = this.uniqueNumberTextField.getText();
        this.basicOperations.twentyOne(personID);
        this.cleanForm();
    }




    @FXML
    private void save() {

        if (!this.validate()) {
            return;
        }
        String name = this.nameTextField.getText();
        String surname = this.surnameTextField.getText();
        String unique = this.uniqueNumberTextField.getText();
        LocalDate birthday = this.birthDatePicker.getValue();

        this.basicOperations.createPatient(new CreatePatient(unique, name, surname, birthday));
        this.cleanForm();
    }

    private void cleanForm() {
        this.nameTextField.setText("");
        this.surnameTextField.setText("");
        this.uniqueNumberTextField.setText("");
        this.birthDatePicker.setValue(null);
    }

    private boolean validate() {

        if (!this.validateTextField(this.nameTextField, "Name is required.")) {
            return false;
        }

        if (!this.validateTextField(this.surnameTextField, "Surname is required.")) {
            return false;
        }

        LocalDate birthday = this.birthDatePicker.getValue();

        if (birthday == null) {
            this.errorLabel.setText("Date is required.");
            this.birthDatePicker.setStyle("-fx-border-color: red; -fx-border-width: 2px;"); return false;
        }else
        {
            this.birthDatePicker.setStyle("");
        }


        if (!this.validateTextField(this.uniqueNumberTextField, "Unique number is required.")) {
            return false;
        }

        this.errorLabel.setText("");


        return true;
    }


    private boolean validateTextField(TextField textField, String message) {
        if (textField.getText() == null || textField.getText().isBlank()) {
            this.errorLabel.setText(message);
            this.errorLabel.setVisible(true);
            textField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            return false;
        }
        textField.setStyle("");
        return true;
    }




}

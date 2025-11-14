package com.milos970.controller;

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
        this.errorLabel.setStyle("-fx-text-fill: red;");
        toggleGroup = new ToggleGroup();

        addPatientRadioButton.setToggleGroup(toggleGroup);
        removePatientRadioButton.setToggleGroup(toggleGroup);
        this.addPatientRadioButton.setSelected(true);
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
        this.showNode(this.saveButton);
    }




    @FXML
    private void remove() {
        String ID = this.uniqueNumberTextField.getText();
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

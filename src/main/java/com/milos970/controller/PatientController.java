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
    private DatePicker birthdayDatePicker;

    @FXML
    private TextField uniqueNumberTextField;

    @FXML
    private Label errorLabel;

    @FXML
    public void initialize() {
        this.errorLabel.setStyle("-fx-text-fill: red;");
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
        this.cleanForm();
    }

    private void cleanForm() {
        this.nameTextField.setText("");
        this.surnameTextField.setText("");
        this.uniqueNumberTextField.setText("");
        this.birthdayDatePicker.setValue(null);
    }

    private boolean validate() {
        String name = this.nameTextField.getText();
        String surname = this.surnameTextField.getText();
        String unique = this.uniqueNumberTextField.getText();
        LocalDate birthday = this.birthdayDatePicker.getValue();


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
            this.birthdayDatePicker.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            return false;
        }else {
            this.birthdayDatePicker.setStyle("");
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

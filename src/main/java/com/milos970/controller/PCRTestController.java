package com.milos970.controller;

import com.milos970.model.service.BasicOperations;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PCRTestController {

        @FXML
        private DatePicker dateDatePicker;
        @FXML
        private TextField patientTextField;
        @FXML
        private TextField workplaceTextField;
        @FXML
        private TextField districtTextField;
        @FXML
        private TextField regionTextField;
        @FXML
        private TextField valueTextField;
        @FXML
        private TextArea notesTextArea;
        @FXML
        private Label errorLabel;
        @FXML
        private TextField timeTextField;

        @FXML
        public void initialize() {
            this.errorLabel.setStyle("-fx-text-fill: red;");

            TextFormatter<Integer> formatter1 = new TextFormatter<>(change -> {
                String newText = change.getControlNewText();
                if (newText.matches("\\d*")) { // iba čísla
                    return change;
                }
                return null;
            });
            TextFormatter<Integer> formatter2 = new TextFormatter<>(change -> {
                String newText = change.getControlNewText();
                if (newText.matches("\\d*")) { // iba čísla
                    return change;
                }
                return null;
            });
            TextFormatter<Integer> formatter3 = new TextFormatter<>(change -> {
                String newText = change.getControlNewText();
                if (newText.matches("\\d*")) { // iba čísla
                    return change;
                }
                return null;
            });
            TextFormatter<Double> formatter4 = new TextFormatter<>(change -> {
                String newText = change.getControlNewText();

                // povolí: prázdny text, celé číslo, desatinné číslo s bodkou
                if (newText.matches("\\d*(\\.\\d*)?")) {
                    return change;
                }
                return null;
            });


            TextFormatter<Integer> formatter5 = new TextFormatter<>(change -> {
                String newText = change.getControlNewText();
                if (newText.matches("\\d*")) { // iba čísla
                    return change;
                }
                return null;
            });
            this.regionTextField.setTextFormatter(formatter1);
            this.districtTextField.setTextFormatter(formatter2);
            this.workplaceTextField.setTextFormatter(formatter3);
            this.valueTextField.setTextFormatter(formatter4);
            this.patientTextField.setTextFormatter(formatter5);


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
            this.dateDatePicker.setValue(null);
            this.patientTextField.setText("");
            this.districtTextField.setText("");
            this.regionTextField.setText("");
            this.workplaceTextField.setText("");
            this.valueTextField.setText("");
            this.notesTextArea.setText("");
            this.errorLabel.setText("");
        }

        private boolean validate() {
            String region = this.regionTextField.getText();
            String district = this.districtTextField.getText();
            String workplace = this.workplaceTextField.getText();
            String value = this.valueTextField.getText();
            String notes = this.notesTextArea.getText();
            String time = this.timeTextField.getText();




            if (region.equals("")) {
                this.errorLabel.setText("No regiond ID specified");
                this.regionTextField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                return false;
            } else {
                this.regionTextField.setStyle("");
            }

            if (district.equals("")) {
                this.errorLabel.setText("No district ID specified");
                this.districtTextField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                return false;
            }else {
                this.districtTextField.setStyle("");
            }

            if (workplace.equals("")) {
                this.errorLabel.setText("No workplace ID specified");
                this.workplaceTextField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                return false;
            }else {
                this.workplaceTextField.setStyle("");
            }

            if (value.equals("")) {
                this.errorLabel.setText("No value specified");
                this.valueTextField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                return false;
            }else {
                this.valueTextField.setStyle("");
            }

            if (notes.equals("")) {
                this.errorLabel.setText("No notes specified");
                this.notesTextArea.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                return false;
            }else {
                this.notesTextArea.setStyle("");
            }

            if (time.equals("")) {
                this.errorLabel.setText("No time specified");
                this.timeTextField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                return false;
            }else {
                this.timeTextField.setStyle("");
            }

            try {
                LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));
            } catch (DateTimeParseException e) {
                this.errorLabel.setText("Invalid time");
                return false;
            }


            this.errorLabel.setText("");
            this.errorLabel.setVisible(false);


            return true;
        }


    }



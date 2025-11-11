package com.milos970.controller;

import com.milos970.model.service.BasicOperations;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;

public class PCRTestController {

        @FXML
        private DatePicker dateDatePicker;
        @FXML
        private TextField patientIdTextField;
        @FXML
        private TextField workplaceIdTextField;
        @FXML
        private TextField districtIdTextField;
        @FXML
        private TextField regionIdTextField;
        @FXML
        private TextField valueTextField;
        @FXML
        private TextArea notesTextArea;
        @FXML
        private Label errorLabel;
        @FXML
        private TextField timeTextField;

        private LocalDateTime localDateTime;

        private Random random = new Random();

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
            this.regionIdTextField.setTextFormatter(formatter1);
            this.districtIdTextField.setTextFormatter(formatter2);
            this.workplaceIdTextField.setTextFormatter(formatter3);
            this.valueTextField.setTextFormatter(formatter4);
            this.patientIdTextField.setTextFormatter(formatter5);


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

            this.basicOperations.one(localDateTime, patientIdTextField.getText(),
                    this.random.nextInt(0,Integer.MAX_VALUE),
                    Integer.valueOf(this.districtIdTextField.getText()), Integer.valueOf(this.regionIdTextField.getText()),
                    Integer.valueOf(this.workplaceIdTextField.getText()),
                    Double.valueOf(this.valueTextField.getText()),
                    this.random.nextDouble() > 0.5, this.notesTextArea.getText());
            this.cleanForm();
        }

        private void cleanForm() {
            this.dateDatePicker.setValue(null);
            this.patientIdTextField.setText("");
            this.districtIdTextField.setText("");
            this.regionIdTextField.setText("");
            this.workplaceIdTextField.setText("");
            this.valueTextField.setText("");
            this.notesTextArea.setText("");
            this.errorLabel.setText("");
        }

    private boolean validate() {
        String region = this.regionIdTextField.getText().trim();
        String district = this.districtIdTextField.getText().trim();
        String workplace = this.workplaceIdTextField.getText().trim();
        String value = this.valueTextField.getText().trim();
        String notes = this.notesTextArea.getText().trim();
        String time = this.timeTextField.getText().trim();
        LocalDate date = this.dateDatePicker.getValue();


        // Validácia polí
        if (region.isEmpty()) {
            this.errorLabel.setText("❌ No region ID specified");
            this.regionIdTextField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            return false;
        }

        if (district.isEmpty()) {
            this.errorLabel.setText("❌ No district ID specified");
            this.districtIdTextField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            return false;
        }

        if (workplace.isEmpty()) {
            this.errorLabel.setText("❌ No workplace ID specified");
            this.workplaceIdTextField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            return false;
        }

        if (value.isEmpty()) {
            this.errorLabel.setText("❌ No value specified");
            this.valueTextField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            return false;
        }

        if (notes.isEmpty()) {
            this.errorLabel.setText("❌ No notes specified");
            this.notesTextArea.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            return false;
        }

        if (time.isEmpty()) {
            this.errorLabel.setText("❌ No time specified");
            this.timeTextField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            return false;
        }

        LocalTime parsedTime;
        try {
            parsedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (DateTimeParseException e) {
            this.errorLabel.setText("❌ Invalid time format (expected HH:mm:ss)");
            this.timeTextField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            return false;
        }

        if (date == null) {
            this.errorLabel.setText("❌ No date specified");
            this.dateDatePicker.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            return false;
        }

        // ✅ Vytvorenie LocalDateTime
        this.localDateTime = LocalDateTime.of(date, parsedTime);

        // ✅ Vyčistenie chybovej hlášky
        this.errorLabel.setText("");
        this.errorLabel.setVisible(false);

        return true;
    }



}



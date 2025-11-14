package com.milos970.controller;

import com.milos970.model.entity.PCRTest;
import com.milos970.model.entity.Patient;
import com.milos970.model.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class FilteringController {

    @FXML
    private RadioButton test;
    @FXML
    private RadioButton district;
    @FXML
    private RadioButton region;
    @FXML
    private RadioButton patient;
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
    private TextField patientIdTextField;

    @FXML
    private TextField testIdTextField;

    @FXML
    private TextField regionIdTextField;

    @FXML
    private TextField districtIdTextField;

    @FXML
    private TextField workplaceIdTextField;

    @FXML
    private VBox vBox;

    @FXML
    private Button filterButton;

    @FXML
    private TextField idTextField;

    @FXML
    private Label errorLabel;

    private final HashMap<String, List<String>> map = new HashMap<>();

    private BasicOperations basicOperations;
    private int currentOperation = -1;
    private String selectedOperation = null;

    public void setBasicOperations(BasicOperations basicOperations) {
        this.basicOperations = basicOperations;
    }

    @FXML
    public void initialize() {

        this.map.put("PCRTest", Arrays.stream(TestSearchType.values())
                .map(Enum::name)
                .toList());

        this.map.put("Patient", Arrays.stream(PatientSearchType.values())
                .map(Enum::name)
                .toList());

        this.map.put("District", Arrays.stream(DistrictSearchType.values())
                .map(Enum::name)
                .toList());

        this.map.put("Region", Arrays.stream(RegionSearchType.values())
                .map(Enum::name)
                .toList());
        toggleGroup.selectToggle(toggleGroup.getToggles().get(0));
        this.checkCategory();
        this.handleOperationSwitch(2);

    }

    @FXML
    private void checkCategory() {
        if (toggleGroup.getSelectedToggle() != null) {
            RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle();
            this.combobox.getItems().clear();
            this.combobox.getItems().addAll(this.map.get(selected.getText()));
            this.combobox.setValue(this.map.get(selected.getText()).get(0));
        }



    }

    @FXML
    private void checkOperation() {
        String selectedOperation = combobox.getValue().toString();
        RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle();

        int whichOperation = switch (selected.getText()) {
            case "PCRTest" -> TestSearchType.fromName(selectedOperation).getIndex();
            case "Patient" -> PatientSearchType.fromName(selectedOperation).getIndex();
            case "District" -> DistrictSearchType.fromName(selectedOperation).getIndex();
            case "Region" -> RegionSearchType.fromName(selectedOperation).getIndex();
            default -> throw new IllegalArgumentException("Unknown type: " + selected.getText());
        };

        this.currentOperation = whichOperation;
        handleOperationSwitch(whichOperation);
    }


    private void hideAllInputs() {
        vBox.getChildren().removeAll(
                testIdTextField,
                patientIdTextField,
                districtIdTextField,
                regionIdTextField,
                fromDatePicker,
                toDatePicker,
                xTextField,
                workplaceIdTextField
        );
    }


    private void showNode(Node node) {
        if (!vBox.getChildren().contains(node)) {
            vBox.getChildren().add(vBox.getChildren().size() - 1, node);
        }
    }


    private void handleOperationSwitch(int whichOperation) {
        hideAllInputs();
        switch (whichOperation) {
            case 2 -> {
                showNode(patientIdTextField);
                showNode(testIdTextField);
            }
            case 3 -> showNode(patientIdTextField);
            case 4, 5 -> {
                showNode(districtIdTextField);
                showNode(fromDatePicker);
                showNode(toDatePicker);
            }
            case 6, 7 -> {
                showNode(regionIdTextField);
                showNode(fromDatePicker);
                showNode(toDatePicker);
            }
            case 8, 9 -> {
                showNode(fromDatePicker);
                showNode(toDatePicker);
            }
            case 10, 11 -> {
                showNode(districtIdTextField);
                showNode(fromDatePicker);
                showNode(xTextField);
            }
            case 12, 16 -> {
                showNode(regionIdTextField);
                showNode(fromDatePicker);
                showNode(xTextField);
            }
            case 13, 14, 15 -> {
                showNode(fromDatePicker);
                showNode(xTextField);
            }

            case 17 -> {
                showNode(workplaceIdTextField);
                showNode(fromDatePicker);
                showNode(toDatePicker);
            }

            case 18, 20 -> {
                showNode(testIdTextField);
            }

            case 21 -> {
                showNode(patientIdTextField);
            }

        }

        if (vBox.getChildren().contains(filterButton)) {
            vBox.getChildren().remove(filterButton);
        }
        vBox.getChildren().add(filterButton);
    }


    @FXML
    private void executeSelectedOperation() {
        if (basicOperations == null) {
            System.err.println("BasicOperations has not been set up!");
            return;
        }
        if (currentOperation == -1) {
            System.out.println("❗ Najprv vyber operáciu z menu!");
            return;
        }
        try {
            switch (currentOperation) {

                case 1 -> basicOperations.createPatient(
                        patientIdTextField.getText(),
                        "Meno",
                        "Priezvisko",
                        LocalDate.now()
                );
                case 2 ->
                {
                    Optional<PCRTest> test = basicOperations.two(Integer.valueOf(this.testIdTextField.getText()), this.patientIdTextField.getText());

                    if (test.isEmpty())
                    {
                        this.errorLabel.setText("Patient or test do not exist!");
                        return;
                    } else {
                        this.errorLabel.setText("");
                    }

                    ObservableList<String> observableList = FXCollections.observableArrayList();
                    observableList.add(formatTestAndPatientWithStars(test.get()));
                    listView.setItems(observableList);
                }
                case 3 -> {
                    List<PCRTest> result = basicOperations.three(patientIdTextField.getText());
                    if (result.isEmpty())
                    {
                        this.errorLabel.setText("No tests!");
                        return;
                    } else {
                        this.errorLabel.setText("");
                    }
                    ObservableList<String> observableList = FXCollections.observableArrayList();
                     for (var test : result) {

                        observableList.add(formatTestAndPatientWithStars(test));
                    }
                    listView.setItems(observableList);
                }

                case 4 -> {
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    LocalDateTime to = toDatePicker.getValue().atTime(23, 59);
                    List<PCRTest> result = basicOperations.four(
                            Integer.parseInt(districtIdTextField.getText()), from, to
                    );
                    if (result.isEmpty())
                    {
                        this.errorLabel.setText("No tests!");
                        return;
                    } else {
                        this.errorLabel.setText("");
                    }
                    ObservableList<String> observableList = FXCollections.observableArrayList();
                    for (var test : result) {
                        observableList.add(formatTestAndPatientWithStars(test));
                    }
                    listView.setItems(observableList);
                }

                case 5 -> {
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    LocalDateTime to = toDatePicker.getValue().atTime(23, 59);
                    List<PCRTest> result = basicOperations.five(
                            Integer.parseInt(districtIdTextField.getText()), from, to
                    );
                    if (result.isEmpty())
                    {
                        this.errorLabel.setText("No tests!");
                        return;
                    } else {
                        this.errorLabel.setText("");
                    }
                    ObservableList<String> observableList = FXCollections.observableArrayList();
                    for (var test : result) {
                        observableList.add(formatTestAndPatientWithStars(test));
                    }
                    listView.setItems(observableList);
                }

                case 6 -> {
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    LocalDateTime to = toDatePicker.getValue().atTime(23, 59);
                    List<PCRTest> result = basicOperations.six(
                            Integer.parseInt(regionIdTextField.getText()), from, to
                    );
                    if (result.isEmpty())
                    {
                        this.errorLabel.setText("No tests!");
                        return;
                    } else {
                        this.errorLabel.setText("");
                    }
                    ObservableList<String> observableList = FXCollections.observableArrayList();
                    for (var test : result) {
                        observableList.add(formatTestAndPatientWithStars(test));
                    }
                    listView.setItems(observableList);
                }

                case 7 -> {
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    LocalDateTime to = toDatePicker.getValue().atTime(23, 59);
                    List<PCRTest> result = basicOperations.seven(
                            Integer.parseInt(regionIdTextField.getText()), from, to
                    );
                    if (result.isEmpty())
                    {
                        this.errorLabel.setText("No tests!");
                        return;
                    } else {
                        this.errorLabel.setText("");
                    }
                    ObservableList<String> observableList = FXCollections.observableArrayList();
                    for (var test : result) {
                        observableList.add(formatTestAndPatientWithStars(test));
                    }
                    listView.setItems(observableList);
                }

                case 8, 9 -> {
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    LocalDateTime to = toDatePicker.getValue().atTime(23, 59);
                    List<PCRTest> result = (currentOperation == 8)
                            ? basicOperations.eight(from, to)
                            : basicOperations.nine(from, to);
                    if (result.isEmpty())
                    {
                        this.errorLabel.setText("No tests!");
                        return;
                    } else {
                        this.errorLabel.setText("");
                    }
                    ObservableList<String> observableList = FXCollections.observableArrayList();
                    observableList.add(String.format("Number of records: %d", result.size()));
                    for (var test : result) {
                        observableList.add(formatTestAndPatientWithStars(test));
                    }
                    listView.setItems(observableList);
                }

                case 10, 11 -> {
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    int x = Integer.parseInt(xTextField.getText());
                    List<PCRTest> result = (currentOperation == 10)
                            ? basicOperations.teen(Integer.parseInt(districtIdTextField.getText()), from, x)
                            : basicOperations.eleven(Integer.parseInt(districtIdTextField.getText()), from, x);
                    if (result.isEmpty())
                    {
                        this.errorLabel.setText("No patients!");
                        return;
                    } else {
                        this.errorLabel.setText("");
                    }
                    ObservableList<String> observableList = FXCollections.observableArrayList();
                    for (var patient : result) {
                        observableList.add("************************************");
                        observableList.add(patient.toString());
                        observableList.add("************************************");
                    }
                    listView.setItems(observableList);
                }

                case 12, 13 ->
                {
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    int x = Integer.parseInt(xTextField.getText());
                    List<PCRTest> result = (currentOperation == 12)
                            ? basicOperations.twelve(Integer.valueOf(regionIdTextField.getText()), from, x)
                            : basicOperations.thirteen(from, x);
                    if (result.isEmpty())
                    {
                        this.errorLabel.setText("No patients!");
                        return;
                    } else {
                        this.errorLabel.setText("");
                    }
                    ObservableList<String> observableList = FXCollections.observableArrayList();
                    for (var test : result) {
                        observableList.add(formatTestAndPatientWithStars(test));
                    }
                    listView.setItems(observableList);
                }

                case 14 ->
                {
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    int x = Integer.parseInt(xTextField.getText());
                    List<Patient> result = basicOperations.fourteen(from, x);
                    if (result.isEmpty())
                    {
                        this.errorLabel.setText("No patients!");
                        return;
                    } else {
                        this.errorLabel.setText("");
                    }
                    ObservableList<String> observableList = FXCollections.observableArrayList();
                    for (var patient : result) {
                        observableList.add("************************************");
                        observableList.add(patient.toString());
                        observableList.add("************************************");
                    }
                    listView.setItems(observableList);
                }

                case 15, 16 ->
                {
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    int x = Integer.parseInt(xTextField.getText());
                    List<Map.Entry<Integer, Integer>> result = (currentOperation == 15)
                            ? basicOperations.fifteen(from, x)
                            : basicOperations.sixteen(from, x);
                    if (result.isEmpty())
                    {
                        this.errorLabel.setText("No patients!");
                        return;
                    } else {
                        this.errorLabel.setText("");
                    }
                    ObservableList<String> observableList = FXCollections.observableArrayList();
                    for (var entry : result) {
                        observableList.add("************************************");
                        observableList.add((currentOperation == 15 ? "Okres " : "Región ") + entry.getKey() + " → " + entry.getValue());
                        observableList.add("************************************");
                    }
                    listView.setItems(observableList);
                }

                case 17 ->
                {
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    LocalDateTime to = toDatePicker.getValue().atTime(23, 59);
                    List<PCRTest> tests = basicOperations.seventeen(
                            Integer.parseInt(workplaceIdTextField.getText()), from, to
                    );
                    if (tests.isEmpty())
                    {
                        this.errorLabel.setText("No patients!");
                        return;
                    } else {
                        this.errorLabel.setText("");
                    }
                    ObservableList<String> observableList = FXCollections.observableArrayList();
                    observableList.add(String.format("Number of records: %d", tests.size()));
                    for (var test : tests) {
                        observableList.add(formatTestAndPatientWithStars(test));
                    }
                    listView.setItems(observableList);
                }

                case 18 ->
                {
                    Optional<PCRTest> result = basicOperations.eighteen(Integer.parseInt(testIdTextField.getText()));

                    if (result.isEmpty())
                    {
                        this.errorLabel.setText("No test!");
                        return;
                    } else {
                        this.errorLabel.setText("");
                    }
                    ObservableList<String> observableList = FXCollections.observableArrayList();
                    result.ifPresent(test -> observableList.add(formatTestAndPatientWithStars(test)));
                    listView.setItems(observableList);
                }


                default -> System.out.println("Operácia " + currentOperation + " zatiaľ nie je implementovaná.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Chyba pri vykonávaní operácie " + currentOperation);
        }
    }

    private String formatTestAndPatientWithStars(PCRTest test) {
        if (test == null) {
            return "";
        }
        String separator = "************************************";
        return separator + "\n" +  test + "\n\n" + test.getPatient() + "\n" + separator;
    }


}
















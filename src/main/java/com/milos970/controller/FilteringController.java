package com.milos970.controller;

import com.milos970.model.service.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private CheckBox positiveCheckBox;

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

    private final HashMap<String, List<String>> map = new HashMap<>();

    private BasicOperations basicOperations;
    private int currentOperation = -1;

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

    /**
     * Odstráni všetky vstupné polia z VBoxu (okrem filterButton).
     */
    private void hideAllInputs() {
        vBox.getChildren().removeAll(
                testIdTextField,
                patientIdTextField,
                districtIdTextField,
                regionIdTextField,
                fromDatePicker,
                toDatePicker,
                xTextField,
                positiveCheckBox
        );
    }

    /**
     * Pridá komponent do VBoxu, ak tam ešte nie je.
     */
    private void showNode(Node node) {
        if (!vBox.getChildren().contains(node)) {
            vBox.getChildren().add(vBox.getChildren().size() - 1, node); // pred tlačidlo
        }
    }

    /**
     * Dynamicky nastaví, ktoré prvky sa majú zobraziť.
     */
    private void handleOperationSwitch(int whichOperation) {
        hideAllInputs();

        switch (whichOperation) {
            case 2 -> {
                showNode(patientIdTextField);
                showNode(testIdTextField);
            }
            case 3 -> showNode(patientIdTextField);
            case 4 -> {
                showNode(districtIdTextField);
                showNode(fromDatePicker);
                showNode(toDatePicker);
                showNode(positiveCheckBox);
            }
            case 5 -> {
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
            case 12 -> {
                showNode(regionIdTextField);
                showNode(fromDatePicker);
                showNode(xTextField);
            }
            case 13, 14, 15 -> {
                showNode(fromDatePicker);
                showNode(xTextField);
            }
        }

        // 🟩 zabezpečí, že filterButton je vždy na konci
        if (vBox.getChildren().contains(filterButton)) {
            vBox.getChildren().remove(filterButton);
        }
        vBox.getChildren().add(filterButton);
    }

    /**
     * 🔍 Spustí aktuálne zvolenú operáciu z BasicOperations podľa `currentOperation`
     */
    @FXML
    private void executeSelectedOperation() {
        if (basicOperations == null) {
            System.err.println("⚠️ BasicOperations nie je nastavené!");
            return;
        }
        if (currentOperation == -1) {
            System.out.println("❗ Najprv vyber operáciu z menu!");
            return;
        }

        try {
            switch (currentOperation) {

                case 1 -> {
                    // createPatient
                    basicOperations.createPatient(
                            patientIdTextField.getText(),
                            "Meno",
                            "Priezvisko",
                            LocalDate.now()
                    );
                    System.out.println("✅ Pacient vytvorený.");
                }

                case 2 -> {
                    // one()
                    basicOperations.one(
                            LocalDateTime.now(),
                            patientIdTextField.getText(),
                            Integer.parseInt(testIdTextField.getText()),
                            Integer.parseInt(districtIdTextField.getText()),
                            Integer.parseInt(regionIdTextField.getText()),
                            1, // workplaceId
                            10.5, // value
                            positiveCheckBox.isSelected(),
                            "Poznámka"
                    );
                    System.out.println("✅ Test uložený.");
                }

                case 3 -> {
                    // three()
                    var result = basicOperations.three(patientIdTextField.getText());
                    System.out.println("➡ Výsledky pre pacienta:");
                    result.forEach(System.out::println);
                }

                case 4 -> {
                    // four()
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    LocalDateTime to = toDatePicker.getValue().atTime(23, 59);
                    var result = basicOperations.four(Integer.parseInt(districtIdTextField.getText()), from, to);
                    System.out.println("➡ Výsledky PCRTest podľa okresu:");
                    result.forEach(System.out::println);
                }

                case 5 -> {
                    // five()
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    LocalDateTime to = toDatePicker.getValue().atTime(23, 59);
                    var result = basicOperations.five(Integer.parseInt(districtIdTextField.getText()), from, to);
                    System.out.println("➡ Výsledky testov (okres, čas):");
                    result.forEach(System.out::println);
                }

                case 6 -> {
                    // six()
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    LocalDateTime to = toDatePicker.getValue().atTime(23, 59);
                    var result = basicOperations.six(Integer.parseInt(regionIdTextField.getText()), from, to);
                    System.out.println("➡ Pozitívne testy podľa regiónu:");
                    result.forEach(System.out::println);
                }

                case 7 -> {
                    // seven()
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    LocalDateTime to = toDatePicker.getValue().atTime(23, 59);
                    var result = basicOperations.seven(Integer.parseInt(regionIdTextField.getText()), from, to);
                    System.out.println("➡ Všetky testy podľa regiónu:");
                    result.forEach(System.out::println);
                }

                case 8 -> {
                    // eight()
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    LocalDateTime to = toDatePicker.getValue().atTime(23, 59);
                    var result = basicOperations.eight(from, to);
                    System.out.println("➡ Výsledky operácie 8:");
                    result.forEach(System.out::println);
                }

                case 9 -> {
                    // nine()
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    LocalDateTime to = toDatePicker.getValue().atTime(23, 59);
                    var result = basicOperations.nine(from, to);
                    System.out.println("➡ Výsledky operácie 9:");
                    result.forEach(System.out::println);
                }

                case 10 -> {
                    // teen()
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    int x = Integer.parseInt(xTextField.getText());
                    var result = basicOperations.teen(Integer.parseInt(districtIdTextField.getText()), from, x);
                    System.out.println("➡ Výsledky operácie 10:");
                    result.forEach(System.out::println);
                }

                case 11 -> {
                    // eleven()
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    int x = Integer.parseInt(xTextField.getText());
                    var result = basicOperations.eleven(Integer.parseInt(districtIdTextField.getText()), from, x);
                    System.out.println("➡ Výsledky operácie 11:");
                    result.forEach(System.out::println);
                }

                case 12 -> {
                    // twelve()
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    int x = Integer.parseInt(xTextField.getText());
                    var result = basicOperations.twelve(from, x);
                    System.out.println("➡ Pozitívne testy v období:");
                    result.forEach(System.out::println);
                }

                case 13 -> {
                    // thirteen()
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    int x = Integer.parseInt(xTextField.getText());
                    var result = basicOperations.thirteen(from, x);
                    System.out.println("➡ Všetky testy v období:");
                    result.forEach(System.out::println);
                }

                case 14 -> {
                    // fourteen()
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    int x = Integer.parseInt(xTextField.getText());
                    var result = basicOperations.fourteen(from, x);
                    System.out.println("➡ Pacienti operácie 14:");
                    result.forEach(System.out::println);
                }

                case 15 -> {
                    // fifteen()
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    int x = Integer.parseInt(xTextField.getText());
                    var result = basicOperations.fifteen(from, x);
                    System.out.println("➡ Počet testov podľa okresov:");
                    result.forEach(e -> System.out.println("Okres " + e.getKey() + " → " + e.getValue()));
                }

                case 16 -> {
                    // sixteen()
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    int x = Integer.parseInt(xTextField.getText());
                    var result = basicOperations.sixteen(from, x);
                    System.out.println("➡ Počet testov podľa regiónov:");
                    result.forEach(e -> System.out.println("Región " + e.getKey() + " → " + e.getValue()));
                }

                case 17 -> {
                    // seventeen()
                    LocalDateTime from = fromDatePicker.getValue().atStartOfDay();
                    LocalDateTime to = toDatePicker.getValue().atTime(23, 59);
                    var result = basicOperations.seventeen(
                            Integer.parseInt(districtIdTextField.getText()), from, to
                    );
                    System.out.println("➡ Pacienti podľa pracoviska:");
                    result.forEach(System.out::println);
                }

                case 18 -> {
                    // eighteen()
                    var result = basicOperations.eighteen(Integer.parseInt(testIdTextField.getText()));
                    System.out.println("➡ Test s ID:");
                    System.out.println(result.orElse(null));
                }

                case 19 -> {
                    // nineteen()
                    basicOperations.nineteen(
                            "Meno",
                            "Priezvisko",
                            LocalDate.now(),
                            patientIdTextField.getText()
                    );
                    System.out.println("✅ Pacient uložený.");
                }

                case 20 -> {
                    // twenty()
                    basicOperations.twenty(Integer.parseInt(testIdTextField.getText()));
                    System.out.println("✅ Test odstránený.");
                }

                case 21 -> {
                    // twentyOne()
                    String patientId = patientIdTextField.getText();
                    basicOperations.twentyOne(patientId);
                    System.out.println("✅ Pacient " + patientId + " a jeho testy boli odstránené.");
                }

                default -> System.out.println("⚠️ Operácia " + currentOperation + " zatiaľ nie je implementovaná.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Chyba pri vykonávaní operácie " + currentOperation);
        }
    }




}
















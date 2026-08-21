package com.milos970;


import com.milos970.controller.MenuController;
import com.milos970.model.repository.InMemoryPatientRepository;
import com.milos970.model.repository.InMemoryTestRepository;
import com.milos970.model.repository.PatientRepository;
import com.milos970.model.repository.TestRepository;
import com.milos970.model.service.BasicOperations;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presenter/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Evidencia PCR testov");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        PatientRepository patientRepository = new InMemoryPatientRepository();
        TestRepository testRepository = new InMemoryTestRepository();

        var basicOperations = new BasicOperations(testRepository, patientRepository);

        MenuController menuController = loader.getController();

        menuController.getFilteringController().setBasicOperations(basicOperations);
        menuController.getPatientController().setBasicOperations(basicOperations);
        menuController.getTestController().setBasicOperations(basicOperations);
        menuController.getImportExportController().setBasicOperations(basicOperations);
    }

    public static void main(String[] args) {


        launch(args);


    }
}

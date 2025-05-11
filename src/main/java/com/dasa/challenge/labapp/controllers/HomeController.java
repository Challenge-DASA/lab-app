package com.dasa.challenge.labapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomeController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onStartProcedure() {
        callTheProcedureScreen();
    }

    private void callTheProcedureScreen() {
        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(getClass()
                            .getResource("/com/dasa/challenge/labapp/views/confirm-procedure/confirm-procedure.fxml"));

            Parent root = fxmlLoader.load();

            Stage stage = (Stage) welcomeText.getScene().getWindow();

            Scene procedureScene = new Scene(root, 800, 600);

            procedureScene.getStylesheets().clear();
            procedureScene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/com/dasa/challenge/labapp/styles/confirm-procedure/confirm-procedure.css")).toExternalForm());

            stage.setScene(procedureScene);
            stage.show();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

}
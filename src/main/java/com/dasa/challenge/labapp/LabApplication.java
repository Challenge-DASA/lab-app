package com.dasa.challenge.labapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LabApplication extends Application {
    // we need to "manually" set where and what styles resources it needs to load ~ lipe
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LabApplication.class.getResource("/com/dasa/challenge/labapp/views/home/home.fxml"));

        Parent root = fxmlLoader.load();
        StackPane rootContainer = new StackPane();
        rootContainer.getChildren().add(root);

        Scene scene = new Scene(rootContainer, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/com/dasa/challenge/labapp/styles/home/home.css")
        ).toExternalForm());

        stage.setTitle("SmartLab Inventory");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
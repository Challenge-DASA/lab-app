package com.dasa.challenge.labapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LabApplication extends Application {
    // we need to "manually" set where and what styles resources it needs to load ~ lipe
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LabApplication.class.getResource("/com/dasa/challenge/labapp/views/home/home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/com/dasa/challenge/labapp/styles/home/home.css")).toExternalForm());
        stage.setTitle("SmartLab Inventory");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
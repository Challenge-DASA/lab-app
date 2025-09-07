package com.dasa.challenge.labapp.infrastructure.controllers;

import com.dasa.challenge.labapp.application.usecases.home.HomePageUseCase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomeController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button startWithdrawButton;

    private final HomePageUseCase homePageUseCase;

    public HomeController(HomePageUseCase homePageUseCase) {
        this.homePageUseCase = homePageUseCase;
    }

    public Parent getView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    HomeController.class.getResource("/com/dasa/challenge/labapp/views/home/home.fxml")
            );
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();

            // Attach CSS
            root.getStylesheets().add(Objects.requireNonNull(
                    getClass().getResource("/com/dasa/challenge/labapp/styles/home/home.css")
            ).toExternalForm());

            return root;
        } catch (IOException err) {
            throw new RuntimeException("Failed to load home view", err);
        }
    }

    @FXML
    private void initialize() {
        startWithdrawButton.setOnAction(event -> handleStartWithdrawAction());
    }

    private void handleStartWithdrawAction() {
        homePageUseCase.handleStartWithdraw();
    }

}
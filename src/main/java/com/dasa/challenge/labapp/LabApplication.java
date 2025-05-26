package com.dasa.challenge.labapp;

import com.dasa.challenge.labapp.application.usecases.home.HomePageUseCase;
import com.dasa.challenge.labapp.infrastructure.config.AppConfig;
import javafx.application.Application;
import javafx.stage.Stage;

public class LabApplication extends Application {

    @Override
    public void start(Stage stage) {
        HomePageUseCase homePageUseCase = AppConfig.homePageUseCaseImpl(stage);
        homePageUseCase.start();
    }


    public static void main(String[] args) {
        launch();
    }
}
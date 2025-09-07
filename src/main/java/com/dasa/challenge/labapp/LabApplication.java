package com.dasa.challenge.labapp;

import com.dasa.challenge.labapp.application.usecases.home.HomePageUseCase;
import com.dasa.challenge.labapp.infrastructure.config.AppConfig;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LabApplication extends Application {

    private static Stage mainStage;
    private static Scene mainScene;
    private static StackPane rootContainer;

    @Override
    public void start(Stage stage) {
        mainStage = stage;

        rootContainer = new StackPane();
        mainScene = new Scene(rootContainer, 800, 600);

        stage.setScene(mainScene);
        stage.setFullScreen(true);
        stage.setTitle("SmartLab Inventory");
        stage.show();

        HomePageUseCase homePageUseCase = AppConfig.homePageUseCaseImpl(stage);
        homePageUseCase.start();
    }


    public static void setView(Parent newView) {
        rootContainer.getChildren().setAll(newView);
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        launch();
    }
}
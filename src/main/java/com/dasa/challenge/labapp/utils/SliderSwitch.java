package com.dasa.challenge.labapp.utils;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class SliderSwitch {
    public static <T> void slide(Stage stage, Class<T> controllerClass, String fxmlPath, String cssPath) {
        try {
            new Thread(() -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(SliderSwitch.class.getResource(fxmlPath));
                    T controllerInstance = controllerClass.getDeclaredConstructor().newInstance();
                    fxmlLoader.setController(controllerInstance);
                    fxmlLoader.setRoot(controllerInstance);
                    Parent newRoot = fxmlLoader.load();

                    javafx.application.Platform.runLater(() -> {
                        StackPane rootContainer = (StackPane) stage.getScene().getRoot();
                        Parent currentRoot = (Parent) rootContainer.getChildren().get(0);

                        newRoot.setTranslateX(stage.getWidth());
                        rootContainer.getChildren().add(newRoot);

                        TranslateTransition slideOut = new TranslateTransition(Duration.millis(500), currentRoot);
                        slideOut.setFromX(0);
                        slideOut.setToX(-stage.getWidth());

                        TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), newRoot);
                        slideIn.setFromX(stage.getWidth());
                        slideIn.setToX(0);

                        ParallelTransition transition = new ParallelTransition(slideOut, slideIn);
                        transition.setOnFinished(event -> {
                            rootContainer.getChildren().remove(currentRoot);
                        });

                        Scene scene = stage.getScene();
                        scene.getStylesheets().add(Objects.requireNonNull(
                                SliderSwitch.class.getResource(cssPath)).toExternalForm()
                        );

                        transition.play();
                    });

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

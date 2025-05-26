package com.dasa.challenge.labapp.utils;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class SliderSwitch {
    public static void slideToUseCase(Stage stage, Runnable nextUseCaseStart, String cssPath) {
        try {
            javafx.application.Platform.runLater(() -> {
                try {
                    Scene scene = stage.getScene();
                    scene.getStylesheets().clear();

                    if (cssPath != null) {
                        scene.getStylesheets().add(Objects.requireNonNull(
                                SliderSwitch.class.getResource(cssPath)).toExternalForm()
                        );
                    }

                    FadeTransition ft = new FadeTransition(Duration.millis(300), scene.getRoot());
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();

                    nextUseCaseStart.run();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
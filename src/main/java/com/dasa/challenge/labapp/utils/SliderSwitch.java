package com.dasa.challenge.labapp.utils;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class SliderSwitch {
    public static void slideToUseCase(Stage stage, Runnable nextUseCaseStart, String cssPath) {
        javafx.application.Platform.runLater(() -> {
            Scene scene = stage.getScene();
            if (scene != null) {
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
            }

            nextUseCaseStart.run();

            if (!stage.isFullScreen()) {
                stage.setFullScreen(true);
            }
        });
    }


}
package com.dasa.challenge.labapp.infrastructure.components;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;

public class RfidReaderComponent extends StackPane {

    @FXML
    private StackPane iconContainer;

    @FXML
    private Rectangle cardIcon;

    @FXML
    private Circle statusCircle;

    @FXML
    private Arc waveArc1;

    @FXML
    private Arc waveArc2;

    @FXML
    private Arc waveArc3;

    private Timeline waveAnimation;

    public RfidReaderComponent() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/dasa/challenge/labapp/views/rfid-auth/components/rfid-reader.fxml")
            );
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load RFID reader component", e);
        }
    }

    @FXML
    private void initialize() {
        startWaveAnimation();
    }

    private void startWaveAnimation() {
        // Create wave animation effect
        FadeTransition wave1Fade = createWaveFade(waveArc1, 0);
        FadeTransition wave2Fade = createWaveFade(waveArc2, 0.3);
        FadeTransition wave3Fade = createWaveFade(waveArc3, 0.6);

        wave1Fade.play();
        wave2Fade.play();
        wave3Fade.play();
    }

    private FadeTransition createWaveFade(Arc arc, double delay) {
        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), arc);
        fade.setFromValue(0.8);
        fade.setToValue(0);
        fade.setCycleCount(Animation.INDEFINITE);
        fade.setDelay(Duration.seconds(delay));
        fade.setAutoReverse(true);
        return fade;
    }

    public void showSuccess() {
        // Stop wave animation
        waveArc1.setVisible(false);
        waveArc2.setVisible(false);
        waveArc3.setVisible(false);

        // Change status circle to green with scale animation
        statusCircle.setFill(Color.web("#4CAF50"));
        ScaleTransition scale = new ScaleTransition(Duration.millis(300), statusCircle);
        scale.setFromX(1);
        scale.setFromY(1);
        scale.setToX(1.2);
        scale.setToY(1.2);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        scale.play();
    }

    public void showError() {
        // Stop wave animation temporarily
        waveArc1.setVisible(false);
        waveArc2.setVisible(false);
        waveArc3.setVisible(false);

        // Change status circle to red with shake animation
        statusCircle.setFill(Color.web("#F44336"));
        TranslateTransition shake = new TranslateTransition(Duration.millis(100), iconContainer);
        shake.setFromX(-5);
        shake.setToX(5);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        shake.play();
    }

    public void reset() {
        // Reset status circle
        statusCircle.setFill(Color.web("#3450D8"));

        // Restart wave animation
        waveArc1.setVisible(true);
        waveArc2.setVisible(true);
        waveArc3.setVisible(true);
        startWaveAnimation();
    }
}
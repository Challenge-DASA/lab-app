package com.dasa.challenge.labapp.controllers.pages;

import com.dasa.challenge.labapp.utils.SliderSwitch;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onStartProcedure() {
        callTheProcedureScreen();
    }

    private void callTheProcedureScreen() {
        try {
            Scene scene = welcomeText.getScene();
            Stage stage = (Stage) scene.getWindow();

            SliderSwitch.slide(stage,
                    "/com/dasa/challenge/labapp/views/confirm-procedure/confirm-procedure.fxml",
                    "/com/dasa/challenge/labapp/styles/confirm-procedure/confirm-procedure.css");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
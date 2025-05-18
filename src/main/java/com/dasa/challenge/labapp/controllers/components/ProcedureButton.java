package com.dasa.challenge.labapp.controllers.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

public class ProcedureButton extends Button {

    public ProcedureButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/com/dasa/challenge/labapp/views/confirm-procedure/components/procedure-button.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

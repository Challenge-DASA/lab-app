package com.dasa.challenge.labapp.infrastructure.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

public class ProcedureButtonComponent extends Button {

    public ProcedureButtonComponent() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/com/dasa/challenge/labapp/views/confirm-procedure/components/procedure-button.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

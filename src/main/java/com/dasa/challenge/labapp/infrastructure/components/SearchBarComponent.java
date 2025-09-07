package com.dasa.challenge.labapp.infrastructure.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SearchBarComponent extends VBox {
    @FXML
    private TextField searchField;

    public SearchBarComponent() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/dasa/challenge/labapp/views/confirm-procedure/components/search-bar.fxml")
            );
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load search bar component", e);
        }
    }

    public TextField getSearchField() {
        return searchField;
    }
}

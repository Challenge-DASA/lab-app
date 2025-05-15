package com.dasa.challenge.labapp.controllers.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SearchBar extends VBox {
    @FXML
    private TextField searchField;

    public SearchBar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/com/dasa/challenge/labapp/views/confirm-procedure/components/search-bar.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TextField getSearchField() {
        return searchField;
    }

    public void setSearchField(TextField searchField) {
        this.searchField = searchField;
    }
}
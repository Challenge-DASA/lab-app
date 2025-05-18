package com.dasa.challenge.labapp.controllers.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Procedure extends VBox {
    @FXML
    private Text procedureTitle;

    @FXML
    private Text procedureItems;

    @FXML
    private Text procedureDescription;

    @FXML
    private Button procedureButton;

    private boolean isSelected = false;

    public Procedure() {
        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(getClass().getResource(
                            "/com/dasa/challenge/labapp/views/confirm-procedure/components/procedure.fxml"
                    ));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Text getProcedureDescription() {
        return procedureDescription;
    }

    public void setProcedureDescription(Text procedureDescription) {
        this.procedureDescription = procedureDescription;
    }

    public Text getProcedureItems() {
        return procedureItems;
    }

    public void setProcedureItems(Text procedureItems) {
        this.procedureItems = procedureItems;
    }

    public Text getProcedureTitle() {
        return procedureTitle;
    }

    public void setProcedureTitle(Text procedureTitle) {
        this.procedureTitle = procedureTitle;
    }
}

package com.dasa.challenge.labapp.infrastructure.components;

import com.dasa.challenge.labapp.domain.entities.Procedure;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.function.Consumer;

public class ProcedureComponent extends VBox {

    @FXML
    private Text procedureTitle;
    @FXML
    private Text procedureItems;
    @FXML
    private Text procedureDescription;
    @FXML
    private Button procedureButton;
    @FXML
    private VBox procedureContainer;

    private Procedure procedure;
    private Consumer<Procedure> onSelectionToggle;

    public ProcedureComponent() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/dasa/challenge/labapp/views/confirm-procedure/components/procedure.fxml")
            );
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load procedure component", e);
        }
    }

    @FXML
    private void initialize() {
        procedureButton.setOnAction(event -> handleButtonClick());
    }

    private void handleButtonClick() {
        if (procedure != null && onSelectionToggle != null) {
            onSelectionToggle.accept(procedure);
        }
    }

    public void bindProcedure(Procedure procedure) {
        this.procedure = procedure;
        updateView();
    }

    public void setOnSelectionToggle(Consumer<Procedure> callback) {
        this.onSelectionToggle = callback;
    }

    public void updateView() {
        if (procedure != null) {
            procedureTitle.setText(procedure.getDisplayName(30));
            procedureDescription.setText(procedure.getDisplayDescription(40));
            procedureItems.setText(procedure.getFormattedItems());

            // Update selection state
            updateSelectionState();
        }
    }

    private void updateSelectionState() {
        System.out.println("Updating selection state for procedure: " + procedure.getName());
        if (procedure.isSelected()) {
            procedureContainer.getStyleClass().removeAll("unselected");
            procedureContainer.getStyleClass().add("selected");
            procedureButton.setText("Remover Procedimento");
        } else {
            procedureContainer.getStyleClass().removeAll("selected");
            procedureContainer.getStyleClass().add("unselected");
            procedureButton.setText("Adicionar Procedimento");
        }
    }

    public Procedure getProcedure() {
        return procedure;
    }
}
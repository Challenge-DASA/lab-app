package com.dasa.challenge.labapp.infrastructure.components;

import com.dasa.challenge.labapp.domain.entities.Procedure;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SelectedProceduresBar extends VBox {

    @FXML
    private Label selectedCountLabel;

    @FXML
    private ScrollPane selectedProceduresScrollPane;

    @FXML
    private HBox selectedProceduresContainer;

    @FXML
    private Button continueButton;

    private ArrayList<Procedure> selectedProcedures = new ArrayList<>();
    private Consumer<ArrayList<Procedure>> onContinueClick;

    public SelectedProceduresBar() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/dasa/challenge/labapp/views/confirm-procedure/components/selected-procedures-bar.fxml")
            );
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load selected procedures bar", e);
        }
    }

    @FXML
    private void initialize() {
        updateView();
        continueButton.setOnAction(event -> handleContinue());

        setVisible(false);
        setManaged(false);
    }

    public void updateSelectedProcedures(List<Procedure> procedures) {
        this.selectedProcedures.clear();
        this.selectedProcedures.addAll(procedures);
        updateView();
    }

    private void updateView() {
        boolean hasSelected = !selectedProcedures.isEmpty();

        setVisible(hasSelected);
        setManaged(hasSelected);

        if (hasSelected) {
            selectedCountLabel.setText(selectedProcedures.size() + " Selecionados");

            selectedProceduresContainer.getChildren().clear();

            for (Procedure procedure : selectedProcedures) {
                selectedProceduresContainer.getChildren().add(createSelectedProcedureItem(procedure));
            }

            continueButton.setDisable(false);
        }
    }

    private HBox createSelectedProcedureItem(Procedure procedure) {
        HBox item = new HBox(5);
        item.getStyleClass().add("selected-procedure-item");

        Label nameLabel = new Label(procedure.getName());
        nameLabel.getStyleClass().add("selected-procedure-name");

        item.getChildren().add(nameLabel);

        return item;
    }

    private void handleContinue() {
        if (onContinueClick != null && !selectedProcedures.isEmpty()) {
            onContinueClick.accept(this.selectedProcedures);
        }
    }

    public void setOnContinueClick(Consumer<ArrayList<Procedure>> handler) {
        this.onContinueClick = handler;
    }

    public void bindToCart(ObservableList<Procedure> procedures) {
        procedures.addListener((ListChangeListener<Procedure>) change -> {
            this.selectedProcedures.clear();
            this.selectedProcedures.addAll(procedures);
            updateView();
        });
    }
}
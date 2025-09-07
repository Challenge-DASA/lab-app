package com.dasa.challenge.labapp.infrastructure.controllers;

import com.dasa.challenge.labapp.application.usecases.confirmProcedure.ConfirmProcedureUseCase;
import com.dasa.challenge.labapp.domain.entities.Procedure;
import com.dasa.challenge.labapp.infrastructure.components.ProcedureComponent;
import com.dasa.challenge.labapp.infrastructure.components.SearchBarComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfirmProcedureController {

    @FXML
    private FlowPane proceduresContainer;

    @FXML
    private SearchBarComponent searchBar;

    @FXML
    private Button confirmButton;

    private final ConfirmProcedureUseCase confirmProcedureUseCase;
    private List<Procedure> allProcedures = new ArrayList<>();
    private List<ProcedureComponent> procedureComponents = new ArrayList<>();

    public ConfirmProcedureController(ConfirmProcedureUseCase confirmProcedureUseCase) {
        this.confirmProcedureUseCase = confirmProcedureUseCase;
    }

    public Parent getView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    ConfirmProcedureController.class.getResource(
                            "/com/dasa/challenge/labapp/views/confirm-procedure/confirm-procedure.fxml"
                    )
            );
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();

            root.getStylesheets().add(Objects.requireNonNull(
                    getClass().getResource(
                            "/com/dasa/challenge/labapp/styles/confirm-procedure/confirm-procedure.css"
                    ).toExternalForm()));

            initializePageLogic();

            return root;
        } catch (IOException err) {
            throw new RuntimeException("Failed to load confirm procedure view", err);
        }
    }

    @FXML
    private void initialize() {
        if (confirmButton != null) {
            confirmButton.setOnAction(event -> handleConfirmAction());
        }
    }

    private void initializePageLogic() {
        setupSearchListener();
        loadAndDisplayProcedures();
    }

    private void setupSearchListener() {
        if (searchBar != null) {
            searchBar.getSearchField().setOnKeyTyped(event -> {
                filterAndDisplayProcedures(searchBar.getSearchField().getText());
            });
        }
    }

    private void loadAndDisplayProcedures() {
        try {
            allProcedures = confirmProcedureUseCase.loadProcedures();
            createProcedureComponents();
            displayAllProcedures();
        } catch (Exception e) {
            System.out.println(e);
            showError("Failed to load procedures: " + e.getMessage());
        }
    }

    private void createProcedureComponents() {
        procedureComponents.clear();

        for (Procedure procedure : allProcedures) {
            ProcedureComponent component = new ProcedureComponent();
            component.bindProcedure(procedure);
            component.setOnSelectionToggle(this::handleProcedureSelectionToggle);
            procedureComponents.add(component);
        }
    }

    private void displayAllProcedures() {
        proceduresContainer.getChildren().clear();
        proceduresContainer.getChildren().addAll(procedureComponents);
    }

    private void filterAndDisplayProcedures(String searchText) {
        List<Procedure> filteredProcedures = confirmProcedureUseCase
                .filterProcedures(allProcedures, searchText);

        proceduresContainer.getChildren().clear();

        procedureComponents.stream()
                .filter(component -> filteredProcedures.contains(component.getProcedure()))
                .forEach(component -> proceduresContainer.getChildren().add(component));
    }

    private void handleProcedureSelectionToggle(Procedure procedure) {
        if (procedure.isSelected()) {
            confirmProcedureUseCase.deselectProcedure(procedure);
        } else {
            confirmProcedureUseCase.selectProcedure(procedure);
        }

        procedureComponents.stream()
                .filter(component -> component.getProcedure().equals(procedure))
                .findFirst()
                .ifPresent(ProcedureComponent::updateView);

        updateConfirmButtonState();
    }

    private void updateConfirmButtonState() {
        boolean hasSelectedProcedures = !confirmProcedureUseCase.getSelectedProcedures().isEmpty();
        confirmButton.setDisable(!hasSelectedProcedures);
    }

    @FXML
    private void handleConfirmAction() {
        try {
            if (confirmProcedureUseCase.getSelectedProcedures().isEmpty()) {
                showError("Please select at least one procedure to continue.");
                return;
            }

            confirmProcedureUseCase.confirmSelectedProcedures();
            showSuccess("Procedures confirmed successfully!");

        } catch (IllegalStateException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            showError("Failed to confirm procedures: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public List<Procedure> getSelectedProcedures() {
        return confirmProcedureUseCase.getSelectedProcedures();
    }
}
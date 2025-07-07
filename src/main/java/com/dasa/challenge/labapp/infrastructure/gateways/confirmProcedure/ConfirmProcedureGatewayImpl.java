package com.dasa.challenge.labapp.infrastructure.gateways.confirmProcedure;

import com.dasa.challenge.labapp.application.dtos.ProcedureDTO;
import com.dasa.challenge.labapp.application.gateways.apiClient.ApiClientGateway;
import com.dasa.challenge.labapp.application.gateways.confirmProcedure.ConfirmProcedureGateway;
import com.dasa.challenge.labapp.domain.entities.Procedure;
import com.dasa.challenge.labapp.domain.entities.ProcedureItem;
import com.dasa.challenge.labapp.infrastructure.components.ProcedureComponent;
import com.dasa.challenge.labapp.infrastructure.components.SearchBarComponent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConfirmProcedureGatewayImpl implements ConfirmProcedureGateway {

    @FXML
    private FlowPane proceduresContainer;

    @FXML
    private SearchBarComponent searchBar;

    @FXML
    private Button confirmButton;

    private final Stage stage;
    private final ApiClientGateway apiClientGateway;
    private List<Procedure> allProcedures = new ArrayList<>();
    private List<ProcedureComponent> procedureComponents = new ArrayList<>();
    private final List<Procedure> selectedProcedures = new ArrayList<>();


    public ConfirmProcedureGatewayImpl(Stage stage, ApiClientGateway apiClientGateway) {
        this.stage = stage;
        this.apiClientGateway = apiClientGateway;
    }

    @Override
    public Parent start() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    ConfirmProcedureGatewayImpl.class.getResource(
                            "/com/dasa/challenge/labapp/views/confirm-procedure/confirm-procedure.fxml"
                    )
            );
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();

            initializePageLogic();

            if (stage.getScene() == null) {
                StackPane rootContainer = new StackPane();
                rootContainer.getChildren().add(root);

                Scene scene = new Scene(rootContainer, 800, 600);
                scene.getStylesheets().add(Objects.requireNonNull(
                        getClass().getResource(
                                "/com/dasa/challenge/labapp/styles/confirm-procedure/confirm-procedure.css"
                        )
                ).toExternalForm());

                this.stage.setTitle("Confirm Procedures - SmartLab Inventory");
                this.stage.setScene(scene);
                this.stage.show();
            }

            return root;

        } catch (IOException err) {
            throw new RuntimeException("Failed to load confirm procedure view", err);
        }
    }

    @Override
    public void nextPage() {
        // Handle navigation logic here
    }

    @Override
    public List<Procedure> loadProcedures() {
        List<ProcedureDTO> procedureDTOs = apiClientGateway.getProcedures();

        return procedureDTOs.stream()
                .map(this::mapDTOToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void selectProcedure(Procedure procedure) {
        if (!selectedProcedures.contains(procedure)) {
            procedure.select();
            selectedProcedures.add(procedure);
            System.out.println("Selected procedure: " + procedure.getName());
        }
    }

    @Override
    public void deselectProcedure(Procedure procedure) {
        if (selectedProcedures.contains(procedure)) {
            procedure.deselect();
            selectedProcedures.remove(procedure);
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
            allProcedures = this.loadProcedures();
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
        List<Procedure> filteredProcedures = this
                .filterProcedures(allProcedures, searchText);

        proceduresContainer.getChildren().clear();

        procedureComponents.stream()
                .filter(component -> filteredProcedures.contains(component.getProcedure()))
                .forEach(component -> proceduresContainer.getChildren().add(component));
    }

    private void handleProcedureSelectionToggle(Procedure procedure) {
        if (procedure.isSelected()) {
            this.deselectProcedure(procedure);
        } else {
            this.selectProcedure(procedure);
        }

        procedureComponents.stream()
                .filter(component -> component.getProcedure().equals(procedure))
                .findFirst()
                .ifPresent(ProcedureComponent::updateView);

        updateConfirmButtonState();
    }

    private void updateConfirmButtonState() {
        boolean hasSelectedProcedures = !this.getSelectedProcedures().isEmpty();
        confirmButton.setDisable(!hasSelectedProcedures);
    }

    @FXML
    private void handleConfirmAction() {
        try {
            if (this.getSelectedProcedures().isEmpty()) {
                showError("Please select at least one procedure to continue.");
                return;
            }

            this.confirmSelectedProcedures();
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
        return new ArrayList<>(selectedProcedures);
    }

    @Override
    public void confirmSelectedProcedures() {
        if (selectedProcedures.isEmpty()) {
            throw new IllegalStateException("No procedures selected");
        }

        selectedProcedures.forEach(p -> System.out.println("- " + p.getName()));
    }

    @Override
    public List<Procedure> filterProcedures(List<Procedure> procedures, String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return procedures;
        }

        String lowerSearchText = searchText.toLowerCase();
        return procedures.stream()
                .filter(procedure ->
                        procedure.getName().toLowerCase().contains(lowerSearchText) ||
                                procedure.getDescription().toLowerCase().contains(lowerSearchText))
                .collect(Collectors.toList());
    }

    private Procedure mapDTOToEntity(ProcedureDTO dto) {
        List<ProcedureItem> items = dto.procedureItems().stream()
                .map(itemDTO -> new ProcedureItem(itemDTO.itemName(), itemDTO.itemQuantity(), itemDTO.itemId().toString()))
                .collect(Collectors.toList());

        return new Procedure(
                dto.procedureId().toString(),
                dto.procedureName(),
                dto.procedureDescription(),
                items
        );
    }

}
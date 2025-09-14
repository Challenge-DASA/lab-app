package com.dasa.challenge.labapp.infrastructure.views.confirmProcedure;

import com.dasa.challenge.labapp.application.usecases.apiClient.ApiClientUseCase;
import com.dasa.challenge.labapp.application.usecases.cart.CartUseCase;
import com.dasa.challenge.labapp.application.views.auth.RfidAuthView;
import com.dasa.challenge.labapp.application.views.confirmProcedure.ConfirmProcedureView;
import com.dasa.challenge.labapp.domain.entities.Procedure;
import com.dasa.challenge.labapp.infrastructure.components.ProcedureComponent;
import com.dasa.challenge.labapp.infrastructure.components.SearchBarComponent;
import com.dasa.challenge.labapp.infrastructure.components.SelectedProceduresBar;
import com.dasa.challenge.labapp.infrastructure.utils.SliderSwitch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class ConfirmProcedureViewImpl implements ConfirmProcedureView {

    @FXML
    private FlowPane proceduresContainer;

    @FXML
    private SearchBarComponent searchBar;

    @FXML
    private SelectedProceduresBar selectedProceduresBar;

    private final Stage stage;
    private final ApiClientUseCase apiClientUseCase;
    private List<Procedure> allProcedures = new ArrayList<>();
    private final List<ProcedureComponent> procedureComponents = new ArrayList<>();
    private final CartUseCase cartUseCase;
    private final UUID laboratoryId;
    private final RfidAuthView rfidAuthView;

    public ConfirmProcedureViewImpl(Stage stage,
                                    ApiClientUseCase apiClientUseCase,
                                    CartUseCase cartUseCase,
                                    RfidAuthView rfidAuthView,
                                    UUID laboratoryId) {
        this.stage = stage;
        this.apiClientUseCase = apiClientUseCase;
        this.cartUseCase = cartUseCase;
        this.rfidAuthView = rfidAuthView;
        this.laboratoryId = laboratoryId;
    }

    @Override
    public Parent start() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    ConfirmProcedureViewImpl.class.getResource(
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
        SliderSwitch.slideTo(
                this.stage,
                () -> {
                    Parent newView = rfidAuthView.start();
                    stage.getScene().setRoot(newView);
                },
                "/com/dasa/challenge/labapp/styles/rfid-auth/rfid-auth.css"
        );

    }

    @Override
    public List<Procedure> loadProcedures() {
        return this.apiClientUseCase.getProcedures(laboratoryId);
    }

    @Override
    public void selectProcedure(Procedure procedure) {
        procedure.select();
        this.cartUseCase.add(procedure);
    }

    @Override
    public void deselectProcedure(Procedure procedure) {
        procedure.deselect();
        this.cartUseCase.remove(UUID.fromString(procedure.getId()));
    }

    @FXML
    private void initialize() {
        if (selectedProceduresBar != null) {
            selectedProceduresBar.setOnContinueClick(this::handleContinueWithProcedures);
            selectedProceduresBar.bindToCart(cartUseCase.watchCart());
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
    }

    private void handleContinueWithProcedures(ArrayList<Procedure> procedures) {
        try {
            confirmSelectedProcedures();
            nextPage();

        } catch (Exception e) {
            showError("Failed to confirm procedures: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(stage);
        alert.showAndWait();
    }

    // here in this method we should call the apiClient to confirm if the procedures ingredients are ok
    @Override
    public void confirmSelectedProcedures() {
        if (this.cartUseCase.getAll().isEmpty()) {
            throw new IllegalStateException("No procedures selected");
        }
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
}
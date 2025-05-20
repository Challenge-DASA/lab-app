package com.dasa.challenge.labapp.controllers.pages;

import com.dasa.challenge.labapp.controllers.components.Procedure;
import com.dasa.challenge.labapp.controllers.components.SearchBar;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class ConfirmProcedureController extends VBox {
    @FXML
    private Label titleText;

    @FXML
    private FlowPane proceduresContainer;

    @FXML
    private SearchBar searchBar;

    private ArrayList<Procedure> selectedProceduresList = new ArrayList<>();

    @FXML
    private void initialize() {
        getProcedures();

        ObservableList<Node> backupProceduresContainer =
                FXCollections.observableArrayList(proceduresContainer.getChildren());

        searchBar.getSearchField().setOnKeyTyped(event -> {
            String searchBarText = searchBar.getSearchField().getText();
            ObservableList<Node> proceduresContainerChildren = backupProceduresContainer;
            proceduresContainer.getChildren().clear();

            if (!searchBarText.isEmpty()) {
                for (Node proceduresContainerNode : proceduresContainerChildren) {
                    Procedure procedure = (Procedure) proceduresContainerNode;

                    if (procedure.getProcedureTitle().getText().toLowerCase()
                            .contains(searchBarText.toLowerCase())) {
                        proceduresContainer.getChildren().add(procedure);
                    }
                }
            } else {
                proceduresContainer.getChildren().addAll(backupProceduresContainer);
            }
        });
    }

    private void getProcedures() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(new File(Objects.requireNonNull
                    (getClass().getResource("/data/procedure_mock.json")).toURI()));

            JsonNode procedures = json.get("procedures");
            for (JsonNode procedure : procedures) {
                Procedure procedureComponent = new Procedure();

                procedureComponent.getProcedureTitle().setText(procedure.get("procedureName").asText().length() > 30 ?
                        procedure.get("procedureName").asText().substring(0, 27) + "..." :
                        procedure.get("procedureName").asText());

                procedureComponent.getProcedureDescription().setText(
                        procedure.get("procedureDescription").asText().length() > 40 ?
                                procedure.get("procedureDescription").asText().substring(0, 37) + "..." :
                                procedure.get("procedureDescription").asText()
                );

                procedureComponent.getProcedureItems().setText(getProcedureItemsString(procedure));

                procedureComponent.getProcedureButton().setOnMouseClicked(event -> {
                    System.out.println("New selected procedure: " + procedure.get("procedureName").asText());
                    selectedProceduresList.add(procedureComponent);
                });

                if (proceduresContainer != null) {
                    proceduresContainer.getChildren().add(procedureComponent);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getProcedureItemsString(JsonNode procedure) {
        StringBuilder procedureItemsBuilder = new StringBuilder();

        procedure.get("procedureItems").elements()
                .forEachRemaining(item -> {
                    if (!procedureItemsBuilder.isEmpty()) {
                        procedureItemsBuilder.append(", ");
                    }

                    procedureItemsBuilder
                            .append(item.get("itemName"))
                            .append("x")
                            .append(item.get("itemQuantity").toString());
                });

        return procedureItemsBuilder.length() > 40 ?
                procedureItemsBuilder.substring(0, 37) + "..." :
                procedureItemsBuilder.toString();
    }

    public Label getTitleText() {
        return titleText;
    }

    public void setTitleText(Label titleText) {
        this.titleText = titleText;
    }

    public FlowPane getProceduresContainer() {
        return proceduresContainer;
    }

    public void setProceduresContainer(FlowPane proceduresContainer) {
        this.proceduresContainer = proceduresContainer;
    }
}

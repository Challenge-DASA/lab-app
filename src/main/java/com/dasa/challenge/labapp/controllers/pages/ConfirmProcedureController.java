package com.dasa.challenge.labapp.controllers.pages;

import com.dasa.challenge.labapp.controllers.components.Procedure;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.Objects;

public class ConfirmProcedureController extends VBox {
    @FXML
    private Label titleText;

    @FXML
    private FlowPane proceduresContainer;

    @FXML
    private void initialize() {
        getProcedures();
    }

    private void getProcedures() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(new File(Objects.requireNonNull
                    (getClass().getResource("/data/procedure_mock.json")).toURI()));

            JsonNode procedures = json.get("procedures");
            for (JsonNode procedure : procedures) {
                Procedure procedureComponent = new Procedure();
                System.out.println("Actual procedure: " + procedure);

                procedureComponent.getProcedureTitle().setText(procedure.get("procedureName").asText().length() > 30 ?
                        procedure.get("procedureName").asText().substring(0, 27) + "..." :
                        procedure.get("procedureName").asText());

                procedureComponent.getProcedureDescription().setText(
                        procedure.get("procedureDescription").asText().length() > 40 ?
                                procedure.get("procedureDescription").asText().substring(0, 37) + "..." :
                                procedure.get("procedureDescription").asText()
                );

                procedureComponent.getProcedureItems().setText(getProcedureItemsString(procedure));

                if (proceduresContainer != null) {
                    proceduresContainer.getChildren().add(procedureComponent);
                    System.out.println("New procedure setted");
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

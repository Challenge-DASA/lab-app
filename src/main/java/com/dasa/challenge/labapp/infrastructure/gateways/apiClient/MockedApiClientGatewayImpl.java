package com.dasa.challenge.labapp.infrastructure.gateways.apiClient;

import com.dasa.challenge.labapp.application.gateways.apiClient.ApiClientGateway;
import com.dasa.challenge.labapp.domain.entities.Procedure;
import com.dasa.challenge.labapp.domain.entities.ProcedureItem;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class MockedApiClientGatewayImpl implements ApiClientGateway {

    @Override
    public ArrayList<Procedure> getLabProcedures(UUID laboratoryId) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(new File(Objects.requireNonNull
                    (getClass().getResource("/data/procedure_mock.json")).toURI()));
            ArrayList<Procedure> proceduresData = new ArrayList<>();

            JsonNode procedures = json.get("procedures");
            for (JsonNode procedure : procedures) {
                final UUID procedureId = UUID.fromString(procedure.get("procedureId").asText());
                final String procedureName = procedure.get("procedureName").asText();
                final String procedureDescription = procedure.get("procedureDescription").asText();
                final ArrayList<ProcedureItem> procedureItems = getProcedureItems(procedure);

                Procedure procedureData =
                        new Procedure(procedureId.toString(),
                                procedureName,
                                procedureDescription,
                                procedureItems);


                proceduresData.add(procedureData);
            }

            return proceduresData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<ProcedureItem> getProcedureItems(JsonNode procedure) {
        ArrayList<ProcedureItem> items = new ArrayList<>();

        procedure.get("procedureItems").elements()
                .forEachRemaining(item -> {
                    ProcedureItem procedureItem = new ProcedureItem(
                            item.get("itemName").asText(),
                            item.get("itemQuantity").asInt(),
                            UUID.fromString(item.get("itemId").asText()).toString()
                    );
                    items.add(procedureItem);
                });

        return items;
    }

    @Override
    public UUID withdrawMaterialsForProcedure(UUID laboratoryId, UUID procedureId, UUID rfidToken) {
        System.out.println("Mocked withdrawMaterialsForProcedure called with laboratoryId: " + laboratoryId
                + " and procedureId: " + procedureId + " and rfidToken: " + rfidToken);
        System.out.println("Withdraw successful, returning mock protocol UUID.");
        return UUID.randomUUID();
    }
}

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
    public ArrayList<Procedure> getProcedures() {
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

    @Override
    public UUID authenticateUser(String userId, String userPassword) {
        if (userId.isEmpty() || userPassword.isEmpty()) {
            return null;
        }

        return UUID.randomUUID();
    }

    @Override
    public UUID sendWithdrawnItems(UUID withdrawProtocol, ArrayList<ProcedureItem> items) {
        if (withdrawProtocol == null || items == null || items.isEmpty()) {
            return null;
        }

        return withdrawProtocol;
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
}

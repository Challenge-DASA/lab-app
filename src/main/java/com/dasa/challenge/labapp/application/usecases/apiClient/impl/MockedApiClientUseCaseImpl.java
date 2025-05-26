package com.dasa.challenge.labapp.application.usecases.apiClient.impl;

import com.dasa.challenge.labapp.application.dtos.ItemDTO;
import com.dasa.challenge.labapp.application.dtos.ProcedureDTO;
import com.dasa.challenge.labapp.application.usecases.apiClient.ApiClientUseCase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class MockedApiClientUseCaseImpl implements ApiClientUseCase {
    @Override
    public ArrayList<ProcedureDTO> getProcedures() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(new File(Objects.requireNonNull
                    (getClass().getResource("/data/procedure_mock.json")).toURI()));
            ArrayList<ProcedureDTO> proceduresData = new ArrayList<>();

            JsonNode procedures = json.get("procedures");
            for (JsonNode procedure : procedures) {
                final UUID procedureId = UUID.fromString(procedure.get("procedureId").asText());
                final String procedureName = procedure.get("procedureName").asText();
                final String procedureDescription = procedure.get("procedureDescription").asText();
                final ArrayList<ItemDTO> procedureItems = getProcedureItems(procedure);

                ProcedureDTO procedureData =
                        new ProcedureDTO(procedureId,
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
            System.out.println("User ID or password cannot be empty.");
            return null;
        }

        return UUID.randomUUID();
    }

    @Override
    public UUID sendWithdrawnItems(UUID withdrawProtocol, ArrayList<ItemDTO> items) {
        if (withdrawProtocol == null || items == null || items.isEmpty()) {
            System.out.println("Withdraw protocol or items cannot be null or empty.");
            return null;
        }

        System.out.println("Items withdrawn successfully with protocol: " + withdrawProtocol);
        for (ItemDTO item : items) {
            System.out.println("Item: " + item.itemName() + ", Quantity: " + item.itemQuantity() + ", was withdrawn.");
        }

        return withdrawProtocol;
    }

    private ArrayList<ItemDTO> getProcedureItems(JsonNode procedure) {
        ArrayList<ItemDTO> items = new ArrayList<>();

        procedure.get("procedureItems").elements()
                .forEachRemaining(item -> {
                    ItemDTO itemDTO = new ItemDTO(
                            UUID.fromString(item.get("itemId").asText()),
                            item.get("itemName").asText(),
                            item.get("itemQuantity").asInt()
                    );
                    items.add(itemDTO);
                });

        return items;
    }

}

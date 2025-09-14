package com.dasa.challenge.labapp.infrastructure.gateways.apiClient;

import com.dasa.challenge.labapp.application.dtos.MaterialsResponseDTO;
import com.dasa.challenge.labapp.application.dtos.ProcedureResponseDTO;
import com.dasa.challenge.labapp.application.dtos.WithdrawResponseDTO;
import com.dasa.challenge.labapp.application.gateways.apiClient.ApiClientGateway;
import com.dasa.challenge.labapp.domain.entities.Procedure;
import com.dasa.challenge.labapp.domain.entities.ProcedureItem;
import com.dasa.challenge.labapp.infrastructure.outbound.api.ApiClient;

import java.util.ArrayList;
import java.util.UUID;

public class BackendApiClientGatewayImpl implements ApiClientGateway {

    private final ApiClient apiClient;

    public BackendApiClientGatewayImpl(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public ArrayList<Procedure> getLabProcedures(UUID laboratoryId) {
        ProcedureResponseDTO procedureResponse = apiClient.getLabProcedures(laboratoryId);
        System.out.println("Got this procedures from api: " + procedureResponse);
        ArrayList<Procedure> procedures = mountProcedures(procedureResponse);
        System.out.println("Mounted procedures: " + procedures);
        return procedures;
    }

    @Override
    public UUID withdrawMaterialsForProcedure(UUID laboratoryId, UUID procedureId, UUID rfidToken) {
        WithdrawResponseDTO withdrawResponse = apiClient.sendWithdrawnItems(laboratoryId, procedureId, rfidToken);
        if (withdrawResponse.status().equalsIgnoreCase("AUTHORIZED")) {
            return withdrawResponse.transaction_id();
        }

        throw new RuntimeException("Error while trying to withdraw materials: "
                + withdrawResponse.status()
                + " - Id: " + withdrawResponse.transaction_id());
    }

    private ArrayList<Procedure> mountProcedures(ProcedureResponseDTO proceduresDataFromAPI) {
        System.out.println("Start mount procedures to UI");
        ArrayList<Procedure> proceduresData = new ArrayList<>();

        proceduresDataFromAPI.procedures().forEach(procedure -> {
            System.out.println("Mounting procedure: " + procedure);
            Procedure procedureData =
                    new Procedure(procedure.id().toString(),
                            procedure.name(),
                            procedure.description(),
                            mountProcedureItems(procedure.id()));

            System.out.println("Procedure mounted: " + procedureData);
            proceduresData.add(procedureData);
        });

        return proceduresData;
    }

    private ArrayList<ProcedureItem> mountProcedureItems(UUID procedureId) {
        System.out.println("Mounting items/materials for procedureId: " + procedureId);
        MaterialsResponseDTO procedureMaterialsItems = apiClient.getProcedureMaterials(procedureId);

        ArrayList<ProcedureItem> items = new ArrayList<>();

        procedureMaterialsItems.materials().forEach(material -> {
            System.out.println("Mounting material/item: " + material);
            ProcedureItem procedureItem = new ProcedureItem(
                    material.name(),
                    material.requiredAmount(),
                    material.id().toString()
            );

            System.out.println("Material/item mounted: " + procedureItem);
            items.add(procedureItem);
        });

        return items;
    }


}

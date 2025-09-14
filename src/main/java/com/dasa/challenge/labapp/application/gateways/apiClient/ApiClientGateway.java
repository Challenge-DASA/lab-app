package com.dasa.challenge.labapp.application.gateways.apiClient;

import com.dasa.challenge.labapp.domain.entities.Procedure;

import java.util.ArrayList;
import java.util.UUID;

public interface ApiClientGateway {
    ArrayList<Procedure> getLabProcedures(UUID laboratoryId);

    UUID withdrawMaterialsForProcedure(UUID laboratoryId, UUID procedureId, UUID rfidToken);
}

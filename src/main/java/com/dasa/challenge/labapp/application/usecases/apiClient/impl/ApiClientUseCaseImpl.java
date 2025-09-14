package com.dasa.challenge.labapp.application.usecases.apiClient.impl;

import com.dasa.challenge.labapp.application.gateways.apiClient.ApiClientGateway;
import com.dasa.challenge.labapp.application.usecases.apiClient.ApiClientUseCase;
import com.dasa.challenge.labapp.domain.entities.Procedure;

import java.util.ArrayList;
import java.util.UUID;

public class ApiClientUseCaseImpl implements ApiClientUseCase {

    private final ApiClientGateway apiClientGateway;

    public ApiClientUseCaseImpl(ApiClientGateway apiClientGateway) {
        this.apiClientGateway = apiClientGateway;
    }

    @Override
    public ArrayList<Procedure> getProcedures(UUID laboratoryId) {
        return apiClientGateway.getLabProcedures(laboratoryId);
    }

    @Override
    public UUID sendWithdrawnItems(UUID laboratoryId, UUID procedureId, UUID rfidToken) {
        return apiClientGateway.withdrawMaterialsForProcedure(laboratoryId, procedureId, rfidToken);
    }
}

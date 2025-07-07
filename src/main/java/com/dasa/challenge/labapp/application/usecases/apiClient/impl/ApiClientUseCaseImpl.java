package com.dasa.challenge.labapp.application.usecases.apiClient.impl;

import com.dasa.challenge.labapp.application.dtos.ItemDTO;
import com.dasa.challenge.labapp.application.dtos.ProcedureDTO;
import com.dasa.challenge.labapp.application.gateways.apiClient.ApiClientGateway;
import com.dasa.challenge.labapp.application.usecases.apiClient.ApiClientUseCase;

import java.util.ArrayList;
import java.util.UUID;

public class ApiClientUseCaseImpl implements ApiClientUseCase {

    private final ApiClientGateway apiClientGateway;

    public ApiClientUseCaseImpl(ApiClientGateway apiClientGateway) {
        this.apiClientGateway = apiClientGateway;
    }

    @Override
    public ArrayList<ProcedureDTO> getProcedures() {
        return this.apiClientGateway.getProcedures();
    }

    @Override
    public UUID authenticateUser(String userId, String userPassword) {
        return this.apiClientGateway.authenticateUser(userId, userPassword);
    }

    @Override
    public UUID sendWithdrawnItems(UUID withdrawProtocol, ArrayList<ItemDTO> items) {
        return this.apiClientGateway.sendWithdrawnItems(withdrawProtocol, items);
    }

}

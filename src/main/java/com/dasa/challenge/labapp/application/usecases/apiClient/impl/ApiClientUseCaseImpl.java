package com.dasa.challenge.labapp.application.usecases.apiClient.impl;

import com.dasa.challenge.labapp.application.gateways.apiClient.ApiClientGateway;
import com.dasa.challenge.labapp.application.usecases.apiClient.ApiClientUseCase;
import com.dasa.challenge.labapp.domain.entities.Procedure;
import com.dasa.challenge.labapp.domain.entities.ProcedureItem;

import java.util.ArrayList;
import java.util.UUID;

public class ApiClientUseCaseImpl implements ApiClientUseCase {

    private final ApiClientGateway apiClientGateway;

    public ApiClientUseCaseImpl(ApiClientGateway apiClientGateway) {
        this.apiClientGateway = apiClientGateway;
    }

    @Override
    public ArrayList<Procedure> getProcedures() {
        return this.apiClientGateway.getProcedures();
    }

    @Override
    public UUID authenticateUser(String userId, String userPassword) {
        return this.apiClientGateway.authenticateUser(userId, userPassword);
    }

    @Override
    public UUID sendWithdrawnItems(UUID withdrawProtocol, ArrayList<ProcedureItem> items) {
        return this.apiClientGateway.sendWithdrawnItems(withdrawProtocol, items);
    }

}

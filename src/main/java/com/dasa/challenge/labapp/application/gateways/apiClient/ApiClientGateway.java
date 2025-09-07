package com.dasa.challenge.labapp.application.gateways.apiClient;

import com.dasa.challenge.labapp.application.dtos.ItemDTO;
import com.dasa.challenge.labapp.domain.entities.Procedure;
import com.dasa.challenge.labapp.domain.entities.ProcedureItem;

import java.util.ArrayList;
import java.util.UUID;

public interface ApiClientGateway {
    ArrayList<Procedure> getProcedures();

    UUID authenticateUser(String userId, String userPassword);

    UUID sendWithdrawnItems(UUID withdrawProtocol, ArrayList<ProcedureItem> items);
}

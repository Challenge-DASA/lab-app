package com.dasa.challenge.labapp.application.usecases.apiClient;

import com.dasa.challenge.labapp.application.dtos.ItemDTO;
import com.dasa.challenge.labapp.application.dtos.ProcedureDTO;

import java.util.ArrayList;
import java.util.UUID;

public interface ApiClientUseCase {
    ArrayList<ProcedureDTO> getProcedures();

    UUID authenticateUser(String userId, String userPassword);

    UUID sendWithdrawnItems(UUID withdrawProtocol, ArrayList<ItemDTO> items);
}

package com.dasa.challenge.labapp.application.usecases.apiClient;

import com.dasa.challenge.labapp.domain.entities.Procedure;
import com.dasa.challenge.labapp.domain.entities.ProcedureItem;

import java.util.ArrayList;
import java.util.UUID;

public interface ApiClientUseCase {
    ArrayList<Procedure> getProcedures(UUID laboratoryId);

    UUID sendWithdrawnItems(UUID laboratoryId, UUID procedureId, UUID rfidToken);
}

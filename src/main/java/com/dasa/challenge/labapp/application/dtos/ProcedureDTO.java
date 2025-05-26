package com.dasa.challenge.labapp.application.dtos;

import java.util.ArrayList;
import java.util.UUID;

public record ProcedureDTO(UUID procedureId,
                           String procedureName,
                           String procedureDescription,
                           ArrayList<ItemDTO> procedureItems) {
}

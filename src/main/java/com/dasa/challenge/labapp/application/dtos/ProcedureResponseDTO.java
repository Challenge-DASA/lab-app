package com.dasa.challenge.labapp.application.dtos;

import java.util.List;

public record ProcedureResponseDTO(List<ProcedureDTO> procedures, Integer totalCount) {
}

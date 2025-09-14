package com.dasa.challenge.labapp.application.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProcedureDTO(
        LocalDateTime createdAt,
        String description,
        UUID id,
        String name,
        LocalDateTime updatedAt
) {}
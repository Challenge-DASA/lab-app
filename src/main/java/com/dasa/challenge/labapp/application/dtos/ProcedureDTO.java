package com.dasa.challenge.labapp.application.dtos;

import com.dasa.challenge.labapp.infrastructure.utils.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProcedureDTO(
        @JsonDeserialize(using = CustomDateDeserializer.class)
        LocalDateTime createdAt,
        String description,
        UUID id,
        String name,
        @JsonDeserialize(using = CustomDateDeserializer.class)
        LocalDateTime updatedAt
) {
}
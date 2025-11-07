package com.dasa.challenge.labapp.application.dtos;

import com.dasa.challenge.labapp.infrastructure.utils.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public record WithdrawResponseDTO(
        @JsonDeserialize(using = CustomDateDeserializer.class)
        LocalDateTime authorized_at,
        @JsonDeserialize(using = CustomDateDeserializer.class)
        LocalDateTime created_at,
        ArrayList<WithdrawItemDTO> items,
        UUID laboratory_id,
        UUID procedure_id,
        String status,
        UUID transaction_id,
        String transaction_type) {
}

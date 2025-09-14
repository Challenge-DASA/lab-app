package com.dasa.challenge.labapp.application.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public record WithdrawResponseDTO(LocalDateTime authorized_at,
                                  LocalDateTime created_at,
                                  ArrayList<WithdrawItemDTO> items,
                                  UUID laboratory_id,
                                  UUID procedure_id,
                                  String status,
                                  UUID transaction_id,
                                  String transaction_type) {
}

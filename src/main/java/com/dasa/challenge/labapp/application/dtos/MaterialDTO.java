package com.dasa.challenge.labapp.application.dtos;

import java.util.UUID;

public record MaterialDTO(String description, UUID id, String name, Integer requiredAmount) {
}

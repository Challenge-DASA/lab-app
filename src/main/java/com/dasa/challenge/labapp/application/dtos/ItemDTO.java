package com.dasa.challenge.labapp.application.dtos;

import java.util.UUID;

public record ItemDTO (UUID itemId, String itemName, Integer itemQuantity) {}

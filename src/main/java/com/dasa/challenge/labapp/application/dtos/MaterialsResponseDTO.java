package com.dasa.challenge.labapp.application.dtos;

import java.util.List;

public record MaterialsResponseDTO(List<MaterialDTO> materials, Integer totalMaterials) {
}

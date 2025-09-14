package com.dasa.challenge.labapp;

import com.dasa.challenge.labapp.application.dtos.ProcedureResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LabApplicationTest {

    @Test
    void testDeserialize() throws Exception {
        String json = """
    {
      "procedures": [
        {
          "created_at": "2025-09-07T00:33:13",
          "description": "Procedimento para coleta",
          "id": "40de967c-843d-4023-b6a7-164dc54953f3",
          "name": "Biopsia",
          "updated_at": "2025-09-07T00:33:13"
        }
      ],
      "total_count": 1
    }
    """;

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ProcedureResponseDTO dto = mapper.readValue(json, ProcedureResponseDTO.class);
        System.out.println(dto);
    }

}
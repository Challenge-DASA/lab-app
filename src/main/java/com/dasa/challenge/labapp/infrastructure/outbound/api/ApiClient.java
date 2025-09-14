package com.dasa.challenge.labapp.infrastructure.outbound.api;

import com.dasa.challenge.labapp.application.dtos.MaterialsResponseDTO;
import com.dasa.challenge.labapp.application.dtos.ProcedureResponseDTO;
import com.dasa.challenge.labapp.application.dtos.WithdrawResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class ApiClient {

    private final HttpClient httpClient;
    private final String baseUrl = "http://localhost:5000"; // make this get from the application.yml later
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ApiClient() {
        this.httpClient = HttpClient.newHttpClient();
        System.out.println("[API] ApiClient initialized with baseUrl: " + baseUrl);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    }

    public ProcedureResponseDTO getLabProcedures(UUID laboratoryId) {
        try {
            System.out.println("[API] Starting get Lab Procedures...");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/laboratory/" + laboratoryId + "/procedures"))
                    .GET()
                    .build();

            HttpResponse<String> response = sendRequest(request);

            if (response.statusCode() != 200) {
                throw new RuntimeException("[API] Status returned when tried to get Lab Procedures: "
                        + response.statusCode() + " - Message: " + response.body());
            }

            ProcedureResponseDTO procedureResponseDTO = objectMapper.readValue(response.body(), ProcedureResponseDTO.class);
            return procedureResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException("[API] Error while trying to get Lab Procedures: ", e);
        }
    }

    public MaterialsResponseDTO getProcedureMaterials(UUID procedureId) {
        try {
            System.out.println("[API] Starting get Procedure Materials...");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/procedures/" + procedureId + "/materials"))
                    .GET()
                    .build();

            HttpResponse<String> response = sendRequest(request);

            if (response.statusCode() != 200) {
                throw new RuntimeException("Status returned when tried to get Procedure Materials: "
                        + response.statusCode() + " - Message: " + response.body());
            }
            MaterialsResponseDTO MaterialsResponseDTO =
                    objectMapper.readValue(response.body(), MaterialsResponseDTO.class);
            return MaterialsResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error while trying to get Lab Procedures: ", e);
        }
    }


    public WithdrawResponseDTO sendWithdrawnItems(UUID laboratoryId, UUID procedureId, UUID rfidToken) {
        try {
            System.out.println("[API] Starting send Withdrawn Items... With rfidToken: " + rfidToken);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/laboratory/" + laboratoryId + "/withdraw/" + procedureId))
                    .header("Authorization", rfidToken.toString())
                    .POST(null)
                    .build();

            HttpResponse<String> response = sendRequest(request);

            System.out.println("[API] Withdraw response status code: " + response.statusCode() +
                    " Response body: " + response.body());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Status returned when tried to Withdraw Procedure Items: "
                        + response.statusCode() + " - Message: " + response.body());
            }

            return objectMapper.readValue(response.body(), WithdrawResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error while trying to Withdraw Procedure Items: ", e);
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) {
        try {
            System.out.println("[API] Making request to route: "
                    + request.uri() + " using rfid token for auth: "
                    + request.headers().firstValue("Authorization"));

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("[API] Received response with status code: "
                    + response.statusCode()
                    + " Response body: " + response.body());

            return response;
        } catch (Exception e) {
            throw new RuntimeException("[API] Error while sending HTTP request: ", e);
        }
    }
}

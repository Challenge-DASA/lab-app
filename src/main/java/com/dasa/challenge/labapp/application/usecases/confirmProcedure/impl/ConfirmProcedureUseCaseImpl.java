package com.dasa.challenge.labapp.application.usecases.confirmProcedure.impl;

import com.dasa.challenge.labapp.application.dtos.ProcedureDTO;
import com.dasa.challenge.labapp.application.usecases.apiClient.ApiClientUseCase;
import com.dasa.challenge.labapp.application.usecases.confirmProcedure.ConfirmProcedureUseCase;
import com.dasa.challenge.labapp.domain.entities.Procedure;
import com.dasa.challenge.labapp.domain.entities.ProcedureItem;
import com.dasa.challenge.labapp.infrastructure.controllers.ConfirmProcedureController;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConfirmProcedureUseCaseImpl implements ConfirmProcedureUseCase {

    private final ApiClientUseCase apiClientUseCase;
    private final List<Procedure> selectedProcedures = new ArrayList<>();
    private final Stage stage;

    public ConfirmProcedureUseCaseImpl(Stage stage, ApiClientUseCase apiClientUseCase) {
        this.stage = stage;
        this.apiClientUseCase = apiClientUseCase;
    }

    @Override
    public List<Procedure> loadProcedures() {
        List<ProcedureDTO> procedureDTOs = apiClientUseCase.getProcedures();

        return procedureDTOs.stream()
                .map(this::mapDTOToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void start() {
        ConfirmProcedureController controller = new ConfirmProcedureController(this);
        controller.initializeView(stage);
    }

    @Override
    public void nextPage() {
        System.out.println("Navigating to next page...");
    }

    public void handleConfirmProcedures() {
        System.out.println("Confirming selected procedures...");
        nextPage();
    }

    private Procedure mapDTOToEntity(ProcedureDTO dto) {
        List<ProcedureItem> items = dto.procedureItems().stream()
                .map(itemDTO -> new ProcedureItem(itemDTO.itemName(), itemDTO.itemQuantity(), itemDTO.itemId().toString()))
                .collect(Collectors.toList());

        return new Procedure(
                dto.procedureId().toString(),
                dto.procedureName(),
                dto.procedureDescription(),
                items
        );
    }

    @Override
    public void selectProcedure(Procedure procedure) {
        if (!selectedProcedures.contains(procedure)) {
            procedure.select();
            selectedProcedures.add(procedure);
            System.out.println("Selected procedure: " + procedure.getName());
        }
    }

    @Override
    public void deselectProcedure(Procedure procedure) {
        if (selectedProcedures.contains(procedure)) {
            procedure.deselect();
            selectedProcedures.remove(procedure);
            System.out.println("Deselected procedure: " + procedure.getName());
        }
    }

    @Override
    public List<Procedure> getSelectedProcedures() {
        return new ArrayList<>(selectedProcedures);
    }

    @Override
    public void confirmSelectedProcedures() {
        if (selectedProcedures.isEmpty()) {
            throw new IllegalStateException("No procedures selected");
        }

        System.out.println("Confirming " + selectedProcedures.size() + " procedures:");
        selectedProcedures.forEach(p -> System.out.println("- " + p.getName()));

    }

    @Override
    public List<Procedure> filterProcedures(List<Procedure> procedures, String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return procedures;
        }

        String lowerSearchText = searchText.toLowerCase();
        return procedures.stream()
                .filter(procedure ->
                        procedure.getName().toLowerCase().contains(lowerSearchText) ||
                                procedure.getDescription().toLowerCase().contains(lowerSearchText))
                .collect(Collectors.toList());
    }
}
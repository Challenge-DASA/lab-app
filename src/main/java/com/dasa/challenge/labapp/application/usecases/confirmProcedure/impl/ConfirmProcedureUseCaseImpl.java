package com.dasa.challenge.labapp.application.usecases.confirmProcedure.impl;

import com.dasa.challenge.labapp.application.gateways.confirmProcedure.ConfirmProcedureGateway;
import com.dasa.challenge.labapp.application.usecases.confirmProcedure.ConfirmProcedureUseCase;
import com.dasa.challenge.labapp.domain.entities.Procedure;

import java.util.List;

public class ConfirmProcedureUseCaseImpl implements ConfirmProcedureUseCase {

    private final ConfirmProcedureGateway confirmProcedureGateway;

    public ConfirmProcedureUseCaseImpl(ConfirmProcedureGateway confirmProcedureGateway) {
        this.confirmProcedureGateway = confirmProcedureGateway;
    }

    @Override
    public List<Procedure> loadProcedures() {
        return this.confirmProcedureGateway.loadProcedures();
    }

    @Override
    public void start() {
        this.confirmProcedureGateway.start();
    }

    @Override
    public void nextPage() {
        this.confirmProcedureGateway.nextPage();
    }

    @Override
    public void selectProcedure(Procedure procedure) {
        this.confirmProcedureGateway.selectProcedure(procedure);
    }

    @Override
    public void deselectProcedure(Procedure procedure) {
        this.confirmProcedureGateway.deselectProcedure(procedure);
    }

    @Override
    public void confirmSelectedProcedures() {
        this.confirmProcedureGateway.confirmSelectedProcedures();
    }

    @Override
    public List<Procedure> filterProcedures(List<Procedure> procedures, String searchText) {
        return this.confirmProcedureGateway.filterProcedures(procedures, searchText);
    }
}
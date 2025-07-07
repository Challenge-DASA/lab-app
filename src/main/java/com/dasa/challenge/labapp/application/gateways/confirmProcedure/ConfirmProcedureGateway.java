package com.dasa.challenge.labapp.application.gateways.confirmProcedure;

import com.dasa.challenge.labapp.domain.entities.Procedure;
import javafx.scene.Parent;

import java.util.List;

public interface ConfirmProcedureGateway {
    Parent start();

    void nextPage();

    List<Procedure> loadProcedures();

    void selectProcedure(Procedure procedure);

    void deselectProcedure(Procedure procedure);

    List<Procedure> getSelectedProcedures();

    void confirmSelectedProcedures();

    List<Procedure> filterProcedures(List<Procedure> procedures, String searchText);

}

package com.dasa.challenge.labapp.application.usecases.confirmProcedure;

import com.dasa.challenge.labapp.application.usecases.page.PageUseCase;
import com.dasa.challenge.labapp.domain.entities.Procedure;

import java.util.List;

public interface ConfirmProcedureUseCase extends PageUseCase {
    List<Procedure> loadProcedures();

    void selectProcedure(Procedure procedure);

    void deselectProcedure(Procedure procedure);

    List<Procedure> getSelectedProcedures();

    void confirmSelectedProcedures();

    List<Procedure> filterProcedures(List<Procedure> procedures, String searchText);

}
package com.dasa.challenge.labapp.application.views.confirmProcedure;

import com.dasa.challenge.labapp.application.views.PageView;
import com.dasa.challenge.labapp.domain.entities.Procedure;
import javafx.scene.Parent;

import java.util.List;

public interface ConfirmProcedureView extends PageView {
    List<Procedure> loadProcedures();

    void selectProcedure(Procedure procedure);

    void deselectProcedure(Procedure procedure);

    void confirmSelectedProcedures();

    List<Procedure> filterProcedures(List<Procedure> procedures, String searchText);
}

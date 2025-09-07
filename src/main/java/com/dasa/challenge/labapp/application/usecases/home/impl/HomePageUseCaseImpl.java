package com.dasa.challenge.labapp.application.usecases.home.impl;

import com.dasa.challenge.labapp.application.usecases.confirmProcedure.ConfirmProcedureUseCase;
import com.dasa.challenge.labapp.application.usecases.home.HomePageUseCase;
import com.dasa.challenge.labapp.infrastructure.controllers.HomeController;
import com.dasa.challenge.labapp.utils.SliderSwitch;
import com.dasa.challenge.labapp.utils.ViewManager;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class HomePageUseCaseImpl implements HomePageUseCase {
    private final Stage stage;
    private final ConfirmProcedureUseCase confirmProcedureUseCase;
    private HomeController controller;

    public HomePageUseCaseImpl(Stage stage, ConfirmProcedureUseCase confirmProcedureUseCase) {
        this.stage = stage;
        this.confirmProcedureUseCase = confirmProcedureUseCase;
    }

    @Override
    public void start() {
        this.controller = new HomeController(this);
        Parent view = controller.getView();
        ViewManager.setView(view);
    }

    @Override
    public void nextPage() {
        SliderSwitch.slideToUseCase(stage,
                confirmProcedureUseCase::start,
                "/com/dasa/challenge/labapp/styles/confirm-procedure/confirm-procedure.css");
    }

    public void handleStartWithdraw() {
        System.out.println("Starting withdrawal process...");

        nextPage();
    }

    protected Stage getStage() {
        return stage;
    }
}
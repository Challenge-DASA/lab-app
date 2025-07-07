package com.dasa.challenge.labapp.application.usecases.home.impl;

import com.dasa.challenge.labapp.application.gateways.home.HomeGateway;
import com.dasa.challenge.labapp.application.usecases.home.HomePageUseCase;
import javafx.stage.Stage;

public class HomePageUseCaseImpl implements HomePageUseCase {
    private final Stage stage;
    private final HomeGateway homeGateway;

    public HomePageUseCaseImpl(HomeGateway homeGateway, Stage stage) {
        this.homeGateway = homeGateway;
        this.stage = stage;
    }

    @Override
    public void start() {
        this.homeGateway.start();
    }

    @Override
    public void nextPage() {
        this.homeGateway.nextPage();
    }

    protected Stage getStage() {
        return stage;
    }
}
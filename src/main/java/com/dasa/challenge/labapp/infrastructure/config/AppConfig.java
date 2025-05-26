package com.dasa.challenge.labapp.infrastructure.config;

import com.dasa.challenge.labapp.application.usecases.apiClient.ApiClientUseCase;
import com.dasa.challenge.labapp.application.usecases.apiClient.impl.MockedApiClientUseCaseImpl;
import com.dasa.challenge.labapp.application.usecases.confirmProcedure.ConfirmProcedureUseCase;
import com.dasa.challenge.labapp.application.usecases.confirmProcedure.impl.ConfirmProcedureUseCaseImpl;
import com.dasa.challenge.labapp.application.usecases.home.HomePageUseCase;
import com.dasa.challenge.labapp.application.usecases.home.impl.HomePageUseCaseImpl;
import javafx.stage.Stage;

public class AppConfig {

    public static ApiClientUseCase apiClientUseCaseImpl() {
        return new MockedApiClientUseCaseImpl();
    }

    public static ConfirmProcedureUseCase confirmProcedurePageUseCaseImpl(Stage stage) {
        return new ConfirmProcedureUseCaseImpl(stage, apiClientUseCaseImpl());
    }

    public static HomePageUseCase homePageUseCaseImpl(Stage stage) {
        return new HomePageUseCaseImpl(stage, AppConfig.confirmProcedurePageUseCaseImpl(stage));
    }

}
package com.dasa.challenge.labapp.infrastructure.config;

import com.dasa.challenge.labapp.application.gateways.apiClient.ApiClientGateway;
import com.dasa.challenge.labapp.application.gateways.confirmProcedure.ConfirmProcedureGateway;
import com.dasa.challenge.labapp.application.gateways.home.HomeGateway;
import com.dasa.challenge.labapp.application.usecases.apiClient.ApiClientUseCase;
import com.dasa.challenge.labapp.application.usecases.apiClient.impl.ApiClientUseCaseImpl;
import com.dasa.challenge.labapp.application.usecases.confirmProcedure.ConfirmProcedureUseCase;
import com.dasa.challenge.labapp.application.usecases.confirmProcedure.impl.ConfirmProcedureUseCaseImpl;
import com.dasa.challenge.labapp.application.usecases.home.HomePageUseCase;
import com.dasa.challenge.labapp.application.usecases.home.impl.HomePageUseCaseImpl;
import com.dasa.challenge.labapp.infrastructure.gateways.apiClient.MockedApiClientGatewayImpl;
import com.dasa.challenge.labapp.infrastructure.gateways.confirmProcedure.ConfirmProcedureGatewayImpl;
import com.dasa.challenge.labapp.infrastructure.gateways.home.HomeGatewayImpl;
import javafx.stage.Stage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfig {

    @Bean
    @Scope("singleton")
    public Stage stage() {
        return new Stage();
    }

    @Bean
    public ApiClientGateway apiClientGateway() {
        return new MockedApiClientGatewayImpl();
    }

    @Bean
    public ApiClientUseCase apiClientUseCase(ApiClientGateway apiClientGateway) {
        return new ApiClientUseCaseImpl(apiClientGateway);
    }

    @Bean
    public ConfirmProcedureUseCase confirmProcedureUseCase(ConfirmProcedureGateway confirmProcedureGateway) {
        return new ConfirmProcedureUseCaseImpl(confirmProcedureGateway);
    }

    @Bean
    public ConfirmProcedureGateway confirmProcedureGateway(Stage stage, ApiClientGateway apiClientGateway) {
        return new ConfirmProcedureGatewayImpl(stage, apiClientGateway);
    }

    @Bean
    public HomeGateway homeGateway(Stage stage, ConfirmProcedureGateway confirmProcedureGateway) {
        return new HomeGatewayImpl(stage, confirmProcedureGateway);
    }

    @Bean
    public HomePageUseCase homePageUseCase(HomeGateway homeGateway,
                                           Stage stage) {
        return new HomePageUseCaseImpl(homeGateway, stage);
    }
}

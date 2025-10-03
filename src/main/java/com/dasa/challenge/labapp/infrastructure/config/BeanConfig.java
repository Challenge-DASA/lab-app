package com.dasa.challenge.labapp.infrastructure.config;

import com.dasa.challenge.labapp.application.gateways.apiClient.ApiClientGateway;
import com.dasa.challenge.labapp.application.gateways.auth.AuthGateway;
import com.dasa.challenge.labapp.application.gateways.cart.CartGateway;
import com.dasa.challenge.labapp.application.usecases.apiClient.ApiClientUseCase;
import com.dasa.challenge.labapp.application.usecases.apiClient.impl.ApiClientUseCaseImpl;
import com.dasa.challenge.labapp.application.usecases.auth.AuthUseCase;
import com.dasa.challenge.labapp.application.usecases.auth.impl.RfidAuthUseCaseImpl;
import com.dasa.challenge.labapp.application.usecases.cart.CartUseCase;
import com.dasa.challenge.labapp.application.usecases.cart.impl.CartUseCaseImpl;
import com.dasa.challenge.labapp.application.views.auth.RfidAuthView;
import com.dasa.challenge.labapp.application.views.conclusion.ConclusionView;
import com.dasa.challenge.labapp.application.views.confirmProcedure.ConfirmProcedureView;
import com.dasa.challenge.labapp.application.views.home.HomeView;
import com.dasa.challenge.labapp.infrastructure.gateways.apiClient.BackendApiClientGatewayImpl;
import com.dasa.challenge.labapp.infrastructure.gateways.apiClient.MockedApiClientGatewayImpl;
import com.dasa.challenge.labapp.infrastructure.gateways.auth.RfidAuthGatewayImpl;
import com.dasa.challenge.labapp.infrastructure.gateways.cart.LocalCartGatewayImpl;
import com.dasa.challenge.labapp.infrastructure.outbound.api.ApiClient;
import com.dasa.challenge.labapp.infrastructure.views.conclusion.ConclusionViewImpl;
import com.dasa.challenge.labapp.infrastructure.views.confirmProcedure.ConfirmProcedureViewImpl;
import com.dasa.challenge.labapp.infrastructure.views.home.HomeViewImpl;
import com.dasa.challenge.labapp.infrastructure.views.rfidAuth.RfidAuthViewImpl;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.UUID;

@Configuration
@PropertySource("classpath:application.properties")
public class BeanConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Scope("singleton")
    public Stage stage() {
        Stage stage = new Stage();
        stage.setFullScreen(true);
        return stage;
    }

    @Bean
    public ApiClient apiClient(@Value("${api.apiUrl}") String baseUrl) {
        return new ApiClient(baseUrl);
    }

    @Bean
    public ApiClientGateway apiClientGateway(ApiClient apiClient, @Value("${api.useMockedApi}") String useMockedApi) {
        if (Boolean.parseBoolean(useMockedApi)) {
            System.out.println("Using Mocked API Client");
            return new MockedApiClientGatewayImpl(); // MockedApi
        } else {
            System.out.println("Using Real API Client");
            return new BackendApiClientGatewayImpl(apiClient);
        }
    }

    @Bean
    public ApiClientUseCase apiClientUseCase(ApiClientGateway apiClientGateway) {
        return new ApiClientUseCaseImpl(apiClientGateway);
    }

    @Bean
    public AuthGateway authGateway() {
        return new RfidAuthGatewayImpl();
    }

    @Bean
    public AuthUseCase authUseCase(AuthGateway authGateway) {
        return new RfidAuthUseCaseImpl(authGateway);
    }

    @Bean
    public CartUseCase cartUseCase(CartGateway cartGateway) {
        return new CartUseCaseImpl(cartGateway);
    }

    @Bean
    public CartGateway cartGateway() {
        return new LocalCartGatewayImpl();
    }

    @Bean
    public RfidAuthView rfidAuthView(Stage stage,
                                     AuthUseCase authUseCase,
                                     ApiClientUseCase apiClientUseCase,
                                     CartUseCase cartUseCase,
                                     @Value("${api.mockedLaboratoryId}") String laboratoryId) {
        return new RfidAuthViewImpl(stage, authUseCase, apiClientUseCase, cartUseCase, UUID.fromString(laboratoryId));
    }

    @Bean
    public ConfirmProcedureView confirmProcedureView(Stage stage,
                                                     ApiClientUseCase apiClientUseCase,
                                                     CartUseCase cartUseCase,
                                                     RfidAuthView rfidAuthView,
                                                     @Value("${api.mockedLaboratoryId}") String laboratoryId) {
        return new ConfirmProcedureViewImpl(stage, apiClientUseCase, cartUseCase, rfidAuthView, UUID.fromString(laboratoryId));
    }

    @Bean
    public HomeView homeView(Stage stage, ConfirmProcedureView confirmProcedureView) {
        return new HomeViewImpl(stage, confirmProcedureView);
    }

    @Bean
    public ConclusionView conclusionView(Stage stage) {
        // A dependência HomeView será injetada manualmente.
        return new ConclusionViewImpl(stage);
    }

    @Bean
    public Object resolveCircularDependencies(
            RfidAuthView rfidAuthView,
            ConclusionView conclusionView,
            HomeView homeView,
            ConfirmProcedureView confirmProcedureView) {

        ((RfidAuthViewImpl) rfidAuthView).setConclusionView(conclusionView);
        ((ConclusionViewImpl) conclusionView).setHomeView(homeView);
        ((ConfirmProcedureViewImpl) confirmProcedureView).setHomeView(homeView);
        return null;
    }
}
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
import com.dasa.challenge.labapp.infrastructure.gateways.auth.RfidAuthGatewayImpl;
import com.dasa.challenge.labapp.infrastructure.gateways.cart.LocalCartGatewayImpl;
import com.dasa.challenge.labapp.infrastructure.outbound.api.ApiClient;
import com.dasa.challenge.labapp.infrastructure.views.conclusion.ConclusionViewImpl;
import com.dasa.challenge.labapp.infrastructure.views.confirmProcedure.ConfirmProcedureViewImpl;
import com.dasa.challenge.labapp.infrastructure.views.home.HomeViewImpl;
import com.dasa.challenge.labapp.infrastructure.views.rfidAuth.RfidAuthViewImpl;
import javafx.stage.Stage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.UUID;

@Configuration
public class BeanConfig {

    @Bean
    @Scope("singleton")
    public Stage stage() {
        Stage stage = new Stage();
        stage.setFullScreen(true);
        return stage;
    }

    @Bean
    public ApiClient apiClient() {
        return new ApiClient();
    }

    @Bean
    public ApiClientGateway apiClientGateway(ApiClient apiClient) {
        // new MockedApiClientGatewayImpl(); // MockedApi
        return new BackendApiClientGatewayImpl(apiClient);
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
    public RfidAuthView rfidAuthView(Stage stage, AuthUseCase authUseCase, ApiClientUseCase apiClientUseCase, CartUseCase cartUseCase) {
        return new RfidAuthViewImpl(stage, authUseCase, apiClientUseCase, cartUseCase);
    }

    @Bean
    public ConfirmProcedureView confirmProcedureView(Stage stage,
                                                     ApiClientUseCase apiClientUseCase,
                                                     CartUseCase cartUseCase,
                                                     RfidAuthView rfidAuthView) {
        return new ConfirmProcedureViewImpl(stage, apiClientUseCase, cartUseCase, rfidAuthView);
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
            HomeView homeView) {
        // Este método garante que todos os beans acima sejam criados primeiro.
        // E então, resolve as dependências circulares via setters.
        ((RfidAuthViewImpl) rfidAuthView).setConclusionView(conclusionView);
        ((ConclusionViewImpl) conclusionView).setHomeView(homeView);
        return null;
    }
}
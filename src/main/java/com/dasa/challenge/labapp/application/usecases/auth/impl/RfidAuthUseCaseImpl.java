package com.dasa.challenge.labapp.application.usecases.auth.impl;

import com.dasa.challenge.labapp.application.gateways.auth.AuthGateway;
import com.dasa.challenge.labapp.application.usecases.auth.AuthUseCase;

import java.util.UUID;
import java.util.function.Consumer;

public class RfidAuthUseCaseImpl implements AuthUseCase {
    private final AuthGateway authGateway;

    public RfidAuthUseCaseImpl(AuthGateway authGateway) {
        this.authGateway = authGateway;
    }

    @Override
    public void validateAuth() {
        this.authGateway.validateAuth();
    }

    @Override
    public void authHandler(Consumer<UUID> handler) {
        this.authGateway.authHandler(handler);
    }
}

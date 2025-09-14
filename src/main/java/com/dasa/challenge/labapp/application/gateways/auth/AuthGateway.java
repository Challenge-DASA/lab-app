package com.dasa.challenge.labapp.application.gateways.auth;

import java.util.UUID;
import java.util.function.Consumer;

public interface AuthGateway {
    void validateAuth();

    void authHandler(Consumer<UUID> handler);
}

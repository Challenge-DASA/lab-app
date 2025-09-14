package com.dasa.challenge.labapp.application.usecases.auth;

import java.util.UUID;
import java.util.function.Consumer;

public interface AuthUseCase {
    void validateAuth();

    void authHandler(Consumer<UUID> handler);
}

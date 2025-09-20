package com.dasa.challenge.labapp.infrastructure.views;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseJavaFxTest {
    private static volatile boolean jfxIsSetup;

    @BeforeAll
    public static void initJfx() {
        if (!jfxIsSetup) {
            try {
                Platform.startup(() -> {});
            } catch (IllegalStateException e) {
                // Platform already started
            }
            jfxIsSetup = true;
        }
    }
}
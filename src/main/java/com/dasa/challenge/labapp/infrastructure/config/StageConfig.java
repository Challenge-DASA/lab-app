package com.dasa.challenge.labapp.infrastructure.config;

import javafx.stage.Stage;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StageConfig {
    private final Stage stage;

    public StageConfig(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }
}

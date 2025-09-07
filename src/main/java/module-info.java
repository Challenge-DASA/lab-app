module com.dasa.challenge.labapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires spring.context;
    requires spring.core;
    requires spring.beans;

    opens com.dasa.challenge.labapp.infrastructure.gateways.home to javafx.fxml, spring.core;
    opens com.dasa.challenge.labapp.infrastructure.gateways.confirmProcedure to javafx.fxml, spring.core;
    opens com.dasa.challenge.labapp.infrastructure.components to javafx.fxml;

    opens com.dasa.challenge.labapp to spring.core, spring.beans, spring.context;
    opens com.dasa.challenge.labapp.infrastructure.config to spring.core, spring.beans, spring.context;
    opens com.dasa.challenge.labapp.application.usecases.home.impl to spring.core, spring.beans, spring.context;
    opens com.dasa.challenge.labapp.application.usecases.confirmProcedure.impl to spring.core, spring.beans, spring.context;
    opens com.dasa.challenge.labapp.application.usecases.apiClient.impl to spring.core, spring.beans, spring.context;
    opens com.dasa.challenge.labapp.infrastructure.gateways.apiClient to spring.core, spring.beans, spring.context;

    exports com.dasa.challenge.labapp;
}
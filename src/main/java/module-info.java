module com.dasa.challenge.labapp {
    requires javafx.fxml;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;
    requires java.smartcardio;
    requires java.net.http;
    requires javafx.controls;


    opens com.dasa.challenge.labapp.infrastructure.views.home to javafx.fxml, spring.core;
    opens com.dasa.challenge.labapp.infrastructure.views.confirmProcedure to javafx.fxml, spring.core;
    opens com.dasa.challenge.labapp.infrastructure.views.rfidAuth to javafx.fxml, spring.core;
    opens com.dasa.challenge.labapp.infrastructure.views.conclusion to javafx.fxml, spring.core;
    opens com.dasa.challenge.labapp.infrastructure.components to javafx.fxml;
    opens com.dasa.challenge.labapp to spring.core, spring.beans, spring.context;
    opens com.dasa.challenge.labapp.infrastructure.config to spring.core, spring.beans, spring.context;
    opens com.dasa.challenge.labapp.application.usecases.apiClient.impl to spring.core, spring.beans, spring.context;
    opens com.dasa.challenge.labapp.application.usecases.auth.impl to spring.core, spring.beans, spring.context;
    opens com.dasa.challenge.labapp.application.usecases.cart.impl to spring.core, spring.beans, spring.context;
    opens com.dasa.challenge.labapp.infrastructure.gateways.apiClient to spring.core, spring.beans, spring.context;
    opens com.dasa.challenge.labapp.infrastructure.gateways.cart to spring.core, spring.beans, spring.context;
    opens com.dasa.challenge.labapp.application.dtos to com.fasterxml.jackson.databind;

    exports com.dasa.challenge.labapp;
}
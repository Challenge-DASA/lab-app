module com.dasa.challenge.labapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens com.dasa.challenge.labapp.infrastructure.controllers to javafx.fxml;
    opens com.dasa.challenge.labapp.infrastructure.components to javafx.fxml;
    exports com.dasa.challenge.labapp;
}
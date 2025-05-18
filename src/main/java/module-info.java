module com.dasa.challenge.labapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens com.dasa.challenge.labapp.controllers.components to javafx.fxml;
    exports com.dasa.challenge.labapp;
    opens com.dasa.challenge.labapp.controllers.pages to javafx.fxml;
}
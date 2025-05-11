module com.dasa.challenge.labapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dasa.challenge.labapp.controllers to javafx.fxml;
    exports com.dasa.challenge.labapp;
}
package com.dasa.challenge.labapp.infrastructure.views.home;


import com.dasa.challenge.labapp.application.views.confirmProcedure.ConfirmProcedureView;
import com.dasa.challenge.labapp.application.views.home.HomeView;
import com.dasa.challenge.labapp.infrastructure.utils.SliderSwitch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomeViewImpl implements HomeView {

    @FXML
    private Label welcomeText;

    @FXML
    private Button startWithdrawButton;

    private final Stage stage;

    private final ConfirmProcedureView confirmProcedureView;

    public HomeViewImpl(Stage stage, ConfirmProcedureView confirmProcedureView) {
        this.stage = stage;
        this.confirmProcedureView = confirmProcedureView;
    }

    @Override
    public Parent start() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HomeViewImpl.class.getResource(
                            "/com/dasa/challenge/labapp/views/home/home.fxml"));
            loader.setController(this);
            Parent root = loader.load();

            if (stage.getScene() == null) {
                StackPane rootContainer = new StackPane(root);
                Scene scene = new Scene(rootContainer, 800, 600);
                scene.getStylesheets().add(Objects.requireNonNull(
                                getClass().getResource("/com/dasa/challenge/labapp/styles/home/home.css"))
                        .toExternalForm());

                stage.setTitle("SmartLab Inventory");
                stage.setScene(scene);
                stage.show();
            }

            return root;

        } catch (IOException ex) {
            throw new RuntimeException("Failed to load home view", ex);
        }
    }

    @Override
    public void nextPage() {
        SliderSwitch.slideTo(
                this.stage,
                () -> {
                    Parent newView = confirmProcedureView.start();
                    stage.getScene().setRoot(newView);
                },
                "/com/dasa/challenge/labapp/styles/confirm-procedure/confirm-procedure.css"
        );
    }

    @FXML
    private void initialize() {
        startWithdrawButton.setOnAction(event -> nextPage());
    }
}
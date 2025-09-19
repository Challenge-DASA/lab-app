package com.dasa.challenge.labapp.infrastructure.views.rfidAuth;

import com.dasa.challenge.labapp.application.usecases.apiClient.ApiClientUseCase;
import com.dasa.challenge.labapp.application.usecases.auth.AuthUseCase;
import com.dasa.challenge.labapp.application.usecases.cart.CartUseCase;
import com.dasa.challenge.labapp.application.views.auth.RfidAuthView;
import com.dasa.challenge.labapp.application.views.conclusion.ConclusionView;
import com.dasa.challenge.labapp.infrastructure.components.RfidReaderComponent;
import com.dasa.challenge.labapp.infrastructure.utils.SliderSwitch;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class RfidAuthViewImpl implements RfidAuthView {

    @FXML
    private VBox mainContainer;

    @FXML
    private Label titleLabel;

    @FXML
    private Label instructionLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private RfidReaderComponent rfidReaderIcon;

    private final Stage stage;
    private final AuthUseCase authUseCase;
    private ConclusionView conclusionView;
    private Consumer<UUID> onAuthSuccess;
    private Timeline animationTimeline;
    private final ApiClientUseCase apiClientUseCase;
    private final CartUseCase cartUseCase;
    private final UUID laboratoryId;

    public RfidAuthViewImpl(Stage stage,
                            AuthUseCase authUseCase,
                            ApiClientUseCase apiClientUseCase,
                            CartUseCase cartUseCase,
                            UUID laboratoryId) {
        this.stage = stage;
        this.authUseCase = authUseCase;
        this.apiClientUseCase = apiClientUseCase;
        this.cartUseCase = cartUseCase;
        this.laboratoryId = laboratoryId;
    }

    @Override
    public Parent start() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    RfidAuthViewImpl.class.getResource(
                            "/com/dasa/challenge/labapp/views/rfid-auth/rfid-auth.fxml"
                    )
            );
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();

            if (stage.getScene() == null) {
                StackPane rootContainer = new StackPane();
                rootContainer.getChildren().add(root);

                Scene scene = new Scene(rootContainer, 800, 600);
                scene.getStylesheets().add(Objects.requireNonNull(
                        getClass().getResource(
                                "/com/dasa/challenge/labapp/styles/rfid-auth/rfid-auth.css"
                        )
                ).toExternalForm());

                this.stage.setTitle("Autenticação - SmartLab Inventory");
                this.stage.setScene(scene);
                this.stage.show();
            }

            initializeRfidReader();
            startWaitingAnimation();

            return root;

        } catch (IOException err) {
            throw new RuntimeException("Failed to load RFID auth view", err);
        }
    }

    @Override
    public void nextPage() {
        System.out.println("Navigating to Conclusion Page...");

        SliderSwitch.slideTo(
                this.stage,
                () -> {
                    Parent newView = conclusionView.start();
                    stage.getScene().setRoot(newView);
                },
                "/com/dasa/challenge/labapp/styles/conclusion/conclusion.css"
        );
    }

    @FXML
    private void initialize() {
        titleLabel.setText("Autenticação Necessária");
        instructionLabel.setText("Aproxime seu cartão do leitor RFID");
        statusLabel.setText("Aguardando cartão...");
    }

    private void initializeRfidReader() {
        authUseCase.authHandler(rfidToken -> {
            if (!this.cartUseCase.getAll().isEmpty()) {
                handleCardRead(rfidToken);
                for (var procedure : cartUseCase.getAll()) {
                    System.out.println("Asking for procedure withdraw: " + procedure.getName() + " (ID: " + procedure.getId() + ")");
                    try {
                        apiClientUseCase.sendWithdrawnItems(laboratoryId, UUID.fromString(procedure.getId()), rfidToken);
                        System.out.println("Procedure " + procedure.getName() + " withdrawn successfully.");
                    } catch (Exception e) {
                        System.err.println("Failed to withdraw procedure '" + procedure.getName() + "': " + e.getMessage());
                        Platform.runLater(() ->
                                showError("Falha para resgatar itens do procedimento '" + procedure.getName() + "': " + e.getMessage())
                        );
                    }

                    this.cartUseCase.remove(UUID.fromString(procedure.getId()));
                }

                this.nextPage();
            }
        });

        authUseCase.validateAuth();
    }

    private void startWaitingAnimation() {
        animationTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> statusLabel.setText("Aguardando cartão.")),
                new KeyFrame(Duration.seconds(0.5), e -> statusLabel.setText("Aguardando cartão..")),
                new KeyFrame(Duration.seconds(1), e -> statusLabel.setText("Aguardando cartão...")),
                new KeyFrame(Duration.seconds(1.5), e -> statusLabel.setText("Aguardando cartão"))
        );
        animationTimeline.setCycleCount(Animation.INDEFINITE);
        animationTimeline.play();
    }

    private void handleCardRead(UUID rfidToken) {
        if (animationTimeline != null) {
            animationTimeline.stop();
        }

        statusLabel.setText("Cartão detectado! Prosseguindo...");
        statusLabel.getStyleClass().add("success");

        rfidReaderIcon.showSuccess();

        Timeline proceedTimeline = new Timeline(new KeyFrame(
                Duration.seconds(1.5),
                e -> {
                    if (onAuthSuccess != null) {
                        onAuthSuccess.accept(rfidToken);
                    }
                }
        ));
        proceedTimeline.play();
    }

    public void setConclusionView(ConclusionView conclusionView) {
        this.conclusionView = conclusionView;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(stage);
        alert.showAndWait();
    }

}
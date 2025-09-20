package com.dasa.challenge.labapp.infrastructure.views.rfidAuth;

import com.dasa.challenge.labapp.infrastructure.views.BaseJavaFxTest;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class RfidAuthViewImplTest extends BaseJavaFxTest {

    @Test
    void testNextPage() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                RfidAuthViewImpl view = new RfidAuthViewImpl(stage, null, null, null, null);
                assertDoesNotThrow(view::nextPage);
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Test timed out");
    }
}
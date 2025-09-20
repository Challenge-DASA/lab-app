package com.dasa.challenge.labapp.infrastructure.views.home;

import com.dasa.challenge.labapp.infrastructure.views.BaseJavaFxTest;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HomeViewImplTest extends BaseJavaFxTest {

    @Test
    void testStart() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                HomeViewImpl view = new HomeViewImpl(stage, null);
                assertDoesNotThrow(view::start);
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Test timed out");
    }

    @Test
    void testNextPage() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                HomeViewImpl view = new HomeViewImpl(stage, null);
                assertDoesNotThrow(view::nextPage);
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Test timed out");

    }

}
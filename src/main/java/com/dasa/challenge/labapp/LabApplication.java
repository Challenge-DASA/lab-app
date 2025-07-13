package com.dasa.challenge.labapp;

import com.dasa.challenge.labapp.application.usecases.home.HomePageUseCase;
import com.dasa.challenge.labapp.infrastructure.config.BeanConfig;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class LabApplication extends Application {

    private final HomePageUseCase homePageUseCase;
    private static ApplicationContext applicationContext;

    public LabApplication() {
        if (applicationContext == null) {
            applicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);
        }

        this.homePageUseCase = applicationContext.getBean(HomePageUseCase.class);
    }

    @Override
    public void start(Stage stage) {
        System.out.println("Starting Lab Application...");
        this.homePageUseCase.start();
    }


    public static void main(String[] args) {
        launch();
    }
}
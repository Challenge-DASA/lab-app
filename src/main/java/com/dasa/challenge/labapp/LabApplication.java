package com.dasa.challenge.labapp;

import com.dasa.challenge.labapp.application.views.home.HomeView;
import com.dasa.challenge.labapp.infrastructure.config.BeanConfig;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class LabApplication extends Application {

    private final HomeView homeView;
    private static ApplicationContext applicationContext;

    public LabApplication() {
        if (applicationContext == null) {
            applicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);
        }

        this.homeView = applicationContext.getBean(HomeView.class);
    }

    @Override
    public void start(Stage stage) {
        System.out.println("Starting Lab Application...");

        stage.setTitle("SmartLab Inventory");
        this.homeView.start();
    }


    public static void main(String[] args) {
        launch();
    }
}
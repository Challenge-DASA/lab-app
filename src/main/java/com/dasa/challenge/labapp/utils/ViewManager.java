package com.dasa.challenge.labapp.utils;

import com.dasa.challenge.labapp.LabApplication;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is a class to centralize the view switching logic, avoiding to have LabApplication in various classes.
 */
public class ViewManager {

    public static void setView(Parent newView) {
        LabApplication.setView(newView);
    }

    public static Stage getMainStage() {
        return LabApplication.getMainStage();
    }

    public static Scene getMainScene() {
        return LabApplication.getMainScene();
    }
}

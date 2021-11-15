package InventoryManagementApplication;
/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Breanna Marsiano
 */

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class InventoryManagementApplication extends javafx.application.Application {

    /* Open Application */
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/InventoryApp.fxml")));

        Scene scene = new Scene(root);   // attach scene graph to scene
        stage.setTitle("Inventory");     // displayed in window's title bar
        stage.setScene(scene);           // attach scene to stage
        stage.show();                    // display the stage
    }

    public static void main(String[] args) { launch(args); }
}
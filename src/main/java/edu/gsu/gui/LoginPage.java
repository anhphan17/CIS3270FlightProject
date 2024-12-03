package edu.gsu.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class LoginPage extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button btLogin = new Button("Login");
        Scene scene  = new Scene(btLogin, 300, 200);
        primaryStage.setTitle("LoginPage");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);

    }

}

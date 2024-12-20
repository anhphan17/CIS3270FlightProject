package edu.gsu.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainPage extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create the root pane
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 400);
        root.setStyle("-fx-background-color: #89CFF0;");

        // Create the login button
        Button btnLogin = new Button("Login");
        btnLogin.setFont(Font.font("Serif", 12));
        btnLogin.setLayoutX(200);
        btnLogin.setLayoutY(200);
        btnLogin.setPrefSize(200, 25);

        btnLogin.setOnAction(event -> {
            LoginPage loginPage = new LoginPage();
            try {
                loginPage.start(primaryStage);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button btnRegister = new Button("Register");
        btnRegister.setFont(Font.font("Serif", 12));
        btnRegister.setLayoutX(200);
        btnRegister.setLayoutY(250);
        btnRegister.setPrefSize(200, 25);

        btnRegister.setOnAction(event -> {
            RegistrationPage registrationPage = new RegistrationPage();
            try {
                registrationPage.start(primaryStage);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button btnAdminLogin = new Button("Admin Login");
        btnAdminLogin.setFont(Font.font("Serif", 12));
        btnAdminLogin.setLayoutX(200);
        btnAdminLogin.setLayoutY(300);
        btnAdminLogin.setPrefSize(200, 25);

        btnAdminLogin.setOnAction(event -> {
            AdminLoginPage adminLoginPage = new AdminLoginPage();
            try {
                adminLoginPage.start(primaryStage);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Create the label for the title
        Label lblTitle = new Label("MIA Flights");
        lblTitle.setFont(Font.font("Serif", 50));
        lblTitle.setTextFill(javafx.scene.paint.Color.web("#fffb27"));
        lblTitle.setLayoutX(175);
        lblTitle.setLayoutY(50);
        lblTitle.setPrefSize(411, 113);

        // Add all nodes to the root pane
        root.getChildren().addAll( btnLogin, btnRegister, lblTitle, btnAdminLogin);

        // Set the scene and stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Main Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

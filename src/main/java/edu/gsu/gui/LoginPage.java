package edu.gsu.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the root pane
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 400);
        root.setStyle("-fx-background-color: #89CFF0;");

        // Create the username text field
        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Username");
        txtUsername.setLayoutX(214);
        txtUsername.setLayoutY(174);
        txtUsername.setPrefSize(190, 25);

        // Create the password text field
        TextField txtPassword = new TextField();
        txtPassword.setPromptText("Password");
        txtPassword.setLayoutX(214);
        txtPassword.setLayoutY(214);
        txtPassword.setPrefSize(190, 25);

        // Create the login button
        Button btnLogin = new Button("Login");
        btnLogin.setFont(Font.font("Serif", 12));
        btnLogin.setLayoutX(268);
        btnLogin.setLayoutY(266);
        btnLogin.setPrefSize(81, 25);

        // Add button click handler
        btnLogin.setOnAction(e -> {
            String username = txtUsername.getText();
            String password = txtPassword.getText();

            if (username.isEmpty() || password.isEmpty()) {
                System.out.println("Please enter both username and password.");
            } else {
                System.out.println("Login button clicked! Username: " + username + ", Password: " + password);
            }
        });

        // Create the label for the title
        Label lblTitle = new Label("Mia Airlines");
        lblTitle.setFont(Font.font("Serif", 50));
        lblTitle.setTextFill(javafx.scene.paint.Color.web("#fffb27"));
        lblTitle.setLayoutX(175);
        lblTitle.setLayoutY(50);
        lblTitle.setPrefSize(411, 113);

        // Add all nodes to the root pane
        root.getChildren().addAll(txtUsername, txtPassword, btnLogin, lblTitle);

        // Set the scene and stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

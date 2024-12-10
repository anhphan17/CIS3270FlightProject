package edu.gsu.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginPage extends Application {

    private static final String DB_URL = "jdbc:mysql://cis3270flightproject.mysql.database.azure.com:3306/project3270";
    private static final String DB_USERNAME = "aphan17";
    private static final String DB_PASSWORD = "Mendes1998!";

    @Override
    public void start(Stage primaryStage) {
        // Create the root pane
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 400);
        root.setStyle("-fx-background-color: #89CFF0;");

        // Create the username text field
        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Username");
        txtUsername.setLayoutX(200);
        txtUsername.setLayoutY(174);
        txtUsername.setPrefSize(200, 25);

        // Create the password text field
        TextField txtPassword = new TextField();
        txtPassword.setPromptText("Password");
        txtPassword.setLayoutX(200);
        txtPassword.setLayoutY(214);
        txtPassword.setPrefSize(200, 25);

        // Create the login button
        Button btnLogin = new Button("Login");
        btnLogin.setFont(Font.font("Serif", 12));
        btnLogin.setLayoutX(268);
        btnLogin.setLayoutY(266);
        btnLogin.setPrefSize(81, 25);

        Label lblMessage = new Label();
        lblMessage.setFont(Font.font("Serif", 12));
        lblMessage.setLayoutX(200);
        lblMessage.setLayoutY(140);
        lblMessage.setPrefSize(300,25);
        lblMessage.setStyle("-fx-text-fill: red;");

        Button btnRegister = new Button("Register");
        btnRegister.setFont(Font.font("Serif", 12));
        btnRegister.setLayoutX(450);
        btnRegister.setLayoutY(350);
        btnRegister.setPrefSize(80, 25);

        btnRegister.setOnAction(event -> {
            RegistrationPage registrationPage = new RegistrationPage();
            try {
                registrationPage.start(primaryStage);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button btnBackToMain = new Button("Back To Main");
        btnBackToMain.setFont(Font.font("Serif", 12));
        btnBackToMain.setLayoutX(25);
        btnBackToMain.setLayoutY(350);
        btnBackToMain.setPrefSize(100, 25);

        btnBackToMain.setOnAction(e -> {
            try {
                primaryStage.close();
                Stage mainPageStage = new Stage();
                new MainPage().start(mainPageStage);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Add button click handler
        btnLogin.setOnAction(e -> {
            String username = txtUsername.getText();
            String password = txtPassword.getText();

            if (username.isEmpty() || password.isEmpty()) {
                if (username.isEmpty() && password.isEmpty()) {
                    lblMessage.setText("Please enter your username and password.");
                } else if (username.isEmpty()) {
                    lblMessage.setText("Please enter a username.");
                } else {
                    lblMessage.setText("Please enter a password.");
                }
            } else {
                String usernameCheckMessage = checkUsernameExists(username);
                if (usernameCheckMessage != null) {
                    lblMessage.setStyle("-fx-text-fill: red;");
                    lblMessage.setText(usernameCheckMessage);
                }
                else {
                    if (checkLoginCredentials(username, password)) {
                        lblMessage.setStyle("-fx-text-fill: green;");
                        lblMessage.setText("Login successful! Proceeding to flight booking.");
                        primaryStage.close();
                        Stage bookFlightStage = new Stage();
                        new BookingPage().start(bookFlightStage);
                    } else {
                        lblMessage.setStyle("-fx-text-fill: red;");
                        lblMessage.setText("Incorrect username or password. Please try again.");
                    }
                }
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
        root.getChildren().addAll(txtUsername, txtPassword, btnLogin, lblTitle, lblMessage, btnBackToMain, btnRegister);

        // Set the scene and stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String checkUsernameExists(String username) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    return "Username does not exist. Please register an account.";
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Database connection error.");
        }
        return null;
    }

    private boolean checkLoginCredentials(String username, String password) {
        boolean isValidUser = false;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    isValidUser = true;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Database connection error.");
        }
        return isValidUser;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

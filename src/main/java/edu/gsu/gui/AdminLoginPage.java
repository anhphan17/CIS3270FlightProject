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

public class AdminLoginPage extends Application {
    private static final String DB_URL = "jdbc:mysql://cis3270flightproject.mysql.database.azure.com:3306/project3270";
    private static final String DB_USERNAME = "aphan17";
    private static final String DB_PASSWORD = "Mendes1998!";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 400);
        root.setStyle("-fx-background-color: #89CFF0;");

        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Username");
        txtUsername.setLayoutX(200);
        txtUsername.setLayoutY(174);
        txtUsername.setPrefSize(200, 25);

        TextField txtPassword = new TextField();
        txtPassword.setPromptText("Password");
        txtPassword.setLayoutX(200);
        txtPassword.setLayoutY(214);
        txtPassword.setPrefSize(200, 25);

        Label lblMessage = new Label();
        lblMessage.setFont(Font.font("Serif", 12));
        lblMessage.setLayoutX(150);
        lblMessage.setLayoutY(140);
        lblMessage.setPrefSize(400,25);
        lblMessage.setStyle("-fx-text-fill: red;");

        Button btnLogin = new Button("Login");
        btnLogin.setFont(Font.font("Serif", 12));
        btnLogin.setLayoutX(195);
        btnLogin.setLayoutY(260);
        btnLogin.setPrefSize(100, 25);

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
                    if (checkLoginCredentials(username, password, true)) {
                        lblMessage.setStyle("-fx-text-fill: green;");
                        lblMessage.setText("Login successful! Proceeding to flight booking.");
                        primaryStage.close();
                        Stage adminPanel = new Stage();
                        new AdminPanel().start(adminPanel);
                    } else {
                        lblMessage.setStyle("-fx-text-fill: red;");
                        lblMessage.setText("Access Denied. You are not an admin. Please log in as a customer.");
                    }
                }
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

        Label lblTitle = new Label("MIA Flights");
        lblTitle.setFont(Font.font("Serif", 50));
        lblTitle.setTextFill(javafx.scene.paint.Color.web("#fffb27"));
        lblTitle.setLayoutX(175);
        lblTitle.setLayoutY(50);
        lblTitle.setPrefSize(411, 113);

        Button btnForgotPassword = new Button("Forgot Password");
        btnForgotPassword.setFont(Font.font("Serif", 12));
        btnForgotPassword.setLayoutX(305);
        btnForgotPassword.setLayoutY(260);
        btnForgotPassword.setPrefSize(100, 25);

        btnForgotPassword.setOnAction(e -> {
            try {
                primaryStage.close();
                Stage ForgotPasswordPageStage = new Stage();
                new ForgotPassword().start(ForgotPasswordPageStage);
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        });


        root.getChildren().addAll(txtUsername, txtPassword, btnLogin, lblTitle, lblMessage,
                btnBackToMain, btnForgotPassword);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Admin Login Page");
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

    private boolean checkLoginCredentials(String username, String password, boolean isAdminLogin) {
        boolean isValidUser = false;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT role FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String role = resultSet.getString("role");
                    if (isAdminLogin && !"admin".equalsIgnoreCase(role)) {
                        return false;
                    }
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
}
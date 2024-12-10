package edu.gsu.gui;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.*;

public class ForgotPassword extends Application {

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

        Label lblTitle = new Label("MIA Flights");
        lblTitle.setFont(Font.font("Serif", 50));
        lblTitle.setTextFill(javafx.scene.paint.Color.web("#fffb27"));
        lblTitle.setLayoutX(175);
        lblTitle.setLayoutY(5);
        lblTitle.setPrefSize(400, 100);

        Label lblSubtitle = new Label("Forgot Password");
        lblSubtitle.setFont(Font.font("Serif", 25));
        lblSubtitle.setTextFill(javafx.scene.paint.Color.web("#fffb27"));
        lblSubtitle.setLayoutX(225);
        lblSubtitle.setLayoutY(75);
        lblSubtitle.setPrefSize(200, 50);

        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Username");
        txtUsername.setLayoutX(20);
        txtUsername.setLayoutY(130);
        txtUsername.setPrefSize(200, 25);

        Button btnGo = new Button("Go");
        btnGo.setFont(Font.font("Serif", 12));
        btnGo.setLayoutX(20);
        btnGo.setLayoutY(160);
        btnGo.setPrefSize(200, 25);

        TextArea txtSecurityQuestion = new TextArea();
        txtSecurityQuestion.setText("Security Question");
        txtSecurityQuestion.setLayoutX(250);
        txtSecurityQuestion.setLayoutY(130);
        txtSecurityQuestion.setPrefSize(200, 75);
        txtSecurityQuestion.setEditable(false);

        TextField txtSecurityAnswer = new TextField();
        txtSecurityAnswer.setPromptText("Security Answer");
        txtSecurityAnswer.setLayoutX(250);
        txtSecurityAnswer.setLayoutY(210);
        txtSecurityAnswer.setPrefSize(200, 75);

        Button btnLookUp = new Button("Look Up");
        btnLookUp.setFont(Font.font("Serif", 12));
        btnLookUp.setLayoutX(250);
        btnLookUp.setLayoutY(290);
        btnLookUp.setPrefSize(200, 25);

        TextArea txtPassword = new TextArea();
        txtPassword.setText("Password");
        txtPassword.setLayoutX(250);
        txtPassword.setLayoutY(320);
        txtPassword.setPrefSize(200, 25);
        txtPassword.setEditable(false);

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

        Button btnBackToLogin = new Button("Back To Login");
        btnBackToLogin.setFont(Font.font("Serif", 12));
        btnBackToLogin.setLayoutX(475);
        btnBackToLogin.setLayoutY(350);
        btnBackToLogin.setPrefSize(100, 25);

        btnBackToLogin.setOnAction(e -> {
            try {
                primaryStage.close();
                Stage loginPageStage = new Stage();
                new LoginPage().start(loginPageStage);
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        });

        btnGo.setOnAction(e -> {
            String username = txtUsername.getText();
            String securityQuestion = getSecurityQuestion(username);
            if (securityQuestion != null) {
                txtSecurityQuestion.setText(securityQuestion);
            }
            else {
                txtSecurityQuestion.setText("Username not found.");
            }
        });

        btnLookUp.setOnAction(e -> {
            String username = txtUsername.getText();
            String answer = txtSecurityAnswer.getText();
            String password = verifySecurityAnswer(username, answer);
            if (password != null) {
                txtPassword.setText(password);
            }
            else {
                txtPassword.setText("Incorrect answer.");
            }
        });

        root.getChildren().addAll(lblTitle, lblSubtitle, txtUsername, btnGo, txtSecurityQuestion, txtSecurityAnswer,
                btnLookUp, txtPassword, btnBackToLogin, btnBackToMain);


        Scene scene = new Scene(root);
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String getSecurityQuestion(String username) {
        String securityQuestion = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT security_question FROM users WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    securityQuestion = resultSet.getString("security_question");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Database connection error.");
        }
        return securityQuestion;
    }

    private String verifySecurityAnswer(String username, String answer) {
        String password = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT password, security_answer FROM users WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String correctAnswer = resultSet.getString("security_answer");
                    if (correctAnswer != null && correctAnswer.equals(answer)) {
                        password = resultSet.getString("password");
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Database connection error.");
        }
        return password;
    }
}

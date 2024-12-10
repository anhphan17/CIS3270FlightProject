package edu.gsu.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegistrationPage extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create the root pane
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 400);
        root.setStyle("-fx-background-color: #89CFF0;");

        // Create the First Name text field
        TextField txtFirstName = new TextField();
        txtFirstName.setPromptText("First Name");
        txtFirstName.setLayoutX(50);
        txtFirstName.setLayoutY(100);
        txtFirstName.setPrefSize(200, 25);

        // Create the Last Name text field
        TextField txtLastName = new TextField();
        txtLastName.setPromptText("Last Name");
        txtLastName.setLayoutX(50);
        txtLastName.setLayoutY(135);
        txtLastName.setPrefSize(200, 25);

        // Create the Address TextField
        TextField txtAddress = new TextField();
        txtAddress.setPromptText("Address");
        txtAddress.setLayoutX(50);
        txtAddress.setLayoutY(170);
        txtAddress.setPrefSize(200, 25);

        TextField txtZipcode = new TextField();
        txtZipcode.setPromptText("Zipcode");
        txtZipcode.setLayoutX(50);
        txtZipcode.setLayoutY(205);
        txtZipcode.setPrefSize(200, 25);

        TextField txtState = new TextField();
        txtState.setPromptText("State");
        txtState.setLayoutX(50);
        txtState.setLayoutY(240);
        txtState.setPrefSize(200, 25);

        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Username");
        txtUsername.setLayoutX(350);
        txtUsername.setLayoutY(100);
        txtUsername.setPrefSize(200, 25);

        TextField txtPassword = new TextField();
        txtPassword.setPromptText("Password");
        txtPassword.setLayoutX(350);
        txtPassword.setLayoutY(135);
        txtPassword.setPrefSize(200, 25);

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtEmail.setLayoutX(350);
        txtEmail.setLayoutY(170);
        txtEmail.setPrefSize(200, 25);

        TextField txtSSN = new TextField();
        txtSSN.setPromptText("Social Security Number");
        txtSSN.setLayoutX(350);
        txtSSN.setLayoutY(205);
        txtSSN.setPrefSize(200, 25);

        ComboBox<String> cmbSecurityQuestion = new ComboBox<>();
        cmbSecurityQuestion.setPromptText("Select Security Question");
        cmbSecurityQuestion.getItems().addAll(
                "What is your mother's maiden name?",
                "What was the name of your first pet?",
                "What was your first car?",
                "What is your favorite food?",
                "What city were you born in?",
                "What was your childhood best friend's name?"
        );
        cmbSecurityQuestion.setLayoutX(350);
        cmbSecurityQuestion.setLayoutY(240);
        cmbSecurityQuestion.setPrefSize(200,25);

        TextField txtSecurityAnswer = new TextField();
        txtSecurityAnswer.setPromptText("Security Answer");
        txtSecurityAnswer.setLayoutX(200);
        txtSecurityAnswer.setLayoutY(280);
        txtSecurityAnswer.setPrefSize(200, 25);

        // Create the login button
        Button btnRegister = new Button("Register");
        btnRegister.setFont(Font.font("Serif", 12));
        btnRegister.setLayoutX(250);
        btnRegister.setLayoutY(325);
        btnRegister.setPrefSize(100, 25);


        // Create the label for the title
        Label lblTitle = new Label("MIA Flights");
        lblTitle.setFont(Font.font("Serif", 50));
        lblTitle.setTextFill(javafx.scene.paint.Color.web("#fffb27"));
        lblTitle.setLayoutX(175);
        lblTitle.setLayoutY(5);
        lblTitle.setPrefSize(411, 113);

        Button btnBack = new Button("Back");
        btnBack.setFont(Font.font("Serif", 12));
        btnBack.setLayoutX(25);
        btnBack.setLayoutY(350);
        btnBack.setPrefSize(81, 25);

        btnBack.setOnAction(e -> {
            try {
                primaryStage.close();
                Stage mainPageStage = new Stage();
                new MainPage().start(mainPageStage);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Add all nodes to the root pane
        root.getChildren().addAll(txtFirstName, txtLastName, txtAddress, txtZipcode,
                txtState, txtUsername, txtPassword, txtEmail, txtSSN,
                cmbSecurityQuestion, txtSecurityAnswer, btnRegister, lblTitle, btnBack);

        // Set the scene and stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Registration Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

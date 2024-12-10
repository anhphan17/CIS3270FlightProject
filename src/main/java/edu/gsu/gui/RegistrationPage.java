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

    private static final String DB_URL = "jdbc:mysql://cis3270flightproject.mysql.database.azure.com:3306/project3270";
    private static final String DB_USERNAME = "aphan17";
    private static final String DB_PASSWORD = "Mendes1998!";

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

        Label lblTitle = new Label("MIA Flights");
        lblTitle.setFont(Font.font("Serif", 50));
        lblTitle.setTextFill(javafx.scene.paint.Color.web("#fffb27"));
        lblTitle.setLayoutX(175);
        lblTitle.setLayoutY(5);
        lblTitle.setPrefSize(400, 100);

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

        Label lblMessage = new Label();
        lblMessage.setFont(Font.font("Serif", 12));
        lblMessage.setLayoutX(75);
        lblMessage.setLayoutY(75);
        lblMessage.setPrefSize(300, 10);
        lblMessage.setStyle("-fx-text-fill: red");

        Button btnRegister = new Button("Register");
        btnRegister.setFont(Font.font("Serif", 12));
        btnRegister.setLayoutX(250);
        btnRegister.setLayoutY(325);
        btnRegister.setPrefSize(100, 25);

        btnRegister.setOnAction(e -> {
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String address = txtAddress.getText();
            String zipcode = txtZipcode.getText();
            String state = txtState.getText();
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String email = txtEmail.getText();
            String ssn = txtSSN.getText();
            String securityQuestion = cmbSecurityQuestion.getValue();
            String securityAnswer = txtSecurityAnswer.getText();

            if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || zipcode.isEmpty() ||
                    state.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || ssn.isEmpty() ||
                    securityQuestion == null || securityAnswer.isEmpty()) {
                lblMessage.setText("Please fill out all fields to register.");
                return;
            }

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                String query = "INSERT INTO users (first_name, last_name, address, zip_code, state, username, " +
                        "password, email, ssn, security_question, security_answer, role)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Customer')";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, firstName);
                    preparedStatement.setString(2, lastName);
                    preparedStatement.setString(3, address);
                    preparedStatement.setString(4, zipcode);
                    preparedStatement.setString(5, state);
                    preparedStatement.setString(6, username);
                    preparedStatement.setString(7, password);
                    preparedStatement.setString(8, email);
                    preparedStatement.setString(9, ssn);
                    preparedStatement.setString(10, securityQuestion);
                    preparedStatement.setString(11, securityAnswer);
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                int userId = generatedKeys.getInt(1);
                                lblMessage.setStyle("-fx-text-fill: green");
                                lblMessage.setText("Registration successful!");
                                primaryStage.close();

                                Stage bookFlightStage = new Stage();
                                new BookingPage(userId).start(bookFlightStage);
                            }
                        }
                    }
                }
            }
            catch (Exception ex) {
                lblMessage.setText("Error registering user: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // Add all nodes to the root pane
        root.getChildren().addAll(txtFirstName, txtLastName, txtAddress, txtZipcode,
                txtState, txtUsername, txtPassword, txtEmail, txtSSN,
                cmbSecurityQuestion, txtSecurityAnswer, btnRegister, lblTitle, btnBackToMain, lblMessage);

        // Set the scene and stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Registration Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

package edu.gsu.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminViewFlights extends Application {
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
        lblTitle.setLayoutY(50);
        lblTitle.setPrefSize(400, 100);

        TextArea txtViewFlights = new TextArea();
        txtViewFlights.setLayoutX(100);
        txtViewFlights.setLayoutY(150);
        txtViewFlights.setPrefSize(400, 100);
        txtViewFlights.setEditable(false);

        Button btnAdminMain = new Button("Back To Admin Panel");
        btnAdminMain.setFont(Font.font("Serif", 12));
        btnAdminMain.setLayoutX(25);
        btnAdminMain.setLayoutY(350);
        btnAdminMain.setPrefSize(150, 25);

        btnAdminMain.setOnAction(e -> {
            try {
                primaryStage.close();
                Stage adminMainPage = new Stage();
                new AdminPanel().start(adminMainPage);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT flight_number, departure_city, destination_city, departure_time, arrival_time " +
                    "FROM flights";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            StringBuilder flightDetails = new StringBuilder();
            while (resultSet.next()) {
                flightDetails.append("Flight Number: ").append(resultSet.getString("flight_number"))
                        .append("\n").append("From: ").append(resultSet.getString("departure_city"))
                        .append("\n").append("To: ").append(resultSet.getString("destination_city"))
                        .append("\n").append("Departure: ").append(resultSet.getString("departure_time"))
                        .append("\n").append("Arrival: ").append(resultSet.getString("arrival_time"))
                        .append("\n\n");
                }
            if (flightDetails.length() > 0) {
                txtViewFlights.setText((flightDetails.toString()));
            }
            else {
                txtViewFlights.setText("No flights found in the database.");
            }
        }
        catch (Exception e) {
            txtViewFlights.setText("Error fetching flight details. Please try again later.");
            e.printStackTrace();
        }

        root.getChildren().addAll(lblTitle, txtViewFlights, btnAdminMain);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Admin Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}

package edu.gsu.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDeleteFlight extends Application {
    private static final String DB_URL = "jdbc:mysql://cis3270flightproject.mysql.database.azure.com:3306/project3270";
    private static final String DB_USERNAME = "aphan17";
    private static final String DB_PASSWORD = "Mendes1998!";

    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 600);
        root.setStyle("-fx-background-color: #89CFF0;");
        // Title and Header
        Label lblTitle = new Label("MIA Flights");
        lblTitle.setFont(Font.font("Serif", 50));
        lblTitle.setTextFill(javafx.scene.paint.Color.web("#fffb27"));
        lblTitle.setLayoutX(175);
        lblTitle.setLayoutY(5);

        Label lblSubtitle = new Label("Delete Flight");
        lblSubtitle.setFont(Font.font("Serif", 25));
        lblSubtitle.setTextFill(javafx.scene.paint.Color.web("#fffb27"));
        lblSubtitle.setLayoutX(200);
        lblSubtitle.setLayoutY(60);
        // Dropdown for selecting flights
        ComboBox<String> cmbFlights = new ComboBox<>();
        cmbFlights.setPromptText("Select a Flight");
        cmbFlights.setLayoutX(100);
        cmbFlights.setLayoutY(100);
        cmbFlights.setPrefWidth(400);

        // Load flights into ComboBox
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT flight_number, departure_city, destination_city FROM flights";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String flightInfo = resultSet.getString("flight_number") + " (" +
                        resultSet.getString("departure_city") + " -> " +
                        resultSet.getString("destination_city") + ")";
                cmbFlights.getItems().add(flightInfo);
            }
        } catch (Exception e) {
            showAlert("Error", "Unable to load flights. Please try again.");
            e.printStackTrace();
        }

        // Delete button
        Button btnDelete = new Button("Delete Flight");
        btnDelete.setLayoutX(250);
        btnDelete.setLayoutY(150);
        btnDelete.setOnAction(e -> {
            String selectedFlight = cmbFlights.getValue();
            if (selectedFlight == null) {
                showAlert("Error", "Please select a flight to delete.");
                return;
            }
            String flightNumber = selectedFlight.split(" ")[0]; // Extract flight number
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                String deleteQuery = "DELETE FROM flights WHERE flight_number = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setString(1, flightNumber);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert("Success", "Flight deleted successfully.");
                    cmbFlights.getItems().remove(selectedFlight);
                } else {
                    showAlert("Error", "Failed to delete the flight.");
                }
            } catch (Exception ex) {
                showAlert("Error", "An error occurred while deleting the flight.");
                ex.printStackTrace();
            }
        });

        // Back button
        Button btnBack = new Button("Back to Admin Panel");
        btnBack.setLayoutX(250);
        btnBack.setLayoutY(200);
        btnBack.setOnAction(e -> {
            try {
                primaryStage.close();
                new AdminPanel().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        root.getChildren().addAll(lblTitle, cmbFlights, btnDelete, btnBack);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Admin - Delete Flight");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper to show alerts
    private void showAlert (String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main (String[]args){
        launch(args);
    }
}

package edu.gsu.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class AddFlight extends Application {
    private static final String DB_URL = "jdbc:mysql://cis3270flightproject.mysql.database.azure.com:3306/project3270";
    private static final String DB_USERNAME = "aphan17";
    private static final String DB_PASSWORD = "Mendes1998!";

    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 600);
        root.setStyle("-fx-background-color: #89CFF0;");

        Label lblTitle = new Label("MIA Flights");
        lblTitle.setFont(Font.font("Serif", 50));
        lblTitle.setTextFill(javafx.scene.paint.Color.web("#fffb27"));
        lblTitle.setLayoutX(175);
        lblTitle.setLayoutY(5);
        lblTitle.setPrefSize(411, 113);

        Label lblSubtitle = new Label("Flight Booking");
        lblSubtitle.setFont(Font.font("Serif", 25));
        lblSubtitle.setTextFill(javafx.scene.paint.Color.web("#fffb27"));
        lblSubtitle.setLayoutX(225);
        lblSubtitle.setLayoutY(60);
        lblSubtitle.setPrefSize(200, 100);

        TextField txtAirline = new TextField();
        txtAirline.setPromptText("Airline");
        txtAirline.setLayoutX(200);
        txtAirline.setLayoutY(180);
        txtAirline.setPrefSize(200, 25);

        TextField txtFlightNumber = new TextField();
        txtFlightNumber.setPromptText("Flight Number");
        txtFlightNumber.setLayoutX(200);
        txtFlightNumber.setLayoutY(210);
        txtFlightNumber.setPrefSize(200, 25);

        TextField txtDepartureCity = new TextField();
        txtDepartureCity.setPromptText("Departure City");
        txtDepartureCity.setLayoutX(200);
        txtDepartureCity.setLayoutY(240);
        txtDepartureCity.setPrefSize(200, 25);

        TextField txtDestinationCity = new TextField();
        txtDestinationCity.setPromptText("Destination City");
        txtDestinationCity.setLayoutX(200);
        txtDestinationCity.setLayoutY(270);
        txtDestinationCity.setPrefSize(200, 25);

        TextField txtFlightDate = new TextField();
        txtFlightDate.setPromptText("Flight Date YYYY-MM-DD");
        txtFlightDate.setLayoutX(200);
        txtFlightDate.setLayoutY(300);
        txtFlightDate.setPrefSize(200, 25);

        TextField txtDepartureTime = new TextField();
        txtDepartureTime.setPromptText("Departure Time HH:MM:SS");
        txtDepartureTime.setLayoutX(200);
        txtDepartureTime.setLayoutY(330);
        txtDepartureTime.setPrefSize(200, 25);

        TextField txtArrivalTime = new TextField();
        txtArrivalTime.setPromptText("Arrival Time HH:MM:SS");
        txtArrivalTime.setLayoutX(200);
        txtArrivalTime.setLayoutY(360);
        txtArrivalTime.setPrefSize(200, 25);

        TextField txtTotalSeats = new TextField();
        txtTotalSeats.setPromptText("Total Seats");
        txtTotalSeats.setLayoutX(200);
        txtTotalSeats.setLayoutY(390);
        txtTotalSeats.setPrefSize(200, 25);

        Button btnAddFlight = new Button("Add Flight");
        btnAddFlight.setLayoutX(200);
        btnAddFlight.setLayoutY(420);
        btnAddFlight.setPrefSize(200,25);

        btnAddFlight.setOnAction(e -> {
            String airline = txtAirline.getText();
            String flightNumber = txtFlightNumber.getText();
            String departureCity = txtDepartureCity.getText();
            String destinationCity = txtDestinationCity.getText();
            String flightDate = txtFlightDate.getText();
            String departureTime = txtDepartureTime.getText();
            String arrivalTime = txtArrivalTime.getText();
            String totalSeats = txtTotalSeats.getText();

            if (airline.isEmpty() || flightNumber.isEmpty() || departureCity.isEmpty() || destinationCity.isEmpty()
                    || flightDate.isEmpty() || departureTime.isEmpty() || arrivalTime.isEmpty()) {
                showAlert("Error", "Please fill in all required fields.");
                return;
            }

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                String query = "INSERT INTO flights (airline, flight_number, departure_city, destination_city, flight_date, departure_time, arrival_time, total_seats) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, airline);
                    preparedStatement.setString(2, flightNumber);
                    preparedStatement.setString(3, departureCity);
                    preparedStatement.setString(4, destinationCity);
                    preparedStatement.setString(5, flightDate);
                    preparedStatement.setString(6, departureTime);
                    preparedStatement.setString(7, arrivalTime);
                    preparedStatement.setInt(8, totalSeats.isEmpty() ? 0 : Integer.parseInt(totalSeats));

                    preparedStatement.executeUpdate();
                    showAlert("Success", "Flight added successfully!");
                }
            } catch (Exception ex) {
                showAlert("Error", "Error adding flight: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // Back Button
        Button btnBack = new Button("Back to Main Admin");
        btnBack.setLayoutX(20);
        btnBack.setLayoutY(500);
        btnBack.setOnAction(e -> {
            try {
                primaryStage.close();
                Stage adminStage = new Stage();
                new AdminPanel().start(adminStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Add components to the root
        root.getChildren().addAll(lblTitle, lblSubtitle, txtAirline, txtFlightNumber, txtDepartureCity,
                txtDestinationCity, txtFlightDate, txtDepartureTime, txtArrivalTime, txtTotalSeats, btnAddFlight, btnBack);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Add Flight - Admin");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


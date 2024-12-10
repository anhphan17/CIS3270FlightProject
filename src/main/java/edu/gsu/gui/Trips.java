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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Trips extends Application{
    private static final String DB_URL = "jdbc:mysql://cis3270flightproject.mysql.database.azure.com:3306/project3270";
    private static final String DB_USERNAME = "aphan17";
    private static final String DB_PASSWORD = "Mendes1998!";
    private int userId;

    public Trips() {
        this.userId = -1; // Default no value
    }

    public Trips(int userId) {
        this.userId = userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static void main(String[] args) {
            Trips trips = new Trips();
            trips.setUserId(123);
        launch(args);}

    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 400);
        root.setStyle("-fx-background-color: #89CFF0;");

        Label lblTitle = new Label("MIA Flights");
        lblTitle.setFont(Font.font("Serif", 50));
        lblTitle.setTextFill(javafx.scene.paint.Color.web("#fffb27"));
        lblTitle.setLayoutX(175);
        lblTitle.setLayoutY(10);

        Label lblSubtitle = new Label("Upcoming flights");
        lblSubtitle.setFont(Font.font("Serif", 25));
        lblSubtitle.setTextFill(javafx.scene.paint.Color.web("#fffb27"));
        lblSubtitle.setLayoutX(200);
        lblSubtitle.setLayoutY(30);
        lblSubtitle.setPrefSize(300, 100);

        ListView<String> lstBookedFlights = new ListView<>();
        lstBookedFlights.setLayoutX(50);
        lstBookedFlights.setLayoutY(150);
        lstBookedFlights.setPrefSize(500, 150);


        // Cancel Booking
        Button btnCancelBooking = new Button("Cancel Booking");
        btnCancelBooking.setLayoutX(150);
        btnCancelBooking.setLayoutY(320);
        btnCancelBooking.setOnAction(event -> {
            String selectedFlight = lstBookedFlights.getSelectionModel().getSelectedItem();
            if (selectedFlight == null) {
                showAlert("Error", "Please select a flight to cancel.");
                return;
            }

            // Cancel (remove from database and update ListView)
            String flightNumber = selectedFlight.split("\\|")[0].trim().replace("Flight: ", "");
            if (deleteBookingFromDatabase(flightNumber)) {
                // Remove from ListView if successfully deleted from the database
                lstBookedFlights.getItems().remove(selectedFlight);
                showAlert("Success", "Booking cancelled successfully.");
            } else {
                showAlert("Error", "Failed to cancel the booking. Please try again.");
            }
        });

        Button btnChangeBooking = new Button("Change Booking");
        btnChangeBooking.setLayoutX(350);
        btnChangeBooking.setLayoutY(320);
        btnChangeBooking.setOnAction(event -> {
            String selectedFlight = lstBookedFlights.getSelectionModel().getSelectedItem();
            if (selectedFlight == null) {
                showAlert("Error", "Please select a flight to change.");
                return;
            }
            // Navigate to BookingPage for rebooking
            try {
                primaryStage.close();
                Stage bookingPageStage = new Stage();
                new BookingPage(this.userId).start(bookingPageStage); // Use default constructor
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Book a Trip Button
        Button btnBookTrip = new Button("Book a Trip");
        btnBookTrip.setLayoutX(150);
        btnBookTrip.setLayoutY(370);
        btnBookTrip.setOnAction(event -> {
            try {
                primaryStage.close();
                Stage bookingPageStage = new Stage();
                new BookingPage(this.userId).start(bookingPageStage); // Use default constructor
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Log Out Button
        Button btnLogout = new Button("Log Out");
        btnLogout.setLayoutX(350);
        btnLogout.setLayoutY(370);
        btnLogout.setOnAction(event -> {
            try {
                primaryStage.close();
                Stage mainPageStage = new Stage();
                new MainPage().start(mainPageStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        // Add components to the root pane
        root.getChildren().addAll(lblTitle, lblSubtitle, lstBookedFlights, btnCancelBooking, btnChangeBooking, btnBookTrip, btnLogout);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Customer - View and Manage Booked Trips");
        primaryStage.setScene(scene);
        primaryStage.show();

        loadBookedFlights(lstBookedFlights);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void loadBookedFlights(ListView<String> lstBookedFlights) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT f.flight_number, f.departure_city, f.destination_city, f.departure_time, f.arrival_time " +
                    "FROM reservations r " +
                    "JOIN flights f ON r.flight_id = f.id " +
                    "WHERE r.user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId); // Use the userId for the query
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String flightDetails = String.format("Flight: %s | From: %s | To: %s | Departure: %s | Arrival: %s",
                            resultSet.getString("flight_number"),
                            resultSet.getString("departure_city"),
                            resultSet.getString("destination_city"),
                            resultSet.getString("departure_time"),
                            resultSet.getString("arrival_time"));
                    lstBookedFlights.getItems().add(flightDetails);
                }

                if (lstBookedFlights.getItems().isEmpty()) {
                    lstBookedFlights.getItems().add("No trips booked yet.");
                }
            }
        } catch (Exception e) {
            lstBookedFlights.getItems().add("Error loading trips. Please try again later.");
            e.printStackTrace();
        }
    }
    private boolean deleteBookingFromDatabase(String flightNumber) {
        String deleteQuery = "DELETE FROM reservations " +
                "WHERE flight_id = (SELECT id FROM flights WHERE flight_number = ?) AND user_id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, flightNumber);
            preparedStatement.setInt(2, userId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was affected
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


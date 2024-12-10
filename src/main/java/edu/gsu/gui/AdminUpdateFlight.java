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

public class AdminUpdateFlight extends Application {
    private static final String DB_URL = "jdbc:mysql://cis3270flightproject.mysql.database.azure.com:3306/project3270";
    private static final String DB_USERNAME = "aphan17";
    private static final String DB_PASSWORD = "Mendes1998!";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 600);
        root.setStyle("-fx-background-color: #89CFF0;");

        // Title
        Label lblTitle = new Label("MIA Flights");
        lblTitle.setFont(Font.font("Serif", 50));
        lblTitle.setTextFill(javafx.scene.paint.Color.web("#fffb27"));
        lblTitle.setLayoutX(175);
        lblTitle.setLayoutY(15);

        // Dropdown for selecting a flight
        ComboBox<String> cmbFlights = new ComboBox<>();
        cmbFlights.setPromptText("Select a Flight to Update");
        cmbFlights.setLayoutX(100);
        cmbFlights.setLayoutY(100);
        cmbFlights.setPrefWidth(400);

        // Load flights into ComboBox
        loadFlights(cmbFlights);

        // Text fields for editing flight details
        TextField txtAirline = new TextField();
        txtAirline.setPromptText("Airline");
        txtAirline.setLayoutX(225);
        txtAirline.setLayoutY(200);

        TextField txtDepartureCity = new TextField();
        txtDepartureCity.setPromptText("Departure City");
        txtDepartureCity.setLayoutX(225);
        txtDepartureCity.setLayoutY(240);

        TextField txtDestinationCity = new TextField();
        txtDestinationCity.setPromptText("Destination City");
        txtDestinationCity.setLayoutX(225);
        txtDestinationCity.setLayoutY(280);

        TextField txtFlightDate = new TextField();
        txtFlightDate.setPromptText("Flight Date (YYYY-MM-DD)");
        txtFlightDate.setLayoutX(225);
        txtFlightDate.setLayoutY(320);

        TextField txtDepartureTime = new TextField();
        txtDepartureTime.setPromptText("Departure Time (HH:mm)");
        txtDepartureTime.setLayoutX(225);
        txtDepartureTime.setLayoutY(360);

        TextField txtArrivalTime = new TextField();
        txtArrivalTime.setPromptText("Arrival Time (HH:mm)");
        txtArrivalTime.setLayoutX(225);
        txtArrivalTime.setLayoutY(400);

        TextField txtTotalSeats = new TextField();
        txtTotalSeats.setPromptText("Total Seats");
        txtTotalSeats.setLayoutX(225);
        txtTotalSeats.setLayoutY(440);

        // Populate fields when a flight is selected
        cmbFlights.setOnAction(e -> {
            String selectedFlight = cmbFlights.getValue();
            if (selectedFlight != null) {
                String flightNumber = selectedFlight.split(" ")[0]; // Extract flight number
                loadFlightDetails(flightNumber, txtAirline, txtDepartureCity, txtDestinationCity, txtFlightDate, txtDepartureTime, txtArrivalTime, txtTotalSeats);
            }
        });

        // Save changes button
        Button btnSave = new Button("Save Changes");
        btnSave.setLayoutX(250);
        btnSave.setLayoutY(490);
        btnSave.setPrefWidth(100);
        btnSave.setOnAction(e -> {
            String selectedFlight = cmbFlights.getValue();
            if (selectedFlight == null) {
                showAlert("Error", "Please select a flight to update.");
                return;
            }

            String flightNumber = selectedFlight.split(" ")[0];
            updateFlight(flightNumber, txtAirline, txtDepartureCity, txtDestinationCity, txtFlightDate, txtDepartureTime, txtArrivalTime, txtTotalSeats);
        });

        // Back button
        Button btnBack = new Button("Back to Admin Panel");     btnBack.setFont(Font.font("Serif", 12));
        btnBack.setLayoutX(25);
        btnBack.setLayoutY(550);
        btnBack.setPrefWidth(100);
        btnBack.setOnAction(e -> {
            try {
                primaryStage.close();
                new AdminPanel().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        root.getChildren().addAll(lblTitle, cmbFlights, txtAirline, txtDepartureCity, txtDestinationCity,
                txtFlightDate, txtDepartureTime, txtArrivalTime, txtTotalSeats, btnSave, btnBack);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Admin - Update Flight");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadFlights(ComboBox<String> cmbFlights) {
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
            showAlert("Error", "Unable to load flights.");
            e.printStackTrace();
        }
    }

    private void loadFlightDetails(String flightNumber, TextField txtAirline, TextField txtDepartureCity, TextField txtDestinationCity,
                                   TextField txtFlightDate, TextField txtDepartureTime, TextField txtArrivalTime, TextField txtTotalSeats) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM flights WHERE flight_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, flightNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                txtAirline.setText(resultSet.getString("airline"));
                txtDepartureCity.setText(resultSet.getString("departure_city"));
                txtDestinationCity.setText(resultSet.getString("destination_city"));
                txtFlightDate.setText(resultSet.getString("flight_date"));
                txtDepartureTime.setText(resultSet.getString("departure_time"));
                txtArrivalTime.setText(resultSet.getString("arrival_time"));
                txtTotalSeats.setText(resultSet.getString("total_seats"));
            }
        } catch (Exception e) {
            showAlert("Error", "Unable to load flight details.");
            e.printStackTrace();
        }
    }

    private void updateFlight(String flightNumber, TextField txtAirline, TextField txtDepartureCity, TextField txtDestinationCity,
                              TextField txtFlightDate, TextField txtDepartureTime, TextField txtArrivalTime, TextField txtTotalSeats) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String updateQuery = "UPDATE flights SET airline = ?, departure_city = ?, destination_city = ?, flight_date = ?, departure_time = ?, arrival_time = ?, total_seats = ? WHERE flight_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, txtAirline.getText());
            preparedStatement.setString(2, txtDepartureCity.getText());
            preparedStatement.setString(3, txtDestinationCity.getText());
            preparedStatement.setString(4, txtFlightDate.getText());
            preparedStatement.setString(5, txtDepartureTime.getText());
            preparedStatement.setString(6, txtArrivalTime.getText());
            preparedStatement.setInt(7, Integer.parseInt(txtTotalSeats.getText()));
            preparedStatement.setString(8, flightNumber);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Flight updated successfully.");
            } else {
                showAlert("Error", "Failed to update the flight.");
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred while updating the flight.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

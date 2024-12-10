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

public class BookingPage extends Application {

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

        TextField txtDepartureCity = new TextField();
        txtDepartureCity.setPromptText("Departure City");
        txtDepartureCity.setLayoutX(200);
        txtDepartureCity.setLayoutY(150);
        txtDepartureCity.setPrefSize(200, 25);

        TextField txtDestinationCity = new TextField();
        txtDestinationCity.setPromptText("Destination City");
        txtDestinationCity.setLayoutX(200);
        txtDestinationCity.setLayoutY(185);
        txtDestinationCity.setPrefSize(200, 25);

        TextField txtFlightDate = new TextField();
        txtFlightDate.setPromptText("Flight Date (YYYY-MM-DD)");
        txtFlightDate.setLayoutX(200);
        txtFlightDate.setLayoutY(220);
        txtFlightDate.setPrefSize(200, 25);

        Button btnSearch = new Button("Search");
        btnSearch.setFont(Font.font("Serif", 12));
        btnSearch.setLayoutX(250);
        btnSearch.setLayoutY(255);
        btnSearch.setPrefSize(100, 25);

        TextArea txtSearchResults = new TextArea();
        txtSearchResults.setText("Search Results");
        txtSearchResults.setLayoutX(100);
        txtSearchResults.setLayoutY(290);
        txtSearchResults.setPrefSize(400, 100);
        txtSearchResults.setEditable(false);

        ComboBox<String> cmbFlightOptions = new ComboBox<>();
        cmbFlightOptions.setPromptText("Select a Flight");
        cmbFlightOptions.setLayoutX(200);
        cmbFlightOptions.setLayoutY(400);
        cmbFlightOptions.setPrefSize(200, 25);

        TextField txtUserId = new TextField();
        txtUserId.setPromptText("Enter UserId");
        txtUserId.setLayoutX(225);
        txtUserId.setLayoutY(435);
        txtFlightDate.setPrefSize(200, 25);

        Button btnBackToMain = new Button("Back To Main");
        btnBackToMain.setFont(Font.font("Serif", 12));
        btnBackToMain.setLayoutX(25);
        btnBackToMain.setLayoutY(550);
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

        Button btnBook = new Button("Book");
        btnBook.setFont(Font.font("Serif", 12));
        btnBook.setLayoutX(250);
        btnBook.setLayoutY(470);
        btnBook.setPrefSize(100, 25);

        btnBook.setOnAction(e -> {
                    String selectedFlight = cmbFlightOptions.getValue();
                    String userIdInput = txtUserId.getText();

                    if (selectedFlight == null || selectedFlight.isEmpty()) {
                        txtSearchResults.setText("Please select a flight to book.");
                        return;
                    }

                    try {
                        int userId = Integer.parseInt(userIdInput);

                        String flightNumber = selectedFlight.split(" ")[1];
                        int flightId = getFlightIdByFlightNumber(flightNumber);

                        if (flightId == -1) {
                            txtSearchResults.setText("Selected flight does not exist.");
                            return;
                        }

                        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                            String getFlightTimesQuery = "SELECT departure_time, arrival_time FROM flights WHERE id = ?";
                            String newDepartureTime = null;
                            String newArrivalTime = null;

                            try (PreparedStatement timeStatement = connection.prepareStatement(getFlightTimesQuery)) {
                                timeStatement.setInt(1, flightId);
                                ResultSet timeResult = timeStatement.executeQuery();
                                if (timeResult.next()) {
                                    newDepartureTime = timeResult.getString("departure_time");
                                    newArrivalTime = timeResult.getString("arrival_time");
                                } else {
                                    txtSearchResults.setText("Error retrieving flight times.");
                                    return;
                                }
                            }

                            if (isConflict(userId, newDepartureTime, newArrivalTime)) {
                                txtSearchResults.setText("Time conflict detected with another booking. " +
                                        "Please select a different flight.");
                                return;
                            }

                            String insertQuery = "INSERT INTO reservations (user_id, flight_id, flight_number) VALUES (?, ?, ?)";
                            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                                insertStatement.setInt(1, userId);
                                insertStatement.setInt(2, flightId);
                                insertStatement.setString(3, flightNumber);

                                insertStatement.executeUpdate();
                                txtSearchResults.setText("Flight booked successfully!");
                            }
                        }
                    } catch (NumberFormatException ex) {
                        txtSearchResults.setText("Invalid UserID. Please enter a valid number.");
                    } catch (Exception ex) {
                        txtSearchResults.setText("Error booking flight: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                });

        btnSearch.setOnAction(event -> {
            String departureCity = txtDepartureCity.getText();
            String destinationCity = txtDestinationCity.getText();
            String flightDate = txtFlightDate.getText();

            if (departureCity.isEmpty() || destinationCity.isEmpty() || flightDate.isEmpty()) {
                txtSearchResults.setText("Please fill in all fields to search.");
                return;
            }

            StringBuilder results = new StringBuilder();

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                String query = "SELECT * FROM flights WHERE departure_city = ? AND destination_city = ? AND flight_date = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, departureCity);
                preparedStatement.setString(2, destinationCity);
                preparedStatement.setString(3, flightDate);

                ResultSet resultSet = preparedStatement.executeQuery();
                cmbFlightOptions.getItems().clear();

                while (resultSet.next()) {
                    String flightInfo = String.format("%s %s (%s - %s)",
                            resultSet.getString("airline"),
                            resultSet.getString("flight_number"),
                            resultSet.getString("departure_time"),
                            resultSet.getString("arrival_time"));
                    results.append(flightInfo).append("\n");
                    cmbFlightOptions.getItems().add(flightInfo);
                }

                if (results.isEmpty()) {
                    txtSearchResults.setText("No flights found matching your criteria.");
                } else {
                    txtSearchResults.setText(results.toString());
                }
            } catch (Exception e) {
                txtSearchResults.setText("Error connecting to the database: " + e.getMessage());
                e.printStackTrace();
            }
        });

        root.getChildren().addAll(lblTitle, lblSubtitle, txtDepartureCity, txtDestinationCity,
                btnSearch, txtFlightDate, txtSearchResults, cmbFlightOptions, txtUserId, btnBook, btnBackToMain);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Booking Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to fetch the flight_id by flight_number
    private int getFlightIdByFlightNumber(String flightNumber) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT id FROM flights WHERE flight_number = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, flightNumber);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;  // Return -1 if flight is not found
    }

    private boolean isConflict(int userId, String newDepartureTime, String newArrivalTime) {
        boolean conflictExists = false;

        try (Connection connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD)) {
            String query = "SELECT * FROM reservations r " +
                    "JOIN flights f ON r.flight_id = f.id " + "WHERE r.user_id = ? " +
                    "AND ((f.departure_time < ? AND f.arrival_time > ?) " +
                    "OR (f.departure_time < ? AND f.arrival_time > ?))";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, newArrivalTime);
                preparedStatement.setString(3, newDepartureTime);
                preparedStatement.setString(4, newDepartureTime);
                preparedStatement.setString(5, newArrivalTime);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    conflictExists = true;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Database connection error.");
        }
        return conflictExists;
    }
}

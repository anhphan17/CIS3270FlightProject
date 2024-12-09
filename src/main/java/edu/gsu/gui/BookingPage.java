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
        txtDepartureCity.setPrefSize(200,25);

        TextField txtDestinationCity = new TextField();
        txtDestinationCity.setPromptText("Destination City");
        txtDestinationCity.setLayoutX(200);
        txtDestinationCity.setLayoutY(185);
        txtDestinationCity.setPrefSize(200,25);

        TextField txtFlightDate = new TextField();
        txtFlightDate.setPromptText("Flight Date (YYYY-MM-DD)");
        txtFlightDate.setLayoutX(200);
        txtFlightDate.setLayoutY(220);
        txtFlightDate.setPrefSize(200,25);

        Button btnSearch = new Button("Search");
        btnSearch.setFont(Font.font("Serif", 12));
        btnSearch.setLayoutX(250);
        btnSearch.setLayoutY(255);
        btnSearch.setPrefSize(100,25);

        TextArea txtSearchResults = new TextArea();
        txtSearchResults.setText("Search Results");
        txtSearchResults.setLayoutX(100);
        txtSearchResults.setLayoutY(290);
        txtSearchResults.setPrefSize(400,100);
        txtSearchResults.setEditable(false);

        ComboBox<String> cmbFlightOptions = new ComboBox<>();
        cmbFlightOptions.setPromptText("Select a Flight");
        cmbFlightOptions.setLayoutX(200);
        cmbFlightOptions.setLayoutY(410);
        cmbFlightOptions.setPrefSize(200,25);

        Button btnBook = new Button("Book");
        btnBook.setFont(Font.font("Serif", 12));
        btnBook.setLayoutX(250);
        btnBook.setLayoutY(450);
        btnBook.setPrefSize(100,25);


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

            if (results.length() == 0) {
                txtSearchResults.setText("No flights found matching your criteria.");
            } else {
                txtSearchResults.setText(results.toString());
            }
        }
        catch (Exception e) {
                txtSearchResults.setText("Error connecting to the database: " + e.getMessage());
                e.printStackTrace();
        }
        });


        root.getChildren().addAll(lblTitle, lblSubtitle, txtDepartureCity, txtDestinationCity,
                btnSearch, txtFlightDate, txtSearchResults, cmbFlightOptions, btnBook);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Booking Page");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}

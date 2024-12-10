package edu.gsu.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Trips extends Application{
    private static final String DB_URL = "jdbc:mysql://cis3270flightproject.mysql.database.azure.com:3306/project3270";
    private static final String DB_USERNAME = "aphan17";
    private static final String DB_PASSWORD = "Mendes1998!";
    private int userId;

    /*public Trips() {
        this.userId = -1; // Default no value
    }
    public Trips(int userId) {
        this.userId = userId;
    }*/
    public static void main(String[] args) {
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

        Label lblSubtitle = new Label("Up coming flights");
        lblSubtitle.setFont(Font.font("Serif", 25));
        lblSubtitle.setTextFill(javafx.scene.paint.Color.web("#fffb27"));
        lblSubtitle.setLayoutX(175);
        lblSubtitle.setLayoutY(80);

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
            lstBookedFlights.getItems().remove(selectedFlight);
            showAlert("Success", "Booking cancelled successfully.");
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
                new BookingPage().start(bookingPageStage); // Use default constructor
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
                new BookingPage().start(bookingPageStage); // Use default constructor
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
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


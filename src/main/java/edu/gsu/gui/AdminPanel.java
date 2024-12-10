package edu.gsu.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.logging.Logger;
import java.util.logging.Level;

public class AdminPanel extends Application {
    private static final Logger logger = Logger.getLogger(AdminPanel.class.getName());
    @Override
    public void start(Stage primaryStage){
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 400);
        root.setStyle("-fx-background-color: #89CFF0;");

        Label lblTitle = new Label("MIA Flights");
        lblTitle.setFont(Font.font("Serif", 50));
        lblTitle.setTextFill(javafx.scene.paint.Color.web("#fffb27"));
        lblTitle.setLayoutX(175);
        lblTitle.setLayoutY(10);

        Button btnAddFlight = new Button("Add Flight");
        btnAddFlight.setLayoutX(200);
        btnAddFlight.setLayoutY(100);
        btnAddFlight.setPrefSize(200, 25);

        Button btnUpdateFlight = new Button("Update Flight");
        btnUpdateFlight.setLayoutX(200);
        btnUpdateFlight.setLayoutY(150);
        btnUpdateFlight.setPrefSize(200, 25);

        btnUpdateFlight.setOnAction(event -> {
            try {
                primaryStage.close();
                new AdminUpdateFlight().start(new Stage());
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error navigating to Admin Update Flight Page", e);
            }
        });

        Button btnDeleteFlight = new Button("Delete Flight");
        btnDeleteFlight.setLayoutX(200);
        btnDeleteFlight.setLayoutY(200);
        btnDeleteFlight.setPrefSize(200, 25);

        Button btnViewReservations = new Button("View Reservations");
        btnViewReservations.setLayoutX(200);
        btnViewReservations.setLayoutY(250);
        btnViewReservations.setPrefSize(200, 25);

        btnViewReservations.setOnAction(e -> {
            try {
                primaryStage.close();
                new AdminViewFlights().start(new Stage());
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error navigating to Admin Update Flight Page", e);
            }
        });


        Button btnAdminLogout = new Button("Log Out");
        btnAdminLogout.setFont(Font.font("Serif", 12));
        btnAdminLogout.setLayoutX(25);
        btnAdminLogout.setLayoutY(350);
        btnAdminLogout.setPrefSize(100, 25);

        btnAdminLogout.setOnAction(e -> {
            try {
                primaryStage.close();
                Stage adminLogOut = new Stage();
                new AdminLoginPage().start(primaryStage);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button btnBackToMenu = new Button("Back to Main Menu");
        btnBackToMenu.setLayoutX(200);
        btnBackToMenu.setLayoutY(300);
        btnBackToMenu.setPrefSize(200, 25);

        btnAddFlight.setOnAction(event -> {
            try {
                primaryStage.close();
                Stage adminAddFlight = new Stage();
                new AddFlight().start(adminAddFlight);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });





        btnDeleteFlight.setOnAction(event -> {
            try {
                primaryStage.close();
                new AdminDeleteFlight().start(new Stage());
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error navigating to Delete Flight Page", e);
            }
        });

        // Navigate to View Reservations Page
        btnViewReservations.setOnAction(event -> {
        });

        btnBackToMenu.setOnAction(event -> {
            MainPage mainPage = new MainPage();
            try {
                mainPage.start(primaryStage);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error navigating back to Main Page", e);
            }
        });

        // Add components to the root pane

        root.getChildren().addAll(lblTitle, btnAddFlight, btnUpdateFlight, btnDeleteFlight, btnViewReservations,
                btnBackToMenu, btnAdminLogout);

        // Set up the scene and stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Admin Panel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

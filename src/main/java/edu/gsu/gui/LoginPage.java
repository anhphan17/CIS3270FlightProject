package edu.gsu.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class LoginPage extends Application {

    @FXML
    private TextField txtUsername;

    @FXML private TextField txtPassword;

    @FXML
    private Button btnLoginAction;

    @Override
    /* public void start(Stage primaryStage) {
        Button btLogin = new Button("Login");
        Scene scene = new Scene(btLogin, 300, 200);
        primaryStage.setTitle("LoginPage");
        primaryStage.setScene(scene);
        primaryStage.show();
    } */
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Menu.class.getResource("/edu/gsu/gui/LoginPage2.fxml"));
            AnchorPane pane = loader.load();

            BorderPane root = new BorderPane();
            root.setCenter(pane);

            Scene scene = new Scene(root,600,420);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Mia Airlines");
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLoginAction(){
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Oops! Look's like you didn't enter both a username and password! Please enter both.");
        }
        else {
            System.out.println("Login button clicked! Username: " + username + ", Password: " + password);
        }
    }

        public static void main(String[] args) {
            launch(args); // Launch the JavaFX application
        }

}

package edu.gsu.db;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;


public class DBQueries extends Application {

    private static final String DB_URL = "jdbc:mysql://cis3270flightproject.mysql.database.azure.com:3306/project3270";
    private static final String DB_USERNAME = "aphan17";
    private static final String DB_PASSWORD = "Mendes1998!";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TextField queryInput = new TextField();
        queryInput.setPromptText("Enter SQL Query (SELECT, UPDATE, DELETE)");

        TextArea queryResults = new TextArea();
        queryResults.setEditable(false);

        Button executeButton = new Button("Execute");

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Enter First Name");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Enter Last Name");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter email");

        Button insertButton = new Button("Insert Customer");

        executeButton.setOnAction(event -> {
            String query  = queryInput.getText();
            queryResults.clear();
            if (query == null || query.trim().isEmpty()) {
                queryResults.setText("Query cannot be empty.");
            }
            else if (validateQuery(query)) {
                executeQuery(query, queryResults);
            }
            else {
                queryResults.setText("Invalid query. Only SELECT, UPDATE,and DELETE are allowed.");
            }
        });

        insertButton.setOnAction(event -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                queryResults.setText("Please fill in all fields.");
            }
            else {
                insertCustomer(firstName, lastName, email, queryResults);
            }
        });

        VBox layout = new VBox(10, new Label("SQL Query Input:"), queryInput, executeButton,
                new Label("Query Results: "), queryResults,
                new Label("Insert Customer: "), firstNameField, lastNameField, emailField, insertButton);
        layout.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(layout, 600, 400);

        primaryStage.setTitle("Database Query UI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean validateQuery(String query) {
        return query != null && query.trim().toUpperCase().matches("^(SELECT|UPDATE|DELETE).*");
    }

    private void executeQuery(String query, TextArea queryResults) {
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                 Statement statement = connection.createStatement()) {

                if (query.trim().toUpperCase().startsWith("SELECT")) {
                    ResultSet resultSet = statement.executeQuery(query);
                    StringBuilder resultBuilder = new StringBuilder();

                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    while (resultSet.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            resultBuilder.append(resultSet.getString(i)).append("\t");
                        }
                        resultBuilder.append("\n");
                    }

                    queryResults.setText(resultBuilder.toString());
                }
                else {
                    int rowsAffected = statement.executeUpdate(query);
                    queryResults.setText(rowsAffected + " row(s) affected.");
                }
            }
        catch (SQLException e) {
                queryResults.setText("Error: " + e.getMessage());
                e.printStackTrace();
        }
    }

    private void insertCustomer(String firstName, String lastName, String email, TextArea queryResults) {
        String insertQuery = "INSERT INTO customer (first_name, last_name, email) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);

            int rowsAffected = preparedStatement.executeUpdate();
            queryResults.setText(rowsAffected + " customer(s) inserted successfully.");

        }

        catch (SQLException e) {
            queryResults.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

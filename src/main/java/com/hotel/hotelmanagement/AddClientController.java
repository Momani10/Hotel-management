package com.hotel.hotelmanagement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.hotel.hotelmanagement.ClientController.clientList;
import static com.hotel.hotelmanagement.ClientController.clients;

public class AddClientController implements Initializable {

    @FXML
    private Button ad;

    @FXML
    private TextField fullName;

    @FXML
    private TextField email;

    @FXML
    private TextField phoneNumber;

    private Connection connection;

    private DBConnection dbConnection;

    private PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
    }

    public void handleAddAction(javafx.event.ActionEvent actionEvent) {
        String query = "INSERT INTO clients (FullName, Email, PhoneNumber) VALUES (?,?,?)";
        try {
            pst = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, fullName.getText());
            pst.setString(2, email.getText());
            pst.setString(3, phoneNumber.getText());
            pst.executeUpdate();

            // Retrieve the generated ClientID
            int clientID = -1;
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                clientID = generatedKeys.getInt(1);
            }

            // Create a new Client instance and add it to the lists
            clientList.add(new Client(clientID, fullName.getText(), email.getText(), phoneNumber.getText()));
            clients.add(new Client(clientID, fullName.getText(), email.getText(), phoneNumber.getText()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

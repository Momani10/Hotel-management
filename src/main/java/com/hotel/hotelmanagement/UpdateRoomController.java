package com.hotel.hotelmanagement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateRoomController implements Initializable {

    @FXML
    private TextField number;

    @FXML
    private TextField type;

    @FXML
    private TextField price;

    @FXML
    private Button update;

    private Connection connection;
    private DBConnection dbConnection;
    private PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
    }

    @FXML
    public void handleUpdateAction(javafx.event.ActionEvent actionEvent) {
        // Validate input fields
        if (!validateInput()) {
            return;
        }

        String roomNumber = number.getText();
        String roomType = type.getText();
        String roomPrice = price.getText();

        // Assuming your update query would look like this
        String query = "UPDATE rooms SET roomType=?, price=? WHERE roomNumber=?";

        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, roomType);
            pst.setString(2, roomPrice);
            pst.setString(3, roomNumber);

            int updatedRows = pst.executeUpdate();

            if (updatedRows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Room Updated", "Room information successfully updated.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Update Error", "Failed to update room. Please check the room information.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "SQL Error", "Error occurred while updating the room information.");
        }
    }

    private boolean validateInput() {
        String roomNumber = number.getText();
        String roomType = type.getText();
        String roomPrice = price.getText();

        if (roomNumber.isEmpty() || roomType.isEmpty() || roomPrice.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill in all the fields.");
            return false;
        }

        // Additional validation if needed

        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

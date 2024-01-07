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

import static com.hotel.hotelmanagement.RoomController.roomList;
import static com.hotel.hotelmanagement.RoomController.rooms;

public class DeleteRoomController implements Initializable {

    @FXML
    private Button delete;

    @FXML
    private TextField number;

    private Connection connection;
    private DBConnection dbConnection;
    private PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
    }

    public void handleSupAction(javafx.event.ActionEvent actionEvent) {
        String roomNumber = number.getText();
        String query = "DELETE FROM rooms WHERE roomNumber = ?";

        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, roomNumber);

            int deletedRows = pst.executeUpdate();

            if (deletedRows > 0) {
                // Remove the room from the lists if it was successfully deleted from the database
                roomList.removeIf(room -> room.getNumber() == Integer.parseInt(roomNumber));
                rooms.removeIf(room -> room.getNumber() == Integer.parseInt(roomNumber));

                showAlert(Alert.AlertType.INFORMATION, "Room Deleted", "Room successfully deleted.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Delete Error", "Failed to delete room. Please check the room number.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "SQL Error", "Error occurred while deleting the room.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

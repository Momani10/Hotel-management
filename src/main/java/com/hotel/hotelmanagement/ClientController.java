package com.hotel.hotelmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientController implements Initializable {


    @FXML
    private TableColumn<Client, String> ClientID;
    
    @FXML
    private TableColumn<Client, String> FullName;

    @FXML
    private TableView<Client> clientTable;

    @FXML
    private TableColumn<Client, String> Email;

    @FXML
    private TextField search;

    @FXML
    private TableColumn<Client, String> PhoneNumber;

    private Connection connection;

    private DBConnection dbConnection;

    private PreparedStatement pst;

    public static final ObservableList<Client> clients = FXCollections.observableArrayList();

    public static final List<Client> clientList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
        // Corrected column names
        ClientID.setCellValueFactory(new PropertyValueFactory<>("ClientID"));
        FullName.setCellValueFactory(new PropertyValueFactory<>("FullName"));
        PhoneNumber.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        try {
            initclientList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientTable.setItems(clients);
    }

    public void handleAddAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage add = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("addroom.fxml"));
        Scene scene = new Scene(root);
        add.setScene(scene);
        add.show();
    }
    public void handleDeleteAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage delete = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("deleteroom.fxml"));
        Scene scene = new Scene(root);
        delete.setScene(scene);
        delete.show();
    }
    public void handleUpdateAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage update = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("updateroom.fxml"));
        Scene scene = new Scene(root);
        update.setScene(scene);
        update.show();
    }

    public void handleViewAction(javafx.event.ActionEvent actionEvent) throws IOException {

    }

    public void clickItem(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            
        }
    }

    public void initclientList() throws IOException {
        clientList.clear();
        clients.clear();
        String query = "SELECT * FROM clients";
        try {
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String PhoneNumber = rs.getString("PhoneNumber");
                String FullName = rs.getString("FullName");
                String Email = rs.getString("Email");
                int ClientID = Integer.parseInt(rs.getString("ClientID"));
                clientList.add(new Client(ClientID ,FullName ,Email, PhoneNumber));
                clients.add(new Client(ClientID ,FullName ,Email, PhoneNumber));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Search(ObservableList<Client> clients, String s) {
        clients.clear();
        for (int i = 0; i < clientList.size(); i++) {
            if (Integer.toString(clientList.get(i).getClientID()).indexOf(s) == 0) {
                clients.add(clientList.get(i));
            }
        }
    }

    public void handleSearchKey(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            String s = search.getText();
            Search(clients, s);
        }
    }
}

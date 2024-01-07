// EmployeeController.java
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

public class EmployeeController implements Initializable {

    @FXML
    private TableColumn<Employee, Integer> EmployeeID;

    @FXML
    private TableColumn<Employee, String> FullName;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> Department;

    @FXML
    private TableColumn<Employee, String> Position;

    @FXML
    private TableColumn<Employee, Double> Salary;

    @FXML
    private TextField search;

    private Connection connection;

    private DBConnection dbConnection;

    private PreparedStatement pst;

    public static final ObservableList<Employee> employees = FXCollections.observableArrayList();

    public static final List<Employee> employeeList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
        EmployeeID.setCellValueFactory(new PropertyValueFactory<>("EmployeeID"));
        FullName.setCellValueFactory(new PropertyValueFactory<>("FullName"));
        Department.setCellValueFactory(new PropertyValueFactory<>("Department"));
        Position.setCellValueFactory(new PropertyValueFactory<>("Position"));
        Salary.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        try {
            initEmployeeList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        employeeTable.setItems(employees);
    }

    public void handleAddAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage add = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("addemployee.fxml"));
        Scene scene = new Scene(root);
        add.setScene(scene);
        add.show();
    }

    public void handleDeleteAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage delete = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("deleteemployee.fxml"));
        Scene scene = new Scene(root);
        delete.setScene(scene);
        delete.show();
    }

    public void handleUpdateAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage update = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("updateemployee.fxml"));
        Scene scene = new Scene(root);
        update.setScene(scene);
        update.show();
    }

    public void clickItem(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            if (employeeTable.getSelectionModel().getSelectedItem() != null) {
                // You can perform actions when a row is double-clicked
            }
        }
    }

    public void initEmployeeList() throws IOException {
        employeeList.clear();
        employees.clear();
        String query = "SELECT * FROM employee";
        try {
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int EmployeeID = rs.getInt("EmployeeID");
                String FullName = rs.getString("FullName");
                String Department = rs.getString("Department");
                String Position = rs.getString("Position");
                double Salary = rs.getDouble("Salary");
                employeeList.add(new Employee(EmployeeID, FullName, Department, Position, Salary));
                employees.add(new Employee(EmployeeID, FullName, Department, Position, Salary));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Search(ObservableList<Employee> employees, String s) {
        employees.clear();
        for (int i = 0; i < employeeList.size(); i++) {
            if (Integer.toString(employeeList.get(i).getEmployeeID()).indexOf(s) == 0) {
                employees.add(employeeList.get(i));
            }
        }
    }

    public void handleSearchKey(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            String s = search.getText();
            Search(employees, s);
        }
    }
}

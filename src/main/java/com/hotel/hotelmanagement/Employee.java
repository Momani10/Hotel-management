// Employee.java
package com.hotel.hotelmanagement;

public class Employee {
    private int employeeID;
    private String fullName;
    private String department;
    private String position;
    private double salary;

    public Employee(int employeeID, String fullName, String department, String position, double salary) {
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.department = department;
        this.position = position;
        this.salary = salary;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}

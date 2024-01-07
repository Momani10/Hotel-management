package com.hotel.hotelmanagement;

public class Client {
    private int ClientID;

    private String FullName;

    private String Email;

    private String PhoneNumber;

    public Client(int ClientID, String FullName, String Email, String PhoneNumber) {
        this.ClientID = ClientID;
        this.FullName = FullName;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
    }

    public int getClientID() {
        return ClientID;
    }

    public String getFullName() {
        return FullName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setClientID(int ClientID) {
        this.ClientID = ClientID;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }
}

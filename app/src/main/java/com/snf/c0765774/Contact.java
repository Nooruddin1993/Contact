package com.snf.c0765774;

public class Contact {

    int id,phonenumber;
    String firstname, lastname, address;

    public Contact(int id, String firstname, String lastname, int phonenumber, String address) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public String getAddress() {
        return address;
    }
}

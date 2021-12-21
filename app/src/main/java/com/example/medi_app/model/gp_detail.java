package com.example.medi_app.model;

public class gp_detail {
    private String uuid;
    private String Name;
    private String Office_address;
    private String Contact_num;
    private String Email;

    public gp_detail() {
    }

    public gp_detail(String uuid, String Name, String Office_address, String Contact_num, String Email) {
        this.uuid = uuid;
        this.Name = Name;
        this.Office_address = Office_address;
        this.Contact_num = Contact_num;
        this.Email = Email;

    }

    public String getuuid() {
        return uuid;
    }

    public void setuuid(String uuid) {
        this.uuid = uuid;
    }

    public String get_Name() {
        return Name;
    }

    public void set_Name(String Name) {
        this.Name = Name;
    }

    public String getOffice_address() {
        return Office_address;
    }

    public void setOffice_address(String Office_address) {
        this.Office_address = Office_address;
    }

    public String getContact_num() {
        return Contact_num;
    }

    public void setContact_num(String Contact_num) {
        this.Contact_num = Contact_num;
    }

    public String get_Email() {
        return Email;
    }

    public void set_Email(String Email) {
        this.Email = Email;
    }
}



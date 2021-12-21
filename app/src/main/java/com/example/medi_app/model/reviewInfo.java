package com.example.medi_app.model;

public class reviewInfo {
    private String firstName;
    private String Medi_coin_rating;
    private String Medi_predict_rating;
    private String email;
    private String otherfeedback;
    public reviewInfo() {
    }

    public reviewInfo(String firstName,String email, String Medi_coin_rating, String Medi_predict_rating,String otherfeedback) {
        this.firstName = firstName;
        this.Medi_coin_rating = Medi_coin_rating;
        this.Medi_predict_rating = Medi_predict_rating;
        this.email = email;
        this.otherfeedback = otherfeedback;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMedi_coin_rating() {
        return Medi_coin_rating;
    }

    public void setMedi_coin_rating(String medi_coin_rating) {
        this.Medi_coin_rating = medi_coin_rating;
    }

    public String getMedi_predict_rating() {
        return Medi_predict_rating;
    }

    public void setMedi_predict_rating(String medi_predict_rating) {
        this.Medi_predict_rating = medi_predict_rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtherfeedback() {
        return otherfeedback;
    }

    public void setOtherfeedback(String otherfeedback) {
        this.otherfeedback = otherfeedback;
    }
}

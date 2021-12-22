package com.example.medi_app.model;

public class appointmentDetail {
    private String GP;
    private String appointment_date;
    private String appointment_time;
    private String visit_type;
    private String notes;

    public appointmentDetail(String GP,String appointment_date,String appointment_time,String visit_type,String notes){
        this.GP = GP;
        this.appointment_date = appointment_date;
        this.appointment_time = appointment_time;
        this.visit_type = visit_type;
        this.notes = notes;
    }

    public String getGP() {
        return GP;
    }

    public void setGP(String GP) {
        this.GP = GP;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }

    public String getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
    }

    public String getVisit_type() {
        return visit_type;
    }

    public void setVisit_type(String visit_type) {
        this.visit_type = visit_type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

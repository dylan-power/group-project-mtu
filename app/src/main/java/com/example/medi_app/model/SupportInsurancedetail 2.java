package com.example.medi_app.model;

public class SupportInsurancedetail {// class for storing Support querys to Insurance companys's to firebase
    private String Query;
    private String Query_regarding;
    private String Query_Details;

    public SupportInsurancedetail(String Query, String Query_regarding, String Query_Details){
        this.Query = Query;
        this.Query_regarding = Query_regarding;
        this.Query_Details = Query_Details;


    }


    public String getQuery() {
        return Query;
    }

    public void setQuery(String query) {
        this.Query = query;
    }

    public String getQuery_regarding() {
        return Query_regarding;
    }

    public String getQuery_Details() {
        return Query_Details;
    }

    public void setQuery_Details(String query_Details) {
        this.Query_Details = query_Details;
    }
}

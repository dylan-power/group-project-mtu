package com.example.medi_app.model;

public class insuranceDetail {
    private String Insurance_company_On_File;
    private String Policy_Number;
    private String Policy_Start;
    private String Policy_End;
    private int contact_number;
    private String email;

    public insuranceDetail() {
    }

    public insuranceDetail(String Insurance_company_On_File, String Policy_Number, String Policy_Start, String Policy_End,int contact_number,String email) {
        this.Insurance_company_On_File = Insurance_company_On_File;
        this.Policy_Number = Policy_Number;
        this.Policy_Start = Policy_Start;
        this.Policy_End = Policy_End;
        this.contact_number = contact_number;
        this.email = email;

    }

    public String getInsurance_company_On_File() {
        return Insurance_company_On_File;
    }

    public void setInsurance_company_On_File(String insurance_company_On_File) {
        this.Insurance_company_On_File = insurance_company_On_File;
    }

    public String getPolicy_Number() {
        return Policy_Number;
    }

    public void setPolicy_Number(String policy_Number) {
        this.Policy_Number = policy_Number;
    }

    public String getPolicy_Start() {
        return Policy_Start;
    }

    public void setPolicy_Start(String policy_Start) {
        this.Policy_Start = policy_Start;
    }

    public String getPolicy_End() {
        return Policy_End;
    }

    public void setPolicy_End(String policy_End) {
        this.Policy_End = policy_End;
    }

    public int getcontact_number() {
        return contact_number;
    }

    public void setcontact_number(int contact_number) {
        this.contact_number = contact_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}



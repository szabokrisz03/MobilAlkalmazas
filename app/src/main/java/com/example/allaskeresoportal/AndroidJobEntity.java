package com.example.allaskeresoportal;

public class AndroidJobEntity {
    private String name;
    private String createdBy;
    private String createdDate;
    private int payment;

    public AndroidJobEntity() {
    }

    public AndroidJobEntity(String name, String createdBy, String createdDate, int payment) {
        this.name = name;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.payment = payment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }
}

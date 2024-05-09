package com.example.allaskeresoportal;

public class AndroidJobEntity {
    private String name;
    private String createdBy;
    private String createdDate;
    private int payment;
    private String shortDesc;
    private String longDesc;

    public AndroidJobEntity() {
    }

    public AndroidJobEntity(String name, String createdBy, String createdDate, int payment, String shortDesc, String longDesc) {
        this.name = name;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.payment = payment;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public String getName() {
        return name;
    }


    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }


    public int getPayment() {
        return payment;
    }

}

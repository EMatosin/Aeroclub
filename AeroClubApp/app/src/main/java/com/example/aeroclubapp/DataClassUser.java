package com.example.aeroclubapp;

public class DataClassUser {

    private String dataTitle;
    private String dataDesc;
    private String dataLang;
    private String dataImage;
    private String fuelType;
    private String quantity;


    public String getFuelType() {
        return fuelType;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataLang() {
        return dataLang;
    }

    public String getDataImage() {
        return dataImage;
    }

    public DataClassUser(String dataTitle, String dataDesc, String dataLang, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataLang = dataLang;
        this.dataImage = dataImage;
    }

    public DataClassUser(String fuelType, String quantity) {
        this.fuelType = fuelType;
        this.quantity = quantity;
    }
}
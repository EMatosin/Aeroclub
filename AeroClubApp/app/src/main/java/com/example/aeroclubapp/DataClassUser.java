package com.example.aeroclubapp;

public class DataClassUser {

    private String dataTitle;
    private String dataBirth;
    private String dataPseudo;
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

    public String getDataBirth() {
        return dataBirth;
    }

    public String getDataPseudo() {
        return dataPseudo;
    }

    public String getDataImage() {
        return dataImage;
    }

    public DataClassUser(String dataTitle, String dataBirth, String dataPseudo, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataBirth = dataBirth;
        this.dataPseudo = dataPseudo;
        this.dataImage = dataImage;
    }

    public DataClassUser(String fuelType, String quantity) {
        this.fuelType = fuelType;
        this.quantity = quantity;
    }
}
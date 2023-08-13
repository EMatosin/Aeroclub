package com.example.aeroclubapp;

public class DataClassUser {

    private String dataName, dataBirth, dataEmail, dataImage;
    private String fuelType, quantity;
    private String typeAvion, periode, groupeAcoustique, heureAtterrissage, categorieAvion, surfaceSol;

    private String umlDate;

    public String getDataName() {
        return dataName;
    }

    public String getDataBirth() {
        return dataBirth;
    }

    public String getDataEmail() {
        return dataEmail;
    }

    public String getDataImage() {
        return dataImage;
    }

    public String getTypeAvion() {
        return typeAvion;
    }

    public String getPeriode() {
        return periode;
    }

    public String getGroupeAcoustique() {
        return groupeAcoustique;
    }

    public String getHeureAtterrissage() {
        return heureAtterrissage;
    }

    public String getCategorieAvion() {
        return categorieAvion;
    }

    public String getSurfaceSol() {
        return surfaceSol;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUmlDate() {
        return umlDate;
    }

    public DataClassUser() {
    }

    public DataClassUser(String dataName, String dataBirth, String dataEmail, String dataImage) {
        this.dataName = dataName;
        this.dataBirth = dataBirth;
        this.dataEmail = dataEmail;
        this.dataImage = dataImage;
    }

    public DataClassUser(String fuelType, String quantity) {
        this.fuelType = fuelType;
        this.quantity = quantity;
    }

    public DataClassUser(String typeAvion, String periode, String groupeAcoustique, String heureAtterrissage, String categorieAvion, String surfaceSol) {
        this.typeAvion = typeAvion;
        this.periode = periode;
        this.groupeAcoustique = groupeAcoustique;
        this.heureAtterrissage = heureAtterrissage;
        this.categorieAvion = categorieAvion;
        this.surfaceSol = surfaceSol;
    }

    public DataClassUser(String umlDate) {
        this.umlDate = umlDate;
    }
}
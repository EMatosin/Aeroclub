package com.example.aeroclubapp;

public class DataClassUser {

    private String dataTitle, dataBirth, dataPseudo, dataImage;
    private String fuelType, quantity;

    private String typeAvion, periode, groupeAcoustique, heureAtterrissage, categorieAvion, surfaceSol;

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

    public DataClassUser(String typeAvion, String periode, String groupeAcoustique, String heureAtterrissage, String categorieAvion, String surfaceSol) {
        this.typeAvion = typeAvion;
        this.periode = periode;
        this.groupeAcoustique = groupeAcoustique;
        this.heureAtterrissage = heureAtterrissage;
        this.categorieAvion = categorieAvion;
        this.surfaceSol = surfaceSol;
    }
}
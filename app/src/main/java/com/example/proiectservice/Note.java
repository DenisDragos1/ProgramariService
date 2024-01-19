package com.example.proiectservice;

public class Note {
    String key, numeClient, masina, constatare, dataProgramare;
    double pret;

    public Note() {

    }

    public String getNumeClient() {
        return numeClient;
    }

    public String getMasina() {
        return masina;
    }

    public String getConstatare() {
        return constatare;
    }

    public String getDataProgramare() {
        return dataProgramare;
    }

    public double getPret() {
        return pret;
    }

    public String getKey() {
        return key;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public void setMasina(String masina) {
        this.masina = masina;
    }

    public void setConstatare(String constatare) {
        this.constatare = constatare;
    }

    public void setDataProgramare(String dataProgramare) {
        this.dataProgramare = dataProgramare;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

package com.example.kidsdaycareactivitycenter.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TransaksiDailyActivity {

    private final StringProperty idDailyActivity;
    private final StringProperty idDaycare;
    private final StringProperty idActivity;
    private final StringProperty idKaryawan;
    private final StringProperty tanggal;
    private final StringProperty jamCheckIn;
    private final StringProperty jamCheckOut;
    private final StringProperty jamSnackPagi;
    private final StringProperty jamMakanSiang;
    private final StringProperty jamSnackSore;
    private final StringProperty jamMandi;
    private final StringProperty jamTidur;
    private final StringProperty aktifitasHarian;
    private final StringProperty catatanCaregiver;

    public TransaksiDailyActivity() {
        this.idDailyActivity = new SimpleStringProperty();
        this.idDaycare = new SimpleStringProperty();
        this.idActivity = new SimpleStringProperty();
        this.idKaryawan = new SimpleStringProperty();
        this.tanggal = new SimpleStringProperty();
        this.jamCheckIn = new SimpleStringProperty();
        this.jamCheckOut = new SimpleStringProperty();
        this.jamSnackPagi = new SimpleStringProperty();
        this.jamMakanSiang = new SimpleStringProperty();
        this.jamSnackSore = new SimpleStringProperty();
        this.jamMandi = new SimpleStringProperty();
        this.jamTidur = new SimpleStringProperty();
        this.aktifitasHarian = new SimpleStringProperty();
        this.catatanCaregiver = new SimpleStringProperty();
    }

    public TransaksiDailyActivity(
            String idDailyActivity,
            String idDaycare,
            String idActivity,
            String idKaryawan,
            String tanggal,
            String jamCheckIn,
            String jamCheckOut,
            String jamSnackPagi,
            String jamMakanSiang,
            String jamSnackSore,
            String jamMandi,
            String jamTidur,
            String aktifitasHarian,
            String catatanCaregiver) {

        this.idDailyActivity = new SimpleStringProperty(idDailyActivity);
        this.idDaycare = new SimpleStringProperty(idDaycare);
        this.idActivity = new SimpleStringProperty(idActivity);
        this.idKaryawan = new SimpleStringProperty(idKaryawan);
        this.tanggal = new SimpleStringProperty(tanggal);
        this.jamCheckIn = new SimpleStringProperty(jamCheckIn);
        this.jamCheckOut = new SimpleStringProperty(jamCheckOut);
        this.jamSnackPagi = new SimpleStringProperty(jamSnackPagi);
        this.jamMakanSiang = new SimpleStringProperty(jamMakanSiang);
        this.jamSnackSore = new SimpleStringProperty(jamSnackSore);
        this.jamMandi = new SimpleStringProperty(jamMandi);
        this.jamTidur = new SimpleStringProperty(jamTidur);
        this.aktifitasHarian = new SimpleStringProperty(aktifitasHarian);
        this.catatanCaregiver = new SimpleStringProperty(catatanCaregiver);
    }

    public String getIdDailyActivity() {
        return idDailyActivity.get();
    }

    public void setIdDailyActivity(String value) {
        idDailyActivity.set(value);
    }

    public StringProperty idDailyActivityProperty() {
        return idDailyActivity;
    }

    public String getIdDaycare() {
        return idDaycare.get();
    }

    public void setIdDaycare(String value) {
        idDaycare.set(value);
    }

    public StringProperty idDaycareProperty() {
        return idDaycare;
    }

    public String getIdActivity() {
        return idActivity.get();
    }

    public void setIdActivity(String value) {
        idActivity.set(value);
    }

    public StringProperty idActivityProperty() {
        return idActivity;
    }

    public String getIdKaryawan() {
        return idKaryawan.get();
    }

    public void setIdKaryawan(String value) {
        idKaryawan.set(value);
    }

    public StringProperty idKaryawanProperty() {
        return idKaryawan;
    }

    public String getTanggal() {
        return tanggal.get();
    }

    public void setTanggal(String value) {
        tanggal.set(value);
    }

    public StringProperty tanggalProperty() {
        return tanggal;
    }

    public String getJamCheckIn() {
        return jamCheckIn.get();
    }

    public void setJamCheckIn(String value) {
        jamCheckIn.set(value);
    }

    public StringProperty jamCheckInProperty() {
        return jamCheckIn;
    }

    public String getJamCheckOut() {
        return jamCheckOut.get();
    }

    public void setJamCheckOut(String value) {
        jamCheckOut.set(value);
    }

    public StringProperty jamCheckOutProperty() {
        return jamCheckOut;
    }

    public String getJamSnackPagi() {
        return jamSnackPagi.get();
    }

    public void setJamSnackPagi(String value) {
        jamSnackPagi.set(value);
    }

    public StringProperty jamSnackPagiProperty() {
        return jamSnackPagi;
    }

    public String getJamMakanSiang() {
        return jamMakanSiang.get();
    }

    public void setJamMakanSiang(String value) {
        jamMakanSiang.set(value);
    }

    public StringProperty jamMakanSiangProperty() {
        return jamMakanSiang;
    }

    public String getJamSnackSore() {
        return jamSnackSore.get();
    }

    public void setJamSnackSore(String value) {
        jamSnackSore.set(value);
    }

    public StringProperty jamSnackSoreProperty() {
        return jamSnackSore;
    }

    public String getJamMandi() {
        return jamMandi.get();
    }

    public void setJamMandi(String value) {
        jamMandi.set(value);
    }

    public StringProperty jamMandiProperty() {
        return jamMandi;
    }

    public String getJamTidur() {
        return jamTidur.get();
    }

    public void setJamTidur(String value) {
        jamTidur.set(value);
    }

    public StringProperty jamTidurProperty() {
        return jamTidur;
    }

    public String getAktifitasHarian() {
        return aktifitasHarian.get();
    }

    public void setAktifitasHarian(String value) {
        aktifitasHarian.set(value);
    }

    public StringProperty aktifitasHarianProperty() {
        return aktifitasHarian;
    }

    public String getCatatanCaregiver() {
        return catatanCaregiver.get();
    }

    public void setCatatanCaregiver(String value) {
        catatanCaregiver.set(value);
    }

    public StringProperty catatanCaregiverProperty() {
        return catatanCaregiver;
    }
}
package com.example.kidsdaycareactivitycenter.Model;

import java.sql.Date;

public class MasterAnak {

    private String idAnak;
    private String idOrangTua;
    private String namaAnak;
    private Date tanggalLahir;
    private char jenisKelamin;
    private String catatanKhusus;

    public MasterAnak() {
    }

    public MasterAnak(String idAnak, String idOrangTua, String namaAnak,
                      Date tanggalLahir, char jenisKelamin, String catatanKhusus) {
        this.idAnak = idAnak;
        this.idOrangTua = idOrangTua;
        this.namaAnak = namaAnak;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.catatanKhusus = catatanKhusus;
    }

    public String getIdAnak() {
        return idAnak;
    }

    public void setIdAnak(String idAnak) {
        this.idAnak = idAnak;
    }

    public String getIdOrangTua() {
        return idOrangTua;
    }

    public void setIdOrangTua(String idOrangTua) {
        this.idOrangTua = idOrangTua;
    }

    public String getNamaAnak() {
        return namaAnak;
    }

    public void setNamaAnak(String namaAnak) {
        this.namaAnak = namaAnak;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public char getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(char jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getCatatanKhusus() {
        return catatanKhusus;
    }

    public void setCatatanKhusus(String catatanKhusus) {
        this.catatanKhusus = catatanKhusus;
    }
}
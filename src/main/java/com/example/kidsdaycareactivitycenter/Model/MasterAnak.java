package com.example.kidsdaycareactivitycenter.Model;

import java.sql.Date;

public class MasterAnak {

    private String idAnak;
    private String idOrangTua;
    private String idPaket;
    private String namaAnak;
    private Date tanggalLahir;
    private char jenisKelamin;
    private String catatanKhusus;
    private String kategori;
    private String status;

    public MasterAnak() {
    }


    public MasterAnak(
            String idAnak,
            String idOrangTua,
            String idPaket,
            String namaAnak,
            Date tanggalLahir,
            char jenisKelamin,
            String kategori,
            String catatanKhusus,
            String status) {

        this.idAnak = idAnak;
        this.idOrangTua = idOrangTua;
        this.idPaket = idPaket;
        this.namaAnak = namaAnak;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.kategori = kategori;
        this.catatanKhusus = catatanKhusus;
        this.status = status;
    }

    public MasterAnak(String idAnak,
                      String idOrangTua,
                      String namaAnak,
                      Date tanggalLahir,
                      char jenisKelamin,
                      String catatanKhusus) {

        this.idAnak = idAnak;
        this.idOrangTua = idOrangTua;
        this.namaAnak = namaAnak;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.catatanKhusus = catatanKhusus;
    }

    public MasterAnak(String idAnak,
                      String idOrangTua,
                      String namaAnak,
                      Date tanggalLahir,
                      char jenisKelamin,
                      String kategori,
                      String catatanKhusus,
                      String status) {

        this.idAnak = idAnak;
        this.idOrangTua = idOrangTua;
        this.namaAnak = namaAnak;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.kategori = kategori;
        this.catatanKhusus = catatanKhusus;
        this.status = status;
    }

    // ================= GETTER & SETTER =================

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

    public String getIdPaket() {
        return idPaket;
    }

    public void setIdPaket(String idPaket) {
        this.idPaket = idPaket;
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

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getStatus() {  // ✅ TAMBAH INI
        return status;
    }

    public void setStatus(String status) {  // ✅ TAMBAH INI
        this.status = status;
    }
}
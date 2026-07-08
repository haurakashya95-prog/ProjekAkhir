package com.example.kidsdaycareactivitycenter.Model;

public class OrangTua {

    private String idOrangTua;
    private String namaOrangTua;
    private String noHandphone;
    private String alamat;
    private String email;

    public OrangTua(String idOrangTua,
                    String namaOrangTua,
                    String noHandphone,
                    String alamat,
                    String email) {

        this.idOrangTua = idOrangTua;
        this.namaOrangTua = namaOrangTua;
        this.noHandphone = noHandphone;
        this.alamat = alamat;
        this.email = email;

    }

    public String getIdOrangTua() {
        return idOrangTua;
    }

    public String getNamaOrangTua() {
        return namaOrangTua;
    }

    public String getNoHandphone() {
        return noHandphone;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setIdOrangTua(String idOrangTua) {
        this.idOrangTua = idOrangTua;
    }

    public void setNamaOrangTua(String namaOrangTua) {
        this.namaOrangTua = namaOrangTua;
    }

    public void setNoHandphone(String noHandphone) {
        this.noHandphone = noHandphone;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
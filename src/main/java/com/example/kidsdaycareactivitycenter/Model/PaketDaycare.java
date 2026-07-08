package com.example.kidsdaycareactivitycenter.Model;

public class PaketDaycare {

    private String idPaket;
    private String kategoriDaycare;
    private String kategoriUsia;
    private String jenisPaket;
    private String hariOperasional;
    private String jamMasuk;
    private String jamPulang;
    private double harga;

    public PaketDaycare() {
    }

    public PaketDaycare(String idPaket,
                        String kategoriDaycare,
                        String kategoriUsia,
                        String jenisPaket,
                        String hariOperasional,
                        String jamMasuk,
                        String jamPulang,
                        double harga) {

        this.idPaket = idPaket;
        this.kategoriDaycare = kategoriDaycare;
        this.kategoriUsia = kategoriUsia;
        this.jenisPaket = jenisPaket;
        this.hariOperasional = hariOperasional;
        this.jamMasuk = jamMasuk;
        this.jamPulang = jamPulang;
        this.harga = harga;
    }


    public String getIdPaket() {
        return idPaket;
    }

    public void setIdPaket(String idPaket) {
        this.idPaket = idPaket;
    }

    public String getKategoriDaycare() {
        return kategoriDaycare;
    }

    public void setKategoriDaycare(String kategoriDaycare) {
        this.kategoriDaycare = kategoriDaycare;
    }

    public String getKategoriUsia() {
        return kategoriUsia;
    }

    public void setKategoriUsia(String kategoriUsia) {
        this.kategoriUsia = kategoriUsia;
    }

    public String getJenisPaket() {
        return jenisPaket;
    }

    public void setJenisPaket(String jenisPaket) {
        this.jenisPaket = jenisPaket;
    }

    public String getHariOperasional() {
        return hariOperasional;
    }

    public void setHariOperasional(String hariOperasional) {
        this.hariOperasional = hariOperasional;
    }

    public String getJamMasuk() {
        return jamMasuk;
    }

    public void setJamMasuk(String jamMasuk) {
        this.jamMasuk = jamMasuk;
    }

    public String getJamPulang() {
        return jamPulang;
    }

    public void setJamPulang(String jamPulang) {
        this.jamPulang = jamPulang;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

}
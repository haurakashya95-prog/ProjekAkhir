package com.example.kidsdaycareactivitycenter.Model;

public class TransaksiDaycare {

    private String idDaycare;
    private String idAnak;
    private String namaAnak;
    private String idPaket;
    private String kategoriDaycare;
    private String kategoriUsia;
    private String jenisPaket;
    private String idKaryawan;
    private String namaKaryawan;
    private String tanggalMulai;
    private String tanggalSelesai;
    private String statusDaycare;

    public TransaksiDaycare() {
    }

    public TransaksiDaycare(String idDaycare,
                            String idAnak,
                            String namaAnak,
                            String idPaket,
                            String kategoriDaycare,
                            String kategoriUsia,
                            String jenisPaket,
                            String idKaryawan,
                            String namaKaryawan,
                            String tanggalMulai,
                            String tanggalSelesai,
                            String statusDaycare) {

        this.idDaycare = idDaycare;
        this.idAnak = idAnak;
        this.namaAnak = namaAnak;
        this.idPaket = idPaket;
        this.kategoriDaycare = kategoriDaycare;
        this.kategoriUsia = kategoriUsia;
        this.jenisPaket = jenisPaket;
        this.idKaryawan = idKaryawan;
        this.namaKaryawan = namaKaryawan;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.statusDaycare = statusDaycare;
    }

    public String getIdDaycare() {
        return idDaycare;
    }

    public void setIdDaycare(String idDaycare) {
        this.idDaycare = idDaycare;
    }

    public String getIdAnak() {
        return idAnak;
    }

    public void setIdAnak(String idAnak) {
        this.idAnak = idAnak;
    }

    public String getNamaAnak() {
        return namaAnak;
    }

    public void setNamaAnak(String namaAnak) {
        this.namaAnak = namaAnak;
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

    public String getIdKaryawan() {
        return idKaryawan;
    }

    public void setIdKaryawan(String idKaryawan) {
        this.idKaryawan = idKaryawan;
    }

    public String getNamaKaryawan() {
        return namaKaryawan;
    }

    public void setNamaKaryawan(String namaKaryawan) {
        this.namaKaryawan = namaKaryawan;
    }

    public String getTanggalMulai() {
        return tanggalMulai;
    }

    public void setTanggalMulai(String tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public String getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(String tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }

    public String getStatusDaycare() {
        return statusDaycare;
    }

    public void setStatusDaycare(String statusDaycare) {
        this.statusDaycare = statusDaycare;
    }

}
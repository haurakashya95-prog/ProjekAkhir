package com.example.kidsdaycareactivitycenter.Model;

public class ActivitySchedule {

    private String idActivity;
    private String idPaket;
    private String hari;
    private String jamMulai;
    private String jamSelesai;
    private String temaActivity;
    private String deskripsiActivity;

    public ActivitySchedule() {
    }

    public ActivitySchedule(String idActivity,
                            String idPaket,
                            String hari,
                            String jamMulai,
                            String jamSelesai,
                            String temaActivity,
                            String deskripsiActivity) {

        this.idActivity = idActivity;
        this.idPaket = idPaket;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.temaActivity = temaActivity;
        this.deskripsiActivity = deskripsiActivity;
    }

    public String getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(String idActivity) {
        this.idActivity = idActivity;
    }

    public String getIdPaket() {
        return idPaket;
    }

    public void setIdPaket(String idPaket) {
        this.idPaket = idPaket;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(String jamMulai) {
        this.jamMulai = jamMulai;
    }

    public String getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(String jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public String getTemaActivity() {
        return temaActivity;
    }

    public void setTemaActivity(String temaActivity) {
        this.temaActivity = temaActivity;
    }

    public String getDeskripsiActivity() {
        return deskripsiActivity;
    }

    public void setDeskripsiActivity(String deskripsiActivity) {
        this.deskripsiActivity = deskripsiActivity;
    }
}
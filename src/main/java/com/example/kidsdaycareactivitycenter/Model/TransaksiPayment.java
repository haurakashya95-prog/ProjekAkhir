package com.example.kidsdaycareactivitycenter.Model;

import java.time.LocalDate;

public class TransaksiPayment {
    private String idPayment;
    private String namaOrangTua;
    private String namaKaryawan;
    private LocalDate tanggal;
    private double totalTagihan;
    private double jumlahDibayar;
    private String metode;
    private String status;

    public TransaksiPayment(String idPayment, String namaOrangTua, String namaKaryawan,
                            LocalDate tanggal, double totalTagihan, double jumlahDibayar,
                            String metode, String status) {
        this.idPayment = idPayment;
        this.namaOrangTua = namaOrangTua;
        this.namaKaryawan = namaKaryawan;
        this.tanggal = tanggal;
        this.totalTagihan = totalTagihan;
        this.jumlahDibayar = jumlahDibayar;
        this.metode = metode;
        this.status = status;
    }

    // Getter
    public String getIdPayment() { return idPayment; }
    public String getNamaOrangTua() { return namaOrangTua; }
    public String getNamaKaryawan() { return namaKaryawan; }
    public LocalDate getTanggal() { return tanggal; }
    public double getTotalTagihan() { return totalTagihan; }
    public double getJumlahDibayar() { return jumlahDibayar; }
    public String getMetode() { return metode; }
    public String getStatus() { return status; }
}

package com.example.kidsdaycareactivitycenter.Model;

public class Karyawan {

    private String idKaryawan;
    private String namaKaryawan;
    private String username;
    private String password;
    private String role;
    private String status;

    public Karyawan(String idKaryawan,
                    String namaKaryawan,
                    String username,
                    String password,
                    String role,
                    String status){

        this.idKaryawan = idKaryawan;
        this.namaKaryawan = namaKaryawan;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;

    }

    public String getIdKaryawan() { return idKaryawan; }

    public String getNamaKaryawan() { return namaKaryawan; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getRole() { return role; }

    public String getStatus() { return status; }

}
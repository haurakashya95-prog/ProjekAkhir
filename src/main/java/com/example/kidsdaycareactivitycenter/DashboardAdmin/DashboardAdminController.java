package com.example.kidsdaycareactivitycenter.DashboardAdmin;

import com.example.kidsdaycareactivitycenter.Database.DBConnect;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import javafx.stage.Stage;
import javafx.scene.control.Button;

import javafx.util.Duration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.SimpleDateFormat;

import java.util.Date;

public class DashboardAdminController {

    //=================================================
    // DATABASE
    //=================================================

    private final DBConnect db = new DBConnect();

    private PreparedStatement ps;

    private ResultSet rs;

    //=================================================
    // HEADER
    //=================================================

    @FXML
    private Label lblLogin;

    @FXML
    private Label lblRole;

    @FXML
    private Label lblTanggal;

    @FXML
    private Label lblJam;

    @FXML
    private Label lblFooterLogin;

    //=================================================
    // DASHBOARD
    //=================================================

    @FXML
    private Label lblTotalAnak;

    @FXML
    private Label lblTotalOrangTua;

    @FXML
    private Label lblTotalKaryawan;

    @FXML
    private Label lblAnakHariIni;

    //=================================================
    // USER LOGIN
    //=================================================

    private String namaLogin = "Administrator";

    private String roleLogin = "Admin";

    public void setUserLogin(String nama, String role) {

        this.namaLogin = nama;
        this.roleLogin = role;

        if (lblLogin != null) {
            lblLogin.setText(namaLogin);
        }

        if (lblRole != null) {
            lblRole.setText(roleLogin);
        }

        if (lblFooterLogin != null) {
            lblFooterLogin.setText(
                    namaLogin + " (" + roleLogin + ")");
        }

    }

    //=================================================
    // INITIALIZE
    //=================================================

    @FXML
    public void initialize() {

        lblLogin.setText(namaLogin);

        lblRole.setText(roleLogin);

        lblFooterLogin.setText(
                namaLogin + " (" + roleLogin + ")");

        tampilTanggal();

        tampilJam();

        loadDashboard();

    }

    //=================================================
    // TANGGAL
    //=================================================

    private void tampilTanggal() {

        SimpleDateFormat sdf =
                new SimpleDateFormat("dd MMMM yyyy");

        lblTanggal.setText(
                sdf.format(new Date()));

    }

    //=================================================
    // JAM
    //=================================================

    private void tampilJam() {

        Timeline timeline = new Timeline(

                new KeyFrame(Duration.seconds(1), e -> {

                    SimpleDateFormat sdf =
                            new SimpleDateFormat("HH:mm:ss");

                    lblJam.setText(
                            sdf.format(new Date()));

                })

        );

        timeline.setCycleCount(
                Timeline.INDEFINITE);

        timeline.play();

    }

    //=================================================
    // LOAD DASHBOARD
    //=================================================

    private void loadDashboard() {

        totalAnak();

        totalOrangTua();

        totalKaryawan();

        anakHariIni();

    }

    //=================================================
    // TOTAL ANAK
    //=================================================

    private void totalAnak() {

        try {

            ps = db.conn.prepareStatement(
                    "SELECT COUNT(*) FROM Anak");

            rs = ps.executeQuery();

            if (rs.next()) {

                lblTotalAnak.setText(
                        rs.getString(1));

            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            lblTotalAnak.setText("0");

        }

    }

    //=================================================
    // TOTAL ORANG TUA
    //=================================================

    private void totalOrangTua() {

        try {

            ps = db.conn.prepareStatement(
                    "SELECT COUNT(*) FROM Orang_Tua");

            rs = ps.executeQuery();

            if (rs.next()) {

                lblTotalOrangTua.setText(
                        rs.getString(1));

            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            lblTotalOrangTua.setText("0");

        }

    }

    //=================================================
    // TOTAL KARYAWAN
    //=================================================

    private void totalKaryawan() {

        try {

            ps = db.conn.prepareStatement(
                    "SELECT COUNT(*) FROM Karyawan");

            rs = ps.executeQuery();

            if (rs.next()) {

                lblTotalKaryawan.setText(
                        rs.getString(1));

            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            lblTotalKaryawan.setText("0");

        }

    }

    //=================================================
    // ANAK HARI INI
    //=================================================

    private void anakHariIni() {

        try {

            ps = db.conn.prepareStatement(
                    "SELECT COUNT(*) " +
                            "FROM Transaksi_Daycare " +
                            "WHERE CAST(Tanggal AS DATE)=CAST(GETDATE() AS DATE)");

            rs = ps.executeQuery();

            if (rs.next()) {

                lblAnakHariIni.setText(
                        rs.getString(1));

            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            lblAnakHariIni.setText("0");

        }

    }

    //=================================================
    // MASTER KARYAWAN
    //=================================================

    @FXML
    private void masterKaryawan(ActionEvent event) {

        bukaForm(
                "/MasterKaryawan/MasterKaryawan.fxml",
                "Master Karyawan"
        );

    }

    //=================================================
    // MASTER ORANG TUA
    //=================================================

    @FXML
    private void masterOrangTua(ActionEvent event) {

        bukaForm(
                "/MasterOrangTua/MasterOrangTua.fxml",
                "Master Orang Tua"
        );

    }

    //=================================================
    // MASTER ANAK
    //=================================================

    @FXML
    private void masterAnak(ActionEvent event) {

        bukaForm(
                "/MasterAnak/MasterAnak.fxml",
                "Master Anak"
        );

    }

    //=================================================
    // PAKET DAYCARE
    //=================================================

    @FXML
    private void paketDaycare(ActionEvent event){

        bukaForm(
                "/MasterPaketDaycare/MasterPaketDaycare.fxml",
                "Master Paket Daycare"
        );

    }

    //=================================================
    // ACTIVITY SCHEDULE
    //=================================================

    @FXML
    private void activitySchedule(ActionEvent event) {

        bukaForm(
                "/MasterActivitySchedule/MasterActivitySchedule.fxml",
                "Master Activity Schedule"
        );

    }

    //=================================================
    // METHOD MEMBUKA FORM
    //=================================================

    private void bukaForm(String fxml, String title) {

        try {

            var url = getClass().getResource(fxml);

            if (url == null) {

                throw new RuntimeException(
                        "FXML tidak ditemukan : " + fxml);

            }

            FXMLLoader loader = new FXMLLoader(url);

            Parent root = loader.load();

            Stage stage =
                    (Stage) lblLogin.getScene().getWindow();

            stage.setScene(new Scene(root));

            stage.setTitle(title);

            stage.centerOnScreen();

            stage.show();

        }

        catch (Exception e){

            e.printStackTrace();

            error(e.getMessage());

        }

    }
    //=================================================
    // TRANSAKSI DAYCARE
    //=================================================

    @FXML
    private void transaksiDaycare(ActionEvent event) {

        bukaForm(
                "/TransaksiDaycare/TransaksiDaycare.fxml",
                "Transaksi Daycare"
        );

    }

    //=================================================
    // TRANSAKSI PAYMENT
    //=================================================

    @FXML
    private void transaksiPayment(ActionEvent event) {

        bukaForm(
                "/TransaksiPayment/TransaksiPayment.fxml",
                "Transaksi Payment"
        );

    }

    //=================================================
    // LAPORAN DAYCARE
    //=================================================

    @FXML
    private void laporanDaycare(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText("Menu Laporan Daycare masih dalam proses pengembangan.");

        alert.showAndWait();

    }

    //=================================================
    // LAPORAN PAYMENT
    //=================================================

    @FXML
    private void laporanPayment(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText("Menu Laporan Payment masih dalam proses pengembangan.");

        alert.showAndWait();

    }

    //=================================================
    // LAPORAN ACTIVITY
    //=================================================

    @FXML
    private void laporanActivity(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText("Menu Laporan Activity masih dalam proses pengembangan.");

        alert.showAndWait();

    }

    //=================================================
    // LOGOUT
    //=================================================

    @FXML
    private void logout(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Login/Login.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) lblLogin.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.setResizable(true);
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

            info("Gagal Logout!\n" + e.getMessage());

        }

    }

    //=================================================
    // ALERT INFORMATION
    //=================================================

    private void info(String pesan) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText(pesan);

        alert.showAndWait();

    }

    //=================================================
    // ALERT ERROR
    //=================================================

    private void error(String pesan) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(pesan);

        alert.showAndWait();

    }

    @FXML
    private void masterPaketDaycare(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/MasterPaketDaycare/MasterPaketDaycare.fxml"));

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Master Paket Daycare");
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Gagal membuka Master Paket Daycare");
            alert.setContentText(e.toString());
            alert.showAndWait();

        }

    }

    @FXML
    private void refreshDashboard() {

        loadDashboard();

        info("Dashboard berhasil diperbarui.");

    }

}
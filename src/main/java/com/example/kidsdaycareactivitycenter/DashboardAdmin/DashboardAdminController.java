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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardAdminController {

    private final DBConnect db = new DBConnect();
    private PreparedStatement ps;
    private ResultSet rs;

    @FXML private Label lblLogin;
    @FXML private Label lblRole;
    @FXML private Label lblTanggal;
    @FXML private Label lblJam;
    @FXML private Label lblFooterLogin;

    @FXML private Label lblTotalAnak;
    @FXML private Label lblTotalOrangTua;
    @FXML private Label lblTotalKaryawan;
    @FXML private Label lblAnakHariIni;

    @FXML private Button btnLogout;

    private String namaLogin="Administrator";
    private String roleLogin="Admin";

    @FXML
    public void initialize(){
        setUserLogin(namaLogin,roleLogin);
        tampilTanggal();
        tampilJam();
        loadDashboard();
    }

    public void setUserLogin(String nama,String role){
        namaLogin=nama;
        roleLogin=role;
        if(lblLogin!=null) lblLogin.setText(nama);
        if(lblRole!=null) lblRole.setText(role);
        if(lblFooterLogin!=null) lblFooterLogin.setText(nama+" ("+role+")");
    }

    private void tampilTanggal(){
        lblTanggal.setText(new SimpleDateFormat("dd MMM yyyy").format(new Date()));
    }

    private void tampilJam(){
        Timeline t=new Timeline(new KeyFrame(Duration.seconds(1),e->
                lblJam.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()))));
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

    private void loadDashboard(){
        totalAnak();
        totalOrangTua();
        totalKaryawan();
        anakHariIni();
    }

    private void totalAnak(){ loadCount("SELECT COUNT(*) FROM Anak",lblTotalAnak); }
    private void totalOrangTua(){ loadCount("SELECT COUNT(*) FROM Orang_Tua",lblTotalOrangTua); }
    private void totalKaryawan(){ loadCount("SELECT COUNT(*) FROM Karyawan",lblTotalKaryawan); }
    private void anakHariIni(){
        loadCount("SELECT COUNT(*) FROM Transaksi_Daycare WHERE CAST(Tanggal AS DATE)=CAST(GETDATE() AS DATE)",lblAnakHariIni);
    }

    private void loadCount(String sql, Label label){
        try{
            ps=db.conn.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next()) label.setText(rs.getString(1));
            rs.close();
            ps.close();
        }catch(Exception e){
            label.setText("0");
            e.printStackTrace();
        }
    }

    private void bukaForm(String fxml,String title){
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource(fxml));
            Parent root=loader.load();
            Stage stage=(Stage)lblLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.centerOnScreen();
        }catch(Exception e){
            error(e.getMessage());
        }
    }

    @FXML private void masterKaryawan(ActionEvent e){bukaForm("/MasterKaryawan/MasterKaryawan.fxml","Master Karyawan");}
    @FXML private void masterOrangTua(ActionEvent e){bukaForm("/MasterOrangTua/MasterOrangTua.fxml","Master Orang Tua");}
    @FXML private void masterAnak(ActionEvent e){bukaForm("/MasterAnak/MasterAnak.fxml","Master Anak");}
    @FXML private void paketDaycare(ActionEvent e){bukaForm("/MasterPaketDaycare/MasterPaketDaycare.fxml","Master Paket Daycare");}
    @FXML private void activitySchedule(ActionEvent e){bukaForm("/MasterActivitySchedule/MasterActivitySchedule.fxml","Activity Schedule");}
    @FXML private void transaksiDaycare(ActionEvent e){bukaForm("/TransaksiDaycare/TransaksiDaycare.fxml","Transaksi Daycare");}
    @FXML private void transaksiPayment(ActionEvent e){bukaForm("/TransaksiPayment/TransaksiPayment.fxml","Transaksi Payment");}

    @FXML private void refreshDashboard(){ loadDashboard(); }

    @FXML
    private void logout(ActionEvent e){
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
            Parent root=loader.load();
            Stage stage=(Stage)lblLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.centerOnScreen();
        }catch(Exception ex){
            error(ex.getMessage());
        }
    }

    private void info(String msg){
        Alert a=new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void error(String msg){
        Alert a=new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("Error");
        a.setContentText(msg);
        a.showAndWait();
    }
}

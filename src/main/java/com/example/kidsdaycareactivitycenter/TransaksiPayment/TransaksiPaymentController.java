package com.example.kidsdaycareactivitycenter.TransaksiPayment;

import com.example.kidsdaycareactivitycenter.Database.DBConnect;
import com.example.kidsdaycareactivitycenter.Model.TransaksiPayment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.sql.ResultSet;
import java.time.LocalDate;

public class TransaksiPaymentController {

    @FXML private TextField txtIDPayment;
    @FXML private ComboBox<String> cmbOrangTua;
    @FXML private ComboBox<String> cmbKaryawan;
    @FXML private DatePicker dpTanggal;
    @FXML private TextField txtTotalTagihan;
    @FXML private TextField txtJumlahDibayar;
    @FXML private ComboBox<String> cmbMetode;
    @FXML private Button btnKembali;

    @FXML private TableView<TransaksiPayment> tblPayment;
    @FXML private TableColumn<TransaksiPayment,String> colIDPayment;
    @FXML private TableColumn<TransaksiPayment,String> colOrangTua;
    @FXML private TableColumn<TransaksiPayment,String> colKaryawan;
    @FXML private TableColumn<TransaksiPayment,LocalDate> colTanggal;
    @FXML private TableColumn<TransaksiPayment,Double> colTotalTagihan;
    @FXML private TableColumn<TransaksiPayment,Double> colJumlahDibayar;
    @FXML private TableColumn<TransaksiPayment,String> colMetode;
    @FXML private TableColumn<TransaksiPayment,String> colStatus;

    private final ObservableList<TransaksiPayment> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTable();
        loadCombo();
        loadTable();
    }

    private void setupTable() {
        colIDPayment.setCellValueFactory(new PropertyValueFactory<>("idPayment"));
        colOrangTua.setCellValueFactory(new PropertyValueFactory<>("namaOrangTua"));
        colKaryawan.setCellValueFactory(new PropertyValueFactory<>("namaKaryawan"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colTotalTagihan.setCellValueFactory(new PropertyValueFactory<>("totalTagihan"));
        colJumlahDibayar.setCellValueFactory(new PropertyValueFactory<>("jumlahDibayar"));
        colMetode.setCellValueFactory(new PropertyValueFactory<>("metode"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblPayment.setItems(data);
    }

    private void loadCombo() {
        cmbMetode.getItems().addAll("Cash","Transfer","QRIS");
        try {
            DBConnect db = new DBConnect();
            ResultSet rs = db.conn.createStatement().executeQuery("SELECT Nama_OrangTua FROM Orang_Tua");
            while(rs.next()) cmbOrangTua.getItems().add(rs.getString(1));
            rs = db.conn.createStatement().executeQuery("SELECT Nama_Karyawan FROM Karyawan");
            while(rs.next()) cmbKaryawan.getItems().add(rs.getString(1));
        } catch(Exception e) { e.printStackTrace(); }
    }

    private void loadTable() {
        data.clear();
        try {
            DBConnect db = new DBConnect();
            ResultSet rs = db.conn.createStatement().executeQuery("EXEC sp_ShowPayment");
            while(rs.next()) {
                data.add(new TransaksiPayment(
                        rs.getString("ID_Payment"),
                        rs.getString("Nama_OrangTua"),
                        rs.getString("Nama_Karyawan"),
                        rs.getDate("Tanggal").toLocalDate(),
                        rs.getDouble("Total_Tagihan"),
                        rs.getDouble("Jumlah_Dibayar"),
                        rs.getString("Metode_Payment"),
                        rs.getString("Status_Payment")
                ));
            }
        } catch(Exception e) { e.printStackTrace(); }
    }

    @FXML private void simpan() { /* TODO: insert ke DB */ }
    @FXML private void ubah() { /* TODO: update ke DB */ }
    @FXML private void hapus() { /* TODO: delete ke DB */ }
    @FXML private void cari() { /* TODO: search ke DB */ }
    @FXML private void batal() { /* TODO: clear form */ }

    @FXML
    private void kembali() {
        try {
            Stage stage = (Stage) btnKembali.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/DashboardAdmin/DashboardAdmin.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard Admin");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Gagal kembali ke Dashboard");
            alert.showAndWait();
        }
    }
}

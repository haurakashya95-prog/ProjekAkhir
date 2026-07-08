package com.example.kidsdaycareactivitycenter.MasterOrangTua;

import com.example.kidsdaycareactivitycenter.Database.DBConnect;
import com.example.kidsdaycareactivitycenter.Model.OrangTua;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;

import java.net.URL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ResourceBundle;
import javafx.scene.Parent;

public class MasterOrangTuaController implements Initializable {


    DBConnect db = new DBConnect();

    Connection conn;

    Statement stat;

    PreparedStatement ps;

    CallableStatement cs;

    ResultSet rs;


    ObservableList<OrangTua> listOrangTua =
            FXCollections.observableArrayList();
    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNama;

    @FXML
    private TextField txtNoHP;

    @FXML
    private TextField txtAlamat;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtCari;

    //==================================================
    // BUTTON
    //==================================================

    @FXML
    private Button btnSimpan;

    @FXML
    private Button btnUbah;

    @FXML
    private Button btnHapus;

    @FXML
    private Button btnBatal;

    @FXML
    private Button btnKembali;

    //==================================================
    // LABEL
    //==================================================

    @FXML
    private Label lblTotalData;

    @FXML
    private Label lblStatus;

    //==================================================
    // TABLEVIEW
    //==================================================

    @FXML
    private TableView<OrangTua> tblOrangTua;

    @FXML
    private TableColumn<OrangTua, String> colID;

    @FXML
    private TableColumn<OrangTua, String> colNama;

    @FXML
    private TableColumn<OrangTua, String> colNoHP;

    @FXML
    private TableColumn<OrangTua, String> colAlamat;

    @FXML
    private TableColumn<OrangTua, String> colEmail;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        conn = db.conn;
        System.out.println(conn);

        initializeTable();

        disableButton();

        autoID();

        loadTable();

        refreshTable();

        tableClick();

        lblStatus.setText("Siap Digunakan");

        lblTotalData.setText(
                String.valueOf(listOrangTua.size())
        );

    }

    private void initializeTable() {
        colID.setCellValueFactory(new PropertyValueFactory<>("idOrangTua"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaOrangTua"));
        colNoHP.setCellValueFactory(new PropertyValueFactory<>("noHandphone"));
        colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tblOrangTua.setItems(listOrangTua);
        System.out.println(colID);
        System.out.println(tblOrangTua);

    }

    private void disableButton() {

        btnSimpan.setDisable(false);

        btnUbah.setDisable(true);

        btnHapus.setDisable(true);

    }

    private void enableButton() {

        btnSimpan.setDisable(true);

        btnUbah.setDisable(false);

        btnHapus.setDisable(false);

    }


    private void autoID() {

        try {

            String sql = "SELECT TOP 1 ID_OrangTua FROM Orang_Tua ORDER BY ID_OrangTua DESC";

            stat = conn.createStatement();
            rs = stat.executeQuery(sql);

            if (rs.next()) {

                String id = rs.getString("ID_OrangTua").substring(2);

                int nomor = Integer.parseInt(id);

                nomor++;

                txtID.setText(String.format("OT%03d", nomor));

            } else {

                txtID.setText("OT001");

            }

            txtID.setEditable(false);

        } catch (Exception e) {

            alertError("Gagal membuat ID : " + e.getMessage());

        }

    }

    private void loadTable() {

        listOrangTua.clear();

        try {

            String sql = "SELECT *\n" +
                    "FROM Orang_Tua";

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            System.out.println("Load Table Jalan");

            while (rs.next()) {

                System.out.println(rs.getString("ID_OrangTua"));

                listOrangTua.add(
                        new OrangTua(
                                rs.getString("ID_OrangTua"),
                                rs.getString("Nama_OrangTua"),
                                rs.getString("No_Handphone"),
                                rs.getString("Alamat"),
                                rs.getString("Email")
                        )
                );
            }

            System.out.println("Jumlah Data = " + listOrangTua.size());


            tblOrangTua.setItems(listOrangTua);
            tblOrangTua.refresh();

            lblTotalData.setText(String.valueOf(listOrangTua.size()));

        } catch (Exception e) {

            e.printStackTrace();
            alertError(e.getMessage());

        }

    }

    private void refreshTable() {
        loadTable();
    }


    private void tableClick() {

        tblOrangTua.setOnMouseClicked(event -> {

            OrangTua data = tblOrangTua.getSelectionModel().getSelectedItem();

            if (data != null) {

                txtID.setText(data.getIdOrangTua());
                txtNama.setText(data.getNamaOrangTua());
                txtNoHP.setText(data.getNoHandphone());
                txtAlamat.setText(data.getAlamat());
                txtEmail.setText(data.getEmail());
                enableButton();
                lblStatus.setText("Data dipilih");

            }

        });

    }

    private boolean validation() {

        if (txtNama.getText().trim().isEmpty()) {

            alertWarning("Nama OrangTua tidak boleh kosong.");
            txtNama.requestFocus();
            return false;

        }

        if (txtEmail.getText().trim().isEmpty()) {

            alertWarning("Email OrangTua tidak boleh kosong.");
            txtNama.requestFocus();
            return false;

        }

        if (txtNoHP.getText().trim().isEmpty()) {

            alertWarning("No.HP tidak boleh kosong.");
            txtNoHP.requestFocus();
            return false;

        }

        return true;

    }


    private void clearForm() {

        txtNama.clear();

        txtNoHP.clear();

        txtEmail.clear();

        txtAlamat.clear();

        tblOrangTua.getSelectionModel().clearSelection();

        txtCari.clear();

        txtID.clear();

        autoID();

        disableButton();

        txtNama.requestFocus();

        lblStatus.setText("Siap Digunakan");

    }


    @FXML
    private void batal(ActionEvent event) {

        clearForm();
        disableButton();
    }

    @FXML
    private void simpan(ActionEvent event) {

        if (!validation()) {

            return;

        }

        try {

            cs = db.conn.prepareCall("{call sp_InsertOrangTua(?,?,?,?)}");

            cs.setString(1, txtNama.getText());
            cs.setString(2, txtNoHP.getText());
            cs.setString(3, txtAlamat.getText());
            cs.setString(4, txtEmail.getText());

            cs.execute();

            alertInformation("Data Orang Tua berhasil disimpan.");

            refreshTable();

            clearForm();

        }

        catch (Exception e) {

            alertError("Gagal menyimpan data : " + e.getMessage());

        }

    }

    @FXML
    private void ubah(ActionEvent event) {

        System.out.println(txtID.getText());

        if (!validation()) {
            return;
        }

        try {

            cs = db.conn.prepareCall("{call sp_UpdateOrangTua(?,?,?,?,?)}");

            cs.setString(1, txtID.getText());
            cs.setString(2, txtNama.getText());
            cs.setString(3, txtNoHP.getText());
            cs.setString(4, txtAlamat.getText());
            cs.setString(5, txtEmail.getText());

            cs.execute(); // <-- WAJIB DITAMBAHKAN

            alertInformation("Data Orang Tua berhasil diubah.");

            refreshTable();

            clearForm();

        } catch (Exception e) {

            alertError("Gagal mengubah data : " + e.getMessage());

        }

    }


    @FXML
    private void hapus(ActionEvent event) {

        if (txtID.getText().isEmpty()) {

            alertWarning("Pilih data yang akan dihapus.");

            return;

        }

        try {

            cs = db.conn.prepareCall("{call sp_DeleteOrangTua(?)}");

            cs.setString(1, txtID.getText());

            cs.execute();

            alertInformation("Data Orang Tua berhasil dihapus.");

            refreshTable();

            clearForm();

        }

        catch (Exception e) {

            alertError("Gagal menghapus data : " + e.getMessage());

        }

    }

    //==================================================
    // CARI
    //==================================================

    @FXML
    private void cari(ActionEvent event) {

        listOrangTua.clear();

        try {

            cs = db.conn.prepareCall("{call sp_SearchOrangTua(?)}");

            cs.setString(1, txtCari.getText());

            rs = cs.executeQuery();

            while (rs.next()) {

                listOrangTua.add(

                        new OrangTua(

                                rs.getString("ID_OrangTua"),

                                rs.getString("Nama_OrangTua"),

                                rs.getString("No_Handphone"),

                                rs.getString("Alamat"),

                                rs.getString("Email")

                        )
                );
            }

            tblOrangTua.setItems(listOrangTua);
            tblOrangTua.refresh();

            lblTotalData.setText(
                    String.valueOf(listOrangTua.size())
            );

            lblStatus.setText("Pencarian selesai.");

        }

        catch (Exception e) {

            alertError(e.getMessage());

        }

    }

    //==================================================
    // KEMBALI
    //==================================================
    @FXML
    private void kembali(ActionEvent event) {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));

            Stage stage = (Stage) btnKembali.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.centerOnScreen();

        } catch (Exception e) {

            e.printStackTrace();
            alertError(e.getMessage());

        }

    }

    //==================================================
    // ALERT INFORMATION
    //==================================================

    private void alertInformation(String pesan) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Informasi");

        alert.setHeaderText(null);

        alert.setContentText(pesan);

        alert.showAndWait();

    }

    //==================================================
    // ALERT WARNING
    //==================================================

    private void alertWarning(String pesan) {

        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Peringatan");

        alert.setHeaderText(null);

        alert.setContentText(pesan);

        alert.showAndWait();

    }

    private void alertError(String pesan) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");

        alert.setHeaderText(null);

        alert.setContentText(pesan);

        alert.showAndWait();

    }

}



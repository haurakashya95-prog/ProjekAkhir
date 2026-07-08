package com.example.kidsdaycareactivitycenter.MasterKaryawan;

import com.example.kidsdaycareactivitycenter.Database.DBConnect;
import com.example.kidsdaycareactivitycenter.Model.Karyawan;

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

public class MasterKaryawanController implements Initializable {


    DBConnect db = new DBConnect();

    Connection conn;

    Statement stat;

    PreparedStatement ps;

    CallableStatement cs;

    ResultSet rs;


    ObservableList<Karyawan> listKaryawan =
            FXCollections.observableArrayList();

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNama;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtCari;

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TableColumn<Karyawan,String> colStatus;

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
    private TableView<Karyawan> tblKaryawan;

    @FXML
    private TableColumn<Karyawan, String> colID;

    @FXML
    private TableColumn<Karyawan, String> colNama;

    @FXML
    private TableColumn<Karyawan, String> colUsername;

    @FXML
    private TableColumn<Karyawan, String> colRole;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        conn = db.conn;
        System.out.println(conn);

        initializeTable();

        initializeCombo();

        disableButton();

        autoID();

        loadTable();

        refreshTable();

        tableClick();

        cmbStatus.getItems().addAll(
                "Aktif",
                "Nonaktif"
        );

        cmbStatus.setValue("Aktif");

        lblStatus.setText("Siap Digunakan");

        lblTotalData.setText(
                String.valueOf(listKaryawan.size())
        );

    }


    private void initializeTable() {

        colID.setCellValueFactory(new PropertyValueFactory<>("idKaryawan"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaKaryawan"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblKaryawan.setItems(listKaryawan);
    }


    private void initializeCombo() {

        cmbRole.getItems().addAll(
                "Admin",
                "Caregiver"
        );

        cmbStatus.getItems().addAll(
                "Aktif",
                "Nonaktif"
        );

        cmbStatus.setValue("Aktif");
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

            String sql = "SELECT TOP 1 ID_Karyawan FROM Karyawan ORDER BY ID_Karyawan DESC";

            stat = conn.createStatement();
            rs = stat.executeQuery(sql);

            if (rs.next()) {

                String id = rs.getString("ID_Karyawan").substring(3);

                int nomor = Integer.parseInt(id);

                nomor++;

                txtID.setText(String.format("KRY%03d", nomor));

            } else {

                txtID.setText("KRY001");

            }

            txtID.setEditable(false);

        } catch (Exception e) {

            alertError("Gagal membuat ID : " + e.getMessage());

        }

    }

    private void loadTable() {

        listKaryawan.clear();

        try {

            String sql = "SELECT\n" +
                    "ID_Karyawan,\n" +
                    "Nama_Karyawan,\n" +
                    "Username,\n" +
                    "Password,\n" +
                    "Role,\n" +
                    "Status\n" +
                    "FROM Karyawan";

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            while (rs.next()) {

                listKaryawan.add(

                        new Karyawan(

                                rs.getString("ID_Karyawan"),
                                rs.getString("Nama_Karyawan"),
                                rs.getString("Username"),
                                rs.getString("Password"),
                                rs.getString("Role"),
                                rs.getString("Status")

                        )

                );

            }

            tblKaryawan.setItems(listKaryawan);
            tblKaryawan.refresh();

            lblTotalData.setText(String.valueOf(listKaryawan.size()));

        } catch (Exception e) {

            e.printStackTrace();
            alertError(e.getMessage());

        }

    }

    private void refreshTable() {
        loadTable();
    }


    private void tableClick() {

        tblKaryawan.setOnMouseClicked(event -> {

            Karyawan data = tblKaryawan.getSelectionModel().getSelectedItem();

            if (data != null) {

                txtID.setText(data.getIdKaryawan());
                txtNama.setText(data.getNamaKaryawan());
                txtUsername.setText(data.getUsername());
                txtPassword.setText(data.getPassword());
                cmbRole.setValue(data.getRole());
                cmbStatus.setValue(data.getStatus());

                enableButton();
                lblStatus.setText("Data dipilih");

            }

        });

    }

    private boolean validation() {

        if (txtNama.getText().trim().isEmpty()) {

            alertWarning("Nama Karyawan tidak boleh kosong.");
            txtNama.requestFocus();
            return false;

        }

        if (txtUsername.getText().trim().isEmpty()) {

            alertWarning("Username tidak boleh kosong.");
            txtUsername.requestFocus();
            return false;

        }

        if (txtPassword.getText().trim().isEmpty()) {

            alertWarning("Password tidak boleh kosong.");
            txtPassword.requestFocus();
            return false;

        }

        if (cmbRole.getValue() == null) {

            alertWarning("Role harus dipilih.");
            cmbRole.requestFocus();
            return false;

        }

        if(cmbStatus.getValue()==null){

            alertWarning("Status harus dipilih.");

            return false;

        }

        return true;

    }


    private void clearForm() {

        txtNama.clear();

        txtUsername.clear();

        txtPassword.clear();

        txtCari.clear();

        cmbRole.getSelectionModel().clearSelection();

        tblKaryawan.getSelectionModel().clearSelection();

        autoID();

        disableButton();

        txtNama.requestFocus();

        lblStatus.setText("Siap Digunakan");

        cmbStatus.setValue("Aktif");

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

            cs = db.conn.prepareCall(
                    "{call sp_InsertKaryawan(?,?,?,?,?)}");

            cs.setString(1,txtNama.getText());
            cs.setString(2,txtUsername.getText());
            cs.setString(3,txtPassword.getText());
            cs.setString(4,cmbRole.getValue());
            cs.setString(5,cmbStatus.getValue());

            cs.execute();

            alertInformation("Data Karyawan berhasil disimpan.");

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

            cs = db.conn.prepareCall("{call sp_UpdateKaryawan(?,?,?,?,?,?)}");

            cs.setString(1, txtID.getText());
            cs.setString(2, txtNama.getText());
            cs.setString(3, txtUsername.getText());
            cs.setString(4, txtPassword.getText());
            cs.setString(5, cmbRole.getValue());
            cs.setString(6, cmbStatus.getValue());

            cs.execute();

            alertInformation("Data Karyawan berhasil diubah.");

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

            cs = db.conn.prepareCall("{call sp_DeleteKaryawan(?)}");

            cs.setString(1, txtID.getText());

            cs.execute();

            alertInformation("Data Karyawan berhasil dihapus.");

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

        listKaryawan.clear();

        try {

            cs = db.conn.prepareCall("{call sp_SearchKaryawan(?)}");

            cs.setString(1, txtCari.getText());

            rs = cs.executeQuery();

            while (rs.next()) {

                listKaryawan.add(

                        new Karyawan(

                                rs.getString("ID_Karyawan"),

                                rs.getString("Nama_Karyawan"),

                                rs.getString("Username"),

                                rs.getString("Password"),

                                rs.getString("Role"),

                                rs.getString("Status")

                        )
                );
            }

            tblKaryawan.setItems(listKaryawan);

            lblTotalData.setText(
                    String.valueOf(listKaryawan.size())
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
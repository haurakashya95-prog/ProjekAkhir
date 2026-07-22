package com.example.kidsdaycareactivitycenter.MasterAnak;

import com.example.kidsdaycareactivitycenter.Database.DBConnect;
import com.example.kidsdaycareactivitycenter.Model.MasterAnak;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.kidsdaycareactivitycenter.Model.MasterAnak;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MasterAnakController implements Initializable {

    DBConnect db = new DBConnect();
    Connection conn;
    Statement stat;
    PreparedStatement ps;
    CallableStatement cs;
    ResultSet rs;

    ObservableList<MasterAnak> listAnak = FXCollections.observableArrayList();

    @FXML
    private TextField txtIdAnak;
    @FXML
    private TextField txtNamaAnak;
    @FXML
    private TextField txtCariId;
    @FXML
    private TextArea txtCatatan;
    @FXML
    private DatePicker dpTanggalLahir;
    @FXML
    private ComboBox<String> cbOrangTua;
    @FXML
    private ComboBox<String> cbJenisKelamin;
    @FXML
    private TableView<MasterAnak> tblAnak;
    @FXML
    private TableColumn<MasterAnak, String> colIdAnak;
    @FXML
    private TableColumn<MasterAnak, String> colIdOrangTua;
    @FXML
    private TableColumn<MasterAnak, String> colNamaAnak;
    @FXML
    private TableColumn<MasterAnak, Character> colJenisKelamin;
    @FXML
    private TableColumn<MasterAnak, Date> colTanggalLahir;
    @FXML
    private TableColumn<MasterAnak, String> colCatatan;
    @FXML
    private Label lblTotalData;
    @FXML
    private Button btnTambah;
    @FXML
    private Button btnSimpan;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnHapus;
    @FXML
    private Button btnCari;

    @FXML
    private Button btnKembali;

    @FXML
    private Label lblStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        conn = db.conn;

        initializeTable();

        cbJenisKelamin.getItems().addAll("L", "P");

        loadComboOrangTua();

        autoID();

        loadTable();

        tableClick();

        btnEdit.setDisable(true);

        btnHapus.setDisable(true);

    }

    private void initializeTable() {

        colIdAnak.setCellValueFactory(new PropertyValueFactory<>("idAnak"));
        colIdOrangTua.setCellValueFactory(new PropertyValueFactory<>("idOrangTua"));
        colNamaAnak.setCellValueFactory(new PropertyValueFactory<>("namaAnak"));
        colTanggalLahir.setCellValueFactory(new PropertyValueFactory<>("tanggalLahir"));
        colJenisKelamin.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
        colCatatan.setCellValueFactory(new PropertyValueFactory<>("catatanKhusus"));

        tblAnak.setItems(listAnak);
    }

    private void disableButton() {
        btnSimpan.setDisable(false);
        btnEdit.setDisable(true);
        btnHapus.setDisable(true);
    }

    private void enableButton() {
        btnSimpan.setDisable(true);
        btnEdit.setDisable(false);
        btnHapus.setDisable(false);
    }

    private void autoID() {
        try {
            String sql = "SELECT TOP 1 ID_Anak FROM Anak ORDER BY ID_Anak DESC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {

                String id = rs.getString("ID_Anak").substring(3);
                int nomor = Integer.parseInt(id);
                nomor++;

                txtIdAnak.setText(String.format("ANK%03d", nomor));

            } else {

                txtIdAnak.setText("ANK001");

            }

            txtIdAnak.setEditable(false);

        } catch (Exception e) {

            new Alert(Alert.AlertType.ERROR,
                    "Gagal Generate ID\n" + e.getMessage()).show();

        }
    }

    private void loadComboOrangTua() {

        cbOrangTua.getItems().clear();

        try {

            String sql = "SELECT ID_OrangTua FROM Orang_Tua ORDER BY ID_OrangTua";

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                cbOrangTua.getItems().add(
                        rs.getString("ID_OrangTua")
                );

            }

        } catch (Exception e) {

            new Alert(Alert.AlertType.ERROR,
                    "Gagal Load Data Orang Tua\n" + e.getMessage()).show();

        }

    }

    private void loadTable() {

        listAnak.clear();

        try {

            String sql = """
            
                    SELECT *
                                FROM Anak
                                WHERE
                    Status='Aktif'
            """;

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                System.out.println("ID : " + rs.getString("ID_Anak"));

                MasterAnak anak = new MasterAnak(
                        rs.getString("ID_Anak"),
                        rs.getString("ID_OrangTua"),
                        rs.getString("Nama_Anak"),
                        rs.getDate("Tanggal_Lahir"),
                        rs.getString("Jenis_Kelamin").charAt(0),
                        rs.getString("Catatan_Khusus")
                );

                listAnak.add(anak);
            }

            System.out.println(listAnak.size());

            tblAnak.setItems(null);
            tblAnak.setItems(listAnak);


            lblTotalData.setText(listAnak.size() + " Data");

        } catch (Exception e) {

            new Alert(Alert.AlertType.ERROR,
                    "Gagal Load Data\n" + e.getMessage()).show();

        }

    }

    private void tableClick() {

        tblAnak.setOnMouseClicked(event -> {

            MasterAnak anak =
                    tblAnak.getSelectionModel().getSelectedItem();

            if (anak != null) {

                txtIdAnak.setText(
                        anak.getIdAnak());

                cbOrangTua.setValue(
                        anak.getIdOrangTua());

                txtNamaAnak.setText(
                        anak.getNamaAnak());

                dpTanggalLahir.setValue(
                        anak.getTanggalLahir().toLocalDate());

                cbJenisKelamin.setValue(
                        String.valueOf(anak.getJenisKelamin()));

                txtCatatan.setText(
                        anak.getCatatanKhusus());

                btnSimpan.setDisable(true);
                btnEdit.setDisable(false);
                btnHapus.setDisable(false);

            }

        });

    }

    private void clearForm() {

        txtIdAnak.clear();
        txtNamaAnak.clear();
        txtCatatan.clear();
        txtCariId.clear();

        cbOrangTua.getSelectionModel().clearSelection();
        cbJenisKelamin.getSelectionModel().clearSelection();

        dpTanggalLahir.setValue(null);

        tblAnak.getSelectionModel().clearSelection();

        autoID();

        disableButton();

    }

    @FXML
    private void simpan(ActionEvent event) {

        if (!validation())
            return;

        try {

            cs = conn.prepareCall("{call sp_InsertAnak(?,?,?,?,?,?)}");

            cs.setString(1, txtIdAnak.getText());
            cs.setString(2, cbOrangTua.getValue());
            cs.setString(3, txtNamaAnak.getText());
            cs.setDate(4, Date.valueOf(dpTanggalLahir.getValue()));
            cs.setString(5, cbJenisKelamin.getValue());
            cs.setString(6, txtCatatan.getText());

            cs.executeUpdate();

            new Alert(Alert.AlertType.INFORMATION,
                    "Data berhasil disimpan.")
                    .showAndWait();

            loadTable();
            clearForm();

        } catch (Exception e) {

            new Alert(Alert.AlertType.ERROR,
                    e.getMessage())
                    .showAndWait();

        }

    }

    @FXML
    private void ubah(ActionEvent event) {

        if (!validation())
            return;

        try {

            cs = conn.prepareCall("{call sp_UpdateAnak(?,?,?,?,?,?)}");

            cs.setString(1, txtIdAnak.getText());
            cs.setString(2, cbOrangTua.getValue());
            cs.setString(3, txtNamaAnak.getText());
            cs.setDate(4, Date.valueOf(dpTanggalLahir.getValue()));
            cs.setString(5, cbJenisKelamin.getValue());
            cs.setString(6, txtCatatan.getText());

            cs.executeUpdate();

            new Alert(Alert.AlertType.INFORMATION,
                    "Data berhasil diubah.")
                    .showAndWait();

            loadTable();

            clearForm();

        } catch (Exception e) {

            new Alert(Alert.AlertType.ERROR,
                    e.getMessage())
                    .showAndWait();

        }

    }

    @FXML
    private void hapus(ActionEvent event) {

        MasterAnak anak =
                tblAnak.getSelectionModel().getSelectedItem();

        if (anak == null) {

            new Alert(Alert.AlertType.WARNING,
                    "Pilih data terlebih dahulu.")
                    .showAndWait();

            return;

        }

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION);

        alert.setTitle("Konfirmasi");

        alert.setHeaderText(null);

        alert.setContentText(
                "Yakin ingin menghapus data ini?");

        if (alert.showAndWait().get() == ButtonType.OK) {

            try {

                cs = conn.prepareCall("{call sp_DeleteAnak(?)}");

                cs.setString(1, txtIdAnak.getText());

                cs.executeUpdate();

                new Alert(Alert.AlertType.INFORMATION,
                        "Data berhasil dihapus.")
                        .showAndWait();

                loadTable();

                clearForm();

            } catch (Exception e) {

                new Alert(Alert.AlertType.ERROR,
                        e.getMessage())
                        .showAndWait();

            }

        }

    }

    @FXML
    private void cari(ActionEvent event) {

        listAnak.clear();

        try {

            String sql =
                    "SELECT * FROM Anak WHERE ID_Anak LIKE ?";

            ps = conn.prepareStatement(sql);

            ps.setString(1,
                    "%" + txtCariId.getText() + "%");

            rs = ps.executeQuery();

            while (rs.next()) {

                listAnak.add(

                        new MasterAnak(
                                rs.getString("ID_Anak"),
                                rs.getString("ID_OrangTua"),
                                rs.getString("Nama_Anak"),
                                rs.getDate("Tanggal_Lahir"),
                                rs.getString("Jenis_Kelamin").charAt(0),
                                rs.getString("Catatan_Khusus")
                        )
                );

            }

            tblAnak.setItems(listAnak);

            lblTotalData.setText(
                    listAnak.size() + " Data");

        } catch (Exception e) {

            new Alert(Alert.AlertType.ERROR,
                    e.getMessage())
                    .showAndWait();

        }

    }

    private boolean validation() {

        if (cbOrangTua.getValue() == null) {

            new Alert(Alert.AlertType.WARNING,
                    "ID Orang Tua harus dipilih.")
                    .showAndWait();

            return false;

        }

        if (txtNamaAnak.getText().trim().isEmpty()) {

            new Alert(Alert.AlertType.WARNING,
                    "Nama Anak masih kosong.")
                    .showAndWait();

            return false;

        }

        if (dpTanggalLahir.getValue() == null) {

            new Alert(Alert.AlertType.WARNING,
                    "Tanggal lahir belum dipilih.")
                    .showAndWait();

            return false;

        }

        if (cbJenisKelamin.getValue() == null) {

            new Alert(Alert.AlertType.WARNING,
                    "Jenis kelamin belum dipilih.")
                    .showAndWait();

            return false;

        }

        return true

                ;

    }


    @FXML
    private void kembali(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/DashboardAdmin/DashboardAdmin.fxml"));

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) btnKembali.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Dashboard Admin");
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Gagal membuka Dashboard Admin");
            alert.showAndWait();
        }
    }
}
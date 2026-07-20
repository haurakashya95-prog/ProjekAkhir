package com.example.kidsdaycareactivitycenter.TransaksiDaycare;

import com.example.kidsdaycareactivitycenter.Database.DBConnect;
import com.example.kidsdaycareactivitycenter.Model.TransaksiDaycare;

import java.time.LocalDate;
import javafx.scene.control.ButtonType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;

public class TransaksiDaycareController {

    //==================== FORM ====================

    @FXML
    private TextField txtIDDaycare;

    @FXML
    private ComboBox<String> cmbAnak;

    @FXML
    private ComboBox<String> cmbPaket;

    @FXML
    private ComboBox<String> cmbKaryawan;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private DatePicker dpTanggalMulai;

    @FXML
    private DatePicker dpTanggalSelesai;

    //==================== BUTTON ====================

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

    @FXML
    private Button btnCari;

    //==================== SEARCH ====================

    @FXML
    private TextField txtCari;

    //==================== TABLE ====================

    @FXML
    private TableView<TransaksiDaycare> tblTransaksi;

    @FXML
    private TableColumn<TransaksiDaycare, String> colIDDaycare;

    @FXML
    private TableColumn<TransaksiDaycare, String> colNamaAnak;

    @FXML
    private TableColumn<TransaksiDaycare, String> colKategori;

    @FXML
    private TableColumn<TransaksiDaycare, String> colJenisPaket;

    @FXML
    private TableColumn<TransaksiDaycare, String> colKaryawan;

    @FXML
    private TableColumn<TransaksiDaycare, String> colTanggalMulai;

    @FXML
    private TableColumn<TransaksiDaycare, String> colTanggalSelesai;

    @FXML
    private TableColumn<TransaksiDaycare, String> colStatus;

    //==================== LABEL ====================

    @FXML
    private Label lblTotalData;

    @FXML
    private Label lblStatus;

    //==================== LIST ====================

    ObservableList<TransaksiDaycare> data =
            FXCollections.observableArrayList();

    //==================== INITIALIZE ====================

    @FXML
    public void initialize() {

        setupTable();

        loadAnak();

        loadPaket();

        loadKaryawan();

        loadStatus();

        loadTable();

        generateID();

        setupSelectionListener();

        updateStatistic();

        lblStatus.setText("Siap Digunakan");

    }

    //==================== TABLE ====================

    private void setupTable() {

        colIDDaycare.setCellValueFactory(
                new PropertyValueFactory<>("idDaycare"));

        colNamaAnak.setCellValueFactory(
                new PropertyValueFactory<>("namaAnak"));

        colKategori.setCellValueFactory(
                new PropertyValueFactory<>("kategoriDaycare"));

        colJenisPaket.setCellValueFactory(
                new PropertyValueFactory<>("jenisPaket"));

        colKaryawan.setCellValueFactory(
                new PropertyValueFactory<>("namaKaryawan"));

        colTanggalMulai.setCellValueFactory(
                new PropertyValueFactory<>("tanggalMulai"));

        colTanggalSelesai.setCellValueFactory(
                new PropertyValueFactory<>("tanggalSelesai"));

        colStatus.setCellValueFactory(
                new PropertyValueFactory<>("statusDaycare"));

        tblTransaksi.setItems(data);

    }
    //==================== GENERATE ID ====================

    private void generateID() {

        try {

            DBConnect db = new DBConnect();

            String sql =
                    "SELECT TOP 1 ID_Daycare " +
                            "FROM Transaksi_Daycare " +
                            "ORDER BY ID_Daycare DESC";

            db.pstat = db.conn.prepareStatement(sql);
            db.result = db.pstat.executeQuery();

            if (db.result.next()) {

                String id = db.result.getString("ID_Daycare");

                int nomor = Integer.parseInt(id.substring(2));

                nomor++;

                txtIDDaycare.setText(
                        String.format("DY%03d", nomor)
                );

            } else {

                txtIDDaycare.setText("DY001");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    //==================== LOAD ANAK ====================

    private void loadAnak() {

        cmbAnak.getItems().clear();

        try {

            DBConnect db = new DBConnect();

            String sql =
                    "SELECT ID_Anak, Nama_Anak " +
                            "FROM Anak " +
                            "ORDER BY Nama_Anak";

            db.pstat = db.conn.prepareStatement(sql);

            db.result = db.pstat.executeQuery();

            while (db.result.next()) {

                cmbAnak.getItems().add(

                        db.result.getString("ID_Anak")
                                + " - "
                                + db.result.getString("Nama_Anak")

                );

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    //==================== LOAD PAKET ====================

    private void loadPaket() {

        cmbPaket.getItems().clear();

        try {

            DBConnect db = new DBConnect();

            String sql =
                    "SELECT ID_Paket " +
                            "FROM Paket_Daycare " +
                            "ORDER BY ID_Paket";

            db.pstat = db.conn.prepareStatement(sql);

            db.result = db.pstat.executeQuery();

            while (db.result.next()) {

                cmbPaket.getItems().add(

                        db.result.getString("ID_Paket")

                );

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    //==================== LOAD KARYAWAN ====================

    private void loadKaryawan() {

        cmbKaryawan.getItems().clear();

        try {

            DBConnect db = new DBConnect();

            String sql =
                    "SELECT ID_Karyawan, Nama_Karyawan " +
                            "FROM Karyawan " +
                            "ORDER BY Nama_Karyawan";

            db.pstat = db.conn.prepareStatement(sql);

            db.result = db.pstat.executeQuery();

            while (db.result.next()) {

                cmbKaryawan.getItems().add(

                        db.result.getString("ID_Karyawan")
                                + " - "
                                + db.result.getString("Nama_Karyawan")

                );

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    //==================== LOAD STATUS ====================

    private void loadStatus() {

        cmbStatus.getItems().clear();

        cmbStatus.getItems().add("Aktif");
        cmbStatus.getItems().add("Selesai");
        cmbStatus.getItems().add("Batal");

    }

    //==================== LOAD TABLE ====================

    private void loadTable() {

        data.clear();

        try {

            DBConnect db = new DBConnect();

            db.pstat = db.conn.prepareStatement(
                    "EXEC sp_ShowTransaksiDaycare"
            );

            db.result = db.pstat.executeQuery();

            while (db.result.next()) {

                TransaksiDaycare transaksi = new TransaksiDaycare();

                transaksi.setIdDaycare(
                        db.result.getString("ID_Daycare"));

                transaksi.setIdAnak(
                        db.result.getString("ID_Anak"));

                transaksi.setNamaAnak(
                        db.result.getString("Nama_Anak"));

                transaksi.setIdPaket(
                        db.result.getString("ID_Paket"));

                transaksi.setKategoriDaycare(
                        db.result.getString("Kategori_Daycare"));

                transaksi.setKategoriUsia(
                        db.result.getString("Kategori_Usia"));

                transaksi.setJenisPaket(
                        db.result.getString("Jenis_Paket"));

                transaksi.setIdKaryawan(
                        db.result.getString("ID_Karyawan"));

                transaksi.setNamaKaryawan(
                        db.result.getString("Nama_Karyawan"));

                transaksi.setTanggalMulai(
                        db.result.getString("Tanggal_Mulai"));

                transaksi.setTanggalSelesai(
                        db.result.getString("Tanggal_Selesai"));

                transaksi.setStatusDaycare(
                        db.result.getString("Status_Daycare"));

                data.add(transaksi);

            }

            tblTransaksi.setItems(data);

            tblTransaksi.refresh();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

//==================== SELECTION LISTENER ====================

    private void setupSelectionListener() {

        tblTransaksi.getSelectionModel().selectedItemProperty().addListener(

                (observable, oldValue, selected) -> {

                    if (selected != null) {

                        txtIDDaycare.setText(
                                selected.getIdDaycare());

                        // Combo Anak
                        for (String item : cmbAnak.getItems()) {

                            if (item.startsWith(selected.getIdAnak())) {

                                cmbAnak.setValue(item);

                                break;

                            }

                        }

                        // Combo Paket
                        cmbPaket.setValue(
                                selected.getIdPaket());

                        // Combo Karyawan
                        for (String item : cmbKaryawan.getItems()) {

                            if (item.startsWith(selected.getIdKaryawan())) {

                                cmbKaryawan.setValue(item);

                                break;

                            }

                        }

                        // DatePicker Mulai
                        dpTanggalMulai.setValue(
                                java.time.LocalDate.parse(
                                        selected.getTanggalMulai()));

                        // DatePicker Selesai
                        dpTanggalSelesai.setValue(
                                java.time.LocalDate.parse(
                                        selected.getTanggalSelesai()));

                        // Status
                        cmbStatus.setValue(
                                selected.getStatusDaycare());

                    }

                }

        );

    }

//==================== UPDATE STATISTIK ====================

    private void updateStatistic() {

        lblTotalData.setText(
                String.valueOf(data.size())
        );

    }

    //==================== SIMPAN ====================

    @FXML
    private void simpan() {

        try {

            DBConnect db = new DBConnect();

            String sql =
                    "EXEC sp_InsertTransaksiDaycare ?,?,?,?,?,?,?";

            db.pstat = db.conn.prepareStatement(sql);

            db.pstat.setString(1,
                    txtIDDaycare.getText());

            db.pstat.setString(2,
                    cmbAnak.getValue().split(" - ")[0]);

            db.pstat.setString(3,
                    cmbPaket.getValue());

            db.pstat.setString(4,
                    cmbKaryawan.getValue().split(" - ")[0]);

            db.pstat.setDate(5,
                    java.sql.Date.valueOf(
                            dpTanggalMulai.getValue()));

            db.pstat.setDate(6,
                    java.sql.Date.valueOf(
                            dpTanggalSelesai.getValue()));

            db.pstat.setString(7,
                    cmbStatus.getValue());

            db.pstat.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Informasi");

            alert.setHeaderText(null);

            alert.setContentText("Data Transaksi Daycare berhasil disimpan.");

            alert.showAndWait();

            loadTable();

            generateID();

            batal();

            updateStatistic();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Database Error");

            alert.setHeaderText("Gagal Menyimpan");

            alert.setContentText(e.getMessage());

            alert.showAndWait();

        }

    }
    //==================== UBAH ====================

    @FXML
    private void ubah() {

        try {

            DBConnect db = new DBConnect();

            String sql =
                    "EXEC sp_UpdateTransaksiDaycare ?,?,?,?,?,?,?";

            db.pstat = db.conn.prepareStatement(sql);

            db.pstat.setString(1,
                    txtIDDaycare.getText());

            db.pstat.setString(2,
                    cmbAnak.getValue().split(" - ")[0]);

            db.pstat.setString(3,
                    cmbPaket.getValue());

            db.pstat.setString(4,
                    cmbKaryawan.getValue().split(" - ")[0]);

            db.pstat.setDate(5,
                    java.sql.Date.valueOf(
                            dpTanggalMulai.getValue()));

            db.pstat.setDate(6,
                    java.sql.Date.valueOf(
                            dpTanggalSelesai.getValue()));

            db.pstat.setString(7,
                    cmbStatus.getValue());

            db.pstat.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Informasi");
            alert.setHeaderText(null);
            alert.setContentText("Data berhasil diubah.");

            alert.showAndWait();

            loadTable();

            batal();

            generateID();

            updateStatistic();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Database Error");
            alert.setHeaderText("Gagal Mengubah");
            alert.setContentText(e.getMessage());

            alert.showAndWait();

        }

    }
    //==================== HAPUS ====================

    @FXML
    private void hapus() {

        try {

            if (txtIDDaycare.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);

                alert.setTitle("Peringatan");
                alert.setHeaderText(null);
                alert.setContentText("Pilih data yang akan dihapus!");

                alert.showAndWait();

                return;

            }

            Alert konfirmasi = new Alert(Alert.AlertType.CONFIRMATION);

            konfirmasi.setTitle("Konfirmasi");

            konfirmasi.setHeaderText(null);

            konfirmasi.setContentText(
                    "Apakah Anda yakin ingin menghapus data ini?"
            );

            if (konfirmasi.showAndWait().get() == ButtonType.OK) {

                DBConnect db = new DBConnect();

                String sql =
                        "EXEC sp_DeleteTransaksiDaycare ?";

                db.pstat = db.conn.prepareStatement(sql);

                db.pstat.setString(1,
                        txtIDDaycare.getText());

                db.pstat.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Informasi");

                alert.setHeaderText(null);

                alert.setContentText("Data berhasil dihapus.");

                alert.showAndWait();

                loadTable();

                batal();

                generateID();

                updateStatistic();

            }

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Database Error");

            alert.setHeaderText("Gagal Menghapus");

            alert.setContentText(e.getMessage());

            alert.showAndWait();

        }

    }
    //==================== CARI ====================

    @FXML
    private void cari() {

        data.clear();

        try {

            DBConnect db = new DBConnect();

            String sql =
                    "EXEC sp_SearchTransaksiDaycare ?";

            db.pstat = db.conn.prepareStatement(sql);

            db.pstat.setString(1,
                    txtCari.getText());

            db.result = db.pstat.executeQuery();

            while (db.result.next()) {

                TransaksiDaycare transaksi = new TransaksiDaycare();

                transaksi.setIdDaycare(
                        db.result.getString("ID_Daycare"));

                transaksi.setIdAnak(
                        db.result.getString("ID_Anak"));

                transaksi.setNamaAnak(
                        db.result.getString("Nama_Anak"));

                transaksi.setIdPaket(
                        db.result.getString("ID_Paket"));

                transaksi.setKategoriDaycare(
                        db.result.getString("Kategori_Daycare"));

                transaksi.setKategoriUsia(
                        db.result.getString("Kategori_Usia"));

                transaksi.setJenisPaket(
                        db.result.getString("Jenis_Paket"));

                transaksi.setIdKaryawan(
                        db.result.getString("ID_Karyawan"));

                transaksi.setNamaKaryawan(
                        db.result.getString("Nama_Karyawan"));

                transaksi.setTanggalMulai(
                        db.result.getString("Tanggal_Mulai"));

                transaksi.setTanggalSelesai(
                        db.result.getString("Tanggal_Selesai"));

                transaksi.setStatusDaycare(
                        db.result.getString("Status_Daycare"));

                data.add(transaksi);

            }

            tblTransaksi.setItems(data);

            tblTransaksi.refresh();

            updateStatistic();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Database Error");

            alert.setHeaderText("Pencarian Gagal");

            alert.setContentText(e.getMessage());

            alert.showAndWait();

        }

    }

//==================== BATAL ====================

    @FXML
    private void batal() {

        txtIDDaycare.clear();

        cmbAnak.getSelectionModel().clearSelection();
        cmbPaket.getSelectionModel().clearSelection();
        cmbKaryawan.getSelectionModel().clearSelection();
        cmbStatus.getSelectionModel().clearSelection();

        dpTanggalMulai.setValue(null);
        dpTanggalSelesai.setValue(null);

        txtCari.clear();

    }

    //==================== KEMBALI ====================
    @FXML
    private void kembali() {
        try {
            Stage stage = (Stage) btnKembali.getScene().getWindow();

            Parent root = FXMLLoader.load(
                    getClass().getResource("/com/example/kidsdaycareactivitycenter/DashboardAdmin/DashboardAdmin.fxml")
            );

            stage.setScene(new Scene(root));
            stage.setTitle("Kids Daycare Activity Center - Dashboard Admin");
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
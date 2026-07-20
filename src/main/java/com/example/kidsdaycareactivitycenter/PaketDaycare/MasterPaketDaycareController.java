package com.example.kidsdaycareactivitycenter.PaketDaycare;

import com.example.kidsdaycareactivitycenter.Database.DBConnect;
import com.example.kidsdaycareactivitycenter.Model.PaketDaycare;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MasterPaketDaycareController {

    // ================= FORM =================

    @FXML
    private TextField txtIDPaket;

    @FXML
    private ComboBox<String> cmbKategoriDaycare;

    @FXML
    private ComboBox<String> cmbKategoriUsia;

    @FXML
    private ComboBox<String> cmbJenisPaket;

    @FXML
    private ComboBox<String> cmbHariOperasional;

    @FXML
    private TextField txtJamMasuk;

    @FXML
    private TextField txtJamPulang;

    @FXML
    private TextField txtHarga;

    @FXML
    private TextField txtCari;

    @FXML
    private Button btnSimpan;

    @FXML
    private Button btnKembali;

    // ================= TABLE =================

    @FXML
    private TableView<PaketDaycare> tblPaket;

    @FXML
    private TableColumn<PaketDaycare,String> colIDPaket;

    @FXML
    private TableColumn<PaketDaycare,String> colKategoriDaycare;

    @FXML
    private TableColumn<PaketDaycare,String> colKategoriUsia;

    @FXML
    private TableColumn<PaketDaycare,String> colJenisPaket;

    @FXML
    private TableColumn<PaketDaycare,String> colHariOperasional;

    @FXML
    private TableColumn<PaketDaycare,String> colJamMasuk;

    @FXML
    private TableColumn<PaketDaycare,String> colJamPulang;

    @FXML
    private TableColumn<PaketDaycare,Double> colHarga;

    // ================= LABEL =================

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblTotalData;

    // ================= DATA =================

    private final ObservableList<PaketDaycare> data =
            FXCollections.observableArrayList();

    // ================= INITIALIZE =================

    @FXML
    public void initialize() {

        System.out.println("=== INITIALIZE PAKET DAYCARE ===");

        setupCombo();
        setupTable();
        setupSelectionListener();   // <-- TAMBAHKAN INI
        loadTable();
        generateID();
        updateStatistic();

        lblStatus.setText("Siap Digunakan");
    }

    // ================= COMBO =================

    private void setupCombo() {

        cmbKategoriDaycare.getItems().addAll(
                "Baby",
                "Toddler",
                "Preschool"
        );

        cmbKategoriUsia.getItems().addAll(
                "3-12 Bulan",
                "1-3 Tahun",
                "3-4 Tahun"
        );

        cmbJenisPaket.getItems().addAll(
                "Harian",
                "Mingguan",
                "Bulanan"
        );

        cmbHariOperasional.getItems().addAll(
                "Senin-Jumat",
                "Senin-Sabtu"
        );

    }

    // ================= TABLE =================

    private void setupTable() {

        colIDPaket.setCellValueFactory(new PropertyValueFactory<>("idPaket"));
        colKategoriDaycare.setCellValueFactory(new PropertyValueFactory<>("kategoriDaycare"));
        colKategoriUsia.setCellValueFactory(new PropertyValueFactory<>("kategoriUsia"));
        colJenisPaket.setCellValueFactory(new PropertyValueFactory<>("jenisPaket"));
        colHariOperasional.setCellValueFactory(new PropertyValueFactory<>("hariOperasional"));
        colJamMasuk.setCellValueFactory(new PropertyValueFactory<>("jamMasuk"));
        colJamPulang.setCellValueFactory(new PropertyValueFactory<>("jamPulang"));

        // ===== FORMAT KOLOM HARGA =====
        colHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));

        colHarga.setCellFactory(column -> new TableCell<PaketDaycare, Double>() {

            @Override
            protected void updateItem(Double harga, boolean empty) {
                super.updateItem(harga, empty);

                if (empty || harga == null) {
                    setText(null);
                } else {

                    NumberFormat format =
                            NumberFormat.getCurrencyInstance(new Locale("in", "ID"));

                    format.setMinimumFractionDigits(2);
                    format.setMaximumFractionDigits(2);

                    setText(format.format(harga));

                }

                setAlignment(Pos.CENTER_RIGHT);
                setStyle("-fx-padding: 0 10 0 0;");

            }
        });

        tblPaket.setItems(data);

    }
    // ================= PILIH DATA =================

    private void setupSelectionListener() {

        tblPaket.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selected) -> {

                    if (selected != null) {

                        txtIDPaket.setText(selected.getIdPaket());

                        cmbKategoriDaycare.setValue(
                                selected.getKategoriDaycare());

                        cmbKategoriUsia.setValue(
                                selected.getKategoriUsia());

                        cmbJenisPaket.setValue(
                                selected.getJenisPaket());

                        cmbHariOperasional.setValue(
                                selected.getHariOperasional());

                        txtJamMasuk.setText(
                                selected.getJamMasuk());

                        txtJamPulang.setText(
                                selected.getJamPulang());

                        txtHarga.setText(
                                String.valueOf((long) selected.getHarga()));

                        btnSimpan.setDisable(true);

                        lblStatus.setText("Data dipilih");

                    }

                });

    }

    // ================= GENERATE ID =================

    private void generateID() {

        try {

            DBConnect db = new DBConnect();

            String sql = """
                    SELECT TOP 1 ID_Paket
                    FROM Paket_Daycare
                    ORDER BY ID_Paket DESC
                    """;

            db.pstat = db.conn.prepareStatement(sql);

            db.result = db.pstat.executeQuery();

            if (db.result.next()) {

                String id = db.result.getString("ID_Paket");

                int angka =
                        Integer.parseInt(id.substring(3));

                angka++;

                txtIDPaket.setText(
                        String.format("PKT%03d", angka));

            } else {

                txtIDPaket.setText("PKT001");

            }

        } catch (Exception e) {

            txtIDPaket.setText("PKT001");

        }

    }

    // ================= LOAD TABLE =================

    private void loadTable() {

        data.clear();

        try {

            DBConnect db = new DBConnect();

            db.pstat = db.conn.prepareStatement(
                    "EXEC sp_ShowPaketDaycare"
            );

            db.result = db.pstat.executeQuery();

            while (db.result.next()) {

                PaketDaycare paket = new PaketDaycare();

                paket.setIdPaket(db.result.getString("ID_Paket"));
                paket.setKategoriDaycare(db.result.getString("Kategori_Daycare"));
                paket.setKategoriUsia(db.result.getString("Kategori_Usia"));
                paket.setJenisPaket(db.result.getString("Jenis_Paket"));
                paket.setHariOperasional(db.result.getString("Hari_Operasional"));
                paket.setJamMasuk(db.result.getString("Jam_Masuk"));
                paket.setJamPulang(db.result.getString("Jam_Pulang"));
                paket.setHarga(db.result.getDouble("Harga"));

                data.add(paket);

            }

            // TARUH DI SINI
            tblPaket.setItems(data);
            tblPaket.refresh();

            System.out.println("Jumlah Data = " + data.size());

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
// ================= VALIDASI =================

    private boolean validasiInput() {

        if (cmbKategoriDaycare.getValue() == null) {

            lblStatus.setText("Kategori Daycare belum dipilih");
            return false;

        }

        if (cmbKategoriUsia.getValue() == null) {

            lblStatus.setText("Kategori Usia belum dipilih");
            return false;

        }

        if (cmbJenisPaket.getValue() == null) {

            lblStatus.setText("Jenis Paket belum dipilih");
            return false;

        }

        if (cmbHariOperasional.getValue() == null) {

            lblStatus.setText("Hari Operasional belum dipilih");
            return false;

        }

        if (txtJamMasuk.getText().isEmpty()) {

            lblStatus.setText("Jam Masuk belum diisi");
            return false;

        }

        if (txtJamPulang.getText().isEmpty()) {

            lblStatus.setText("Jam Pulang belum diisi");
            return false;

        }

        if (txtHarga.getText().isEmpty()) {

            lblStatus.setText("Harga belum diisi");
            return false;

        }

        return true;

    }

// ================= SIMPAN =================

    @FXML
    private void simpan() {

        if (!validasiInput()) {
            return;
        }

        try {

            DBConnect db = new DBConnect();

            db.pstat = db.conn.prepareStatement(
                    "EXEC sp_InsertPaketDaycare ?,?,?,?,?,?,?,?"
            );

            db.pstat.setString(1, txtIDPaket.getText());
            db.pstat.setString(2, cmbKategoriDaycare.getValue());
            db.pstat.setString(3, cmbKategoriUsia.getValue());
            db.pstat.setString(4, cmbJenisPaket.getValue());
            db.pstat.setString(5, cmbHariOperasional.getValue());

            // Mengubah 08.00 menjadi 08:00 jika user mengetik titik
            db.pstat.setString(6,
                    txtJamMasuk.getText().replace(".", ":"));

            db.pstat.setString(7,
                    txtJamPulang.getText().replace(".", ":"));

            // Mengubah 250.000 menjadi 250000
            String harga = txtHarga.getText().replace(".", "");

            db.pstat.setDouble(8, Double.parseDouble(harga));

            db.pstat.executeUpdate();

            lblStatus.setText("Data berhasil disimpan");

            loadTable();

            batal();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informasi");
            alert.setHeaderText(null);
            alert.setContentText("Data berhasil disimpan.");
            alert.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Gagal Menyimpan");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

            lblStatus.setText("Gagal menyimpan data");

        }

    }

// ================= UBAH =================

    @FXML
    private void ubah() {

        if (!validasiInput()) {

            return;

        }

        try {

            DBConnect db = new DBConnect();

            db.pstat = db.conn.prepareStatement(
                    "EXEC sp_UpdatePaketDaycare ?,?,?,?,?,?,?,?"
            );

            db.pstat.setString(1, txtIDPaket.getText());
            db.pstat.setString(2, cmbKategoriDaycare.getValue());
            db.pstat.setString(3, cmbKategoriUsia.getValue());
            db.pstat.setString(4, cmbJenisPaket.getValue());
            db.pstat.setString(5, cmbHariOperasional.getValue());
            db.pstat.setString(6, txtJamMasuk.getText().replace(".", ":"));
            db.pstat.setString(7, txtJamPulang.getText().replace(".", ":"));

            // Mengubah 250.000 menjadi 250000
            String harga = txtHarga.getText().replace(".", "");

            db.pstat.setDouble(8, Double.parseDouble(harga));

            db.pstat.executeUpdate();

            lblStatus.setText("Data berhasil diubah");

            loadTable();

            batal();

        } catch (Exception e) {

            lblStatus.setText("Gagal mengubah data");

            e.printStackTrace();

        }

    }

    // ================= HAPUS =================

    @FXML
    private void hapus() {

        if (txtIDPaket.getText().isEmpty()) {
            lblStatus.setText("Pilih data yang akan dihapus");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Konfirmasi");

        alert.setHeaderText("Hapus Data");

        alert.setContentText("Apakah Anda yakin ingin menghapus data ini?");

        if (alert.showAndWait().get() == ButtonType.OK) {

            try {

                DBConnect db = new DBConnect();

                db.pstat = db.conn.prepareStatement(
                        "EXEC sp_DeletePaketDaycare ?"
                );

                db.pstat.setString(1, txtIDPaket.getText());

                db.pstat.executeUpdate();

                lblStatus.setText("Data berhasil dihapus");

                loadTable();

                batal();

            } catch (Exception e) {

                lblStatus.setText("Gagal menghapus data");

                e.printStackTrace();

            }

        }

    }

// ================= CARI =================

    @FXML
    private void cari() {

        data.clear();

        try {

            DBConnect db = new DBConnect();

            db.pstat = db.conn.prepareStatement(
                    "EXEC sp_SearchPaketDaycare ?"
            );

            db.pstat.setString(1, txtCari.getText());

            db.result = db.pstat.executeQuery();

            while (db.result.next()) {

                data.add(new PaketDaycare(

                        db.result.getString("ID_Paket"),
                        db.result.getString("Kategori_Daycare"),
                        db.result.getString("Kategori_Usia"),
                        db.result.getString("Jenis_Paket"),
                        db.result.getString("Hari_Operasional"),
                        db.result.getString("Jam_Masuk"),
                        db.result.getString("Jam_Pulang"),
                        db.result.getDouble("Harga")

                ));

            }

            tblPaket.setItems(data);

            updateStatistic();

            lblStatus.setText("Pencarian selesai");

        } catch (Exception e) {

            lblStatus.setText("Pencarian gagal");

            e.printStackTrace();

        }

    }

// ================= BATAL =================

    @FXML
    private void batal() {

        txtIDPaket.clear();

        cmbKategoriDaycare.getSelectionModel().clearSelection();

        cmbKategoriUsia.getSelectionModel().clearSelection();

        cmbJenisPaket.getSelectionModel().clearSelection();

        cmbHariOperasional.getSelectionModel().clearSelection();

        txtJamMasuk.clear();

        txtJamPulang.clear();

        txtHarga.clear();

        txtCari.clear();

        btnSimpan.setDisable(false);

        generateID();

        lblStatus.setText("Form berhasil dibersihkan");

    }

// ================= KEMBALI =================

    @FXML
    private void kembali() {

        var url = getClass().getResource("/DashboardAdmin/DashboardAdmin.fxml");

        System.out.println("HASIL URL = " + url);

        try {

            FXMLLoader loader = new FXMLLoader(url);

            Parent root = loader.load();

            Stage stage = (Stage) btnKembali.getScene().getWindow();

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Dashboard Admin");
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Gagal Kembali ke Dashboard");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }

    }
// ================= UPDATE STATISTIK =================

    private void updateStatistic() {

        lblTotalData.setText(String.valueOf(data.size()));

    }
}
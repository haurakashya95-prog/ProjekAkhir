package com.example.kidsdaycareactivitycenter.ActivitySchedule;

import com.example.kidsdaycareactivitycenter.Model.ActivitySchedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.kidsdaycareactivitycenter.Database.DBConnect;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;

public class MasterActivityScheduleController {

    @FXML private TextField txtID;
    @FXML private ComboBox<String> cmbPaket;
    @FXML private ComboBox<String> cmbHari;
    @FXML private TextField txtJamMulai;
    @FXML private TextField txtJamSelesai;
    @FXML private TextField txtTema;
    @FXML private TextField txtDeskripsi;
    @FXML private TextField txtCari;
    @FXML private Button btnKembali;

    @FXML
    private TableView<ActivitySchedule> tblActivity;

    @FXML
    private TableColumn<ActivitySchedule,String> colID;
    @FXML
    private TableColumn<ActivitySchedule,String> colPaket;
    @FXML
    private TableColumn<ActivitySchedule,String> colHari;
    @FXML
    private TableColumn<ActivitySchedule,String> colJamMulai;
    @FXML
    private TableColumn<ActivitySchedule,String> colJamSelesai;
    @FXML
    private TableColumn<ActivitySchedule,String> colTema;
    @FXML
    private TableColumn<ActivitySchedule,String> colDeskripsi;

    @FXML
    private Label lblStatus;
    @FXML
    private Label lblTotalData;

    private final ObservableList<ActivitySchedule> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupCombo();
        setupTable();
        setupSelectionListener();
        loadPaket();
        loadTable();
        generateID();
        updateStatistic();
        lblStatus.setText("Siap Digunakan");
    }

    private void setupCombo() {

        cmbHari.getItems().addAll(
                "Senin",
                "Selasa",
                "Rabu",
                "Kamis",
                "Jumat",
                "Sabtu"
        );

    }
    private void setupTable() {

        colID.setCellValueFactory(new PropertyValueFactory<>("idActivity"));
        colPaket.setCellValueFactory(new PropertyValueFactory<>("idPaket"));
        colHari.setCellValueFactory(new PropertyValueFactory<>("hari"));
        colJamMulai.setCellValueFactory(new PropertyValueFactory<>("jamMulai"));
        colJamSelesai.setCellValueFactory(new PropertyValueFactory<>("jamSelesai"));
        colTema.setCellValueFactory(new PropertyValueFactory<>("temaActivity"));
        colDeskripsi.setCellValueFactory(new PropertyValueFactory<>("deskripsiActivity"));

        tblActivity.setItems(data);
    }

    private void setupSelectionListener() {

        tblActivity.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selected) -> {

                    if (selected != null) {

                        txtID.setText(selected.getIdActivity());
                        cmbPaket.setValue(selected.getIdPaket());
                        cmbHari.setValue(selected.getHari());
                        txtJamMulai.setText(selected.getJamMulai());
                        txtJamSelesai.setText(selected.getJamSelesai());
                        txtTema.setText(selected.getTemaActivity());
                        txtDeskripsi.setText(selected.getDeskripsiActivity());

                    }
                });
    }

    private void loadPaket() {

        cmbPaket.getItems().clear();

        try {

            DBConnect db = new DBConnect();

            String sql = """
                SELECT ID_Paket
                FROM Paket_Daycare
                ORDER BY ID_Paket
                """;

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

    private String formatJam(String jam) {

        if (jam == null) {
            return "";
        }

        if (jam.length() >= 5) {
            return jam.substring(0, 5);
        }

        return jam;

    }

    private void loadTable() {

        data.clear();

        try {

            DBConnect db = new DBConnect();

            db.pstat = db.conn.prepareStatement(
                    "EXEC sp_ShowActivitySchedule"
            );

            db.result = db.pstat.executeQuery();

            while (db.result.next()) {

                data.add(new ActivitySchedule(

                        db.result.getString("ID_Activity"),
                        db.result.getString("ID_Paket"),
                        db.result.getString("Hari"),
                        formatJam(db.result.getString("Jam_Mulai")),
                        formatJam(db.result.getString("Jam_Selesai")),
                        db.result.getString("Tema_Activity"),
                        db.result.getString("Deskripsi_Activity")

                ));

            }

            tblActivity.setItems(data);

            updateStatistic();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void generateID() {

        try {

            DBConnect db = new DBConnect();

            String sql = """
                SELECT TOP 1 ID_Activity
                FROM Activity_Schedule
                ORDER BY ID_Activity DESC
                """;

            db.pstat = db.conn.prepareStatement(sql);
            db.result = db.pstat.executeQuery();

            if (db.result.next()) {

                String id = db.result.getString("ID_Activity");

                int angka = Integer.parseInt(id.substring(3));

                angka++;

                txtID.setText(String.format("ACT%03d", angka));

            } else {

                txtID.setText("ACT001");

            }

        } catch (Exception e) {

            txtID.setText("ACT001");

        }

    }

    @FXML
    private void simpan() {

        try {

            DBConnect db = new DBConnect();

            db.pstat = db.conn.prepareStatement(
                    "EXEC sp_InsertActivitySchedule ?,?,?,?,?,?,?"
            );

            db.pstat.setString(1, txtID.getText());
            db.pstat.setString(2, cmbPaket.getValue());
            db.pstat.setString(3, cmbHari.getValue());
            db.pstat.setString(4, txtJamMulai.getText());
            db.pstat.setString(5, txtJamSelesai.getText());
            db.pstat.setString(6, txtTema.getText());
            db.pstat.setString(7, txtDeskripsi.getText());

            db.pstat.executeUpdate();

            lblStatus.setText("Data berhasil disimpann");

            loadTable();

            batal();

        } catch (Exception e) {

            lblStatus.setText("Gagal menyimpan data");

            e.printStackTrace();

        }

    }

    @FXML
    private void ubah() {

        try {

            DBConnect db = new DBConnect();

            db.pstat = db.conn.prepareStatement(
                    "EXEC sp_UpdateActivitySchedule ?,?,?,?,?,?,?"
            );

            db.pstat.setString(1, txtID.getText());
            db.pstat.setString(2, cmbPaket.getValue());
            db.pstat.setString(3, cmbHari.getValue());
            db.pstat.setString(4, txtJamMulai.getText());
            db.pstat.setString(5, txtJamSelesai.getText());
            db.pstat.setString(6, txtTema.getText());
            db.pstat.setString(7, txtDeskripsi.getText());

            db.pstat.executeUpdate();

            lblStatus.setText("Data berhasil diubah");

            loadTable();

            batal();

        } catch (Exception e) {

            lblStatus.setText("Gagal mengubah data");

            e.printStackTrace();

        }

    }

    @FXML
    private void hapus() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Konfirmasi");

        alert.setHeaderText("Hapus Data");

        alert.setContentText("Apakah Anda yakin?");

        if (alert.showAndWait().get() == ButtonType.OK) {

            try {

                DBConnect db = new DBConnect();

                db.pstat = db.conn.prepareStatement(
                        "EXEC sp_DeleteActivitySchedule ?"
                );

                db.pstat.setString(1, txtID.getText());

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

    @FXML
    private void cari() {

        data.clear();

        try {

            DBConnect db = new DBConnect();

            db.pstat = db.conn.prepareStatement(
                    "EXEC sp_SearchActivitySchedule ?"
            );

            db.pstat.setString(1, txtCari.getText());

            db.result = db.pstat.executeQuery();

            while (db.result.next()) {

                data.add(new ActivitySchedule(

                        db.result.getString("ID_Activity"),
                        db.result.getString("ID_Paket"),
                        db.result.getString("Hari"),
                        formatJam(db.result.getString("Jam_Mulai")),
                        formatJam(db.result.getString("Jam_Selesai")),
                        db.result.getString("Tema_Activity"),
                        db.result.getString("Deskripsi_Activity")

                ));

            }

            tblActivity.setItems(data);

            updateStatistic();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @FXML
    private void batal() {

        txtID.clear();

        cmbPaket.getSelectionModel().clearSelection();

        cmbHari.getSelectionModel().clearSelection();

        txtJamMulai.clear();

        txtJamSelesai.clear();

        txtTema.clear();

        txtDeskripsi.clear();

        txtCari.clear();

        generateID();

        lblStatus.setText("Form dibersihkan");

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

    private void updateStatistic() {
        lblTotalData.setText(String.valueOf(data.size()));
    }
}
package com.example.kidsdaycareactivitycenter.TransaksiDailyActivty;

import com.example.kidsdaycareactivitycenter.Database.DBConnect;
import com.example.kidsdaycareactivitycenter.Model.TransaksiDailyActivity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TransaksiDailyActivtyController implements Initializable {

    //==================== DATABASE ====================

    private final DBConnect db = new DBConnect();

    //==================== MODEL ====================

    private TransaksiDailyActivity dailyActivity;

    //==================== DATA TRANSAKSI ====================

    @FXML
    private ComboBox<String> cmbDaycare;

    @FXML
    private ComboBox<String> cmbActivity;

    @FXML
    private TextField txtCaregiver;

    @FXML
    private DatePicker dpTanggal;

    //==================== TIMELINE ====================

    @FXML
    private TextField txtCheckIn;

    @FXML
    private TextField txtSnackPagi;

    @FXML
    private TextField txtMakanSiang;

    @FXML
    private TextField txtSnackSore;

    @FXML
    private TextField txtMandi;

    @FXML
    private TextField txtTidur;

    @FXML
    private TextField txtCheckOut;

    //==================== CHECKBOX ====================

    @FXML
    private CheckBox chkCheckIn;

    @FXML
    private CheckBox chkSnackPagi;

    @FXML
    private CheckBox chkMakanSiang;

    @FXML
    private CheckBox chkSnackSore;

    @FXML
    private CheckBox chkMandi;

    @FXML
    private CheckBox chkTidur;

    @FXML
    private CheckBox chkCheckOut;

    //==================== TEXT AREA ====================

    @FXML
    private TextArea txtAktivitasHarian;

    @FXML
    private TextArea txtCatatan;

    //==================== LABEL ====================

    @FXML
    private Label lblIDAnak;

    @FXML
    private Label lblNamaAnak;

    @FXML
    private Label lblStatusAnak;

    @FXML
    private Label lblNamaCaregiver;

    @FXML
    private Label lblTanggalJam;

    @FXML
    private Label lblStatus;

    //==================== BUTTON ====================

    @FXML
    private Button btnSimpan;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnCheckIn;

    @FXML
    private Button btnDailyActivity;

    @FXML
    private Button btnCheckOut;

    //==================== INITIALIZE ====================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("🚀 Initializing TransaksiDailyActivity...");

        dpTanggal.setValue(LocalDate.now());

        loadDaycare();

        loadActivity();

        loadCaregiver();

        autoTime();

        setupDaycareListener();

        setupButtonHandlers();

        System.out.println("✅ TransaksiDailyActivity berhasil di-load");

    }

    //==================== SETUP BUTTON HANDLERS ====================

    private void setupButtonHandlers() {

        btnSimpan.setOnAction(e -> btnSimpanOnAction());

        btnReset.setOnAction(e -> btnResetOnAction());

        btnBack.setOnAction(e -> btnBackOnAction());

        btnLogout.setOnAction(e -> btnLogoutOnAction());

    }

    //==================== LOAD DATA TRANSAKSI DAYCARE ====================

    private void loadDaycare() {

        try {

            System.out.println("📂 Loading Daycare data...");

            cmbDaycare.getItems().clear();

            String sql = "SELECT ID_Daycare FROM Transaksi_Daycare ORDER BY ID_Daycare";

            db.stat = db.conn.createStatement();
            db.result = db.stat.executeQuery(sql);

            int count = 0;

            while (db.result.next()) {

                cmbDaycare.getItems().add(
                        db.result.getString("ID_Daycare")
                );

                count++;

            }

            System.out.println("✅ Daycare data loaded: " + count + " item");

            db.stat.close();
            db.result.close();

        } catch (Exception e) {

            System.out.println("❌ Error loading Daycare: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memuat data daycare: " + e.getMessage());
            e.printStackTrace();

        }

    }

    //==================== LOAD ACTIVITY SCHEDULE ====================

    private void loadActivity() {

        try {

            System.out.println("📂 Loading Activity Schedule data...");

            cmbActivity.getItems().clear();

            String sql = "SELECT ID_Activity FROM Activity_Schedule ORDER BY ID_Activity";

            db.stat = db.conn.createStatement();
            db.result = db.stat.executeQuery(sql);

            int count = 0;

            while (db.result.next()) {

                cmbActivity.getItems().add(
                        db.result.getString("ID_Activity")
                );

                count++;

            }

            System.out.println("✅ Activity Schedule data loaded: " + count + " item");

            db.stat.close();
            db.result.close();

        } catch (Exception e) {

            System.out.println("❌ Error loading Activity Schedule: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memuat jadwal kegiatan: " + e.getMessage());
            e.printStackTrace();

        }

    }

    //==================== LOAD CAREGIVER ====================

    private void loadCaregiver() {

        /*
           Jika project kamu sudah mempunyai CurrentUser
           nanti method ini tinggal diganti menjadi:

           txtCaregiver.setText(CurrentUser.getNama());
           lblNamaCaregiver.setText(CurrentUser.getNama());

        */

        txtCaregiver.setText("Caregiver");

        if (lblNamaCaregiver != null) {
            lblNamaCaregiver.setText("Caregiver");
        }

        System.out.println("👤 Caregiver: Caregiver");

    }

    //==================== GENERATE ID DAILY ACTIVITY ====================

    private String generateID() {

        String id = "DA001";

        try {

            String sql =
                    "SELECT TOP 1 ID_Daily_Activity " +
                            "FROM Transaksi_Daily_Activity " +
                            "ORDER BY ID_Daily_Activity DESC";

            db.stat = db.conn.createStatement();
            db.result = db.stat.executeQuery(sql);

            if (db.result.next()) {

                String oldID = db.result.getString("ID_Daily_Activity");

                int angka = Integer.parseInt(oldID.substring(2));

                angka++;

                id = String.format("DA%03d", angka);

            }

            db.stat.close();
            db.result.close();

            System.out.println("🆔 Generated ID: " + id);

        } catch (Exception e) {

            System.out.println("❌ Error generating ID: " + e.getMessage());
            e.printStackTrace();

        }

        return id;

    }

    //==================== CLEAR FORM ====================

    private void clearForm() {

        cmbDaycare.getSelectionModel().clearSelection();

        cmbActivity.getSelectionModel().clearSelection();

        dpTanggal.setValue(LocalDate.now());

        txtCheckIn.clear();

        txtSnackPagi.clear();

        txtMakanSiang.clear();

        txtSnackSore.clear();

        txtMandi.clear();

        txtTidur.clear();

        txtCheckOut.clear();

        txtAktivitasHarian.clear();

        txtCatatan.clear();

        chkCheckIn.setSelected(false);

        chkSnackPagi.setSelected(false);

        chkMakanSiang.setSelected(false);

        chkSnackSore.setSelected(false);

        chkMandi.setSelected(false);

        chkTidur.setSelected(false);

        chkCheckOut.setSelected(false);

        lblIDAnak.setText("-");

        lblNamaAnak.setText("-");

        lblStatusAnak.setText("-");

        System.out.println("🔄 Form cleared");

    }

    //==================== AUTO TIME ====================

    private void autoTime() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        // ✅ PERBAIKAN: Extract setTimeOnCheckbox ke method terpisah untuk DRY principle
        setTimeOnCheckbox(chkCheckIn, txtCheckIn, formatter, "Check In");
        setTimeOnCheckbox(chkSnackPagi, txtSnackPagi, formatter, "Snack Pagi");
        setTimeOnCheckbox(chkMakanSiang, txtMakanSiang, formatter, "Makan Siang");
        setTimeOnCheckbox(chkSnackSore, txtSnackSore, formatter, "Snack Sore");
        setTimeOnCheckbox(chkMandi, txtMandi, formatter, "Mandi");
        setTimeOnCheckbox(chkTidur, txtTidur, formatter, "Tidur");
        setTimeOnCheckbox(chkCheckOut, txtCheckOut, formatter, "Check Out");

    }

    //==================== SET TIME ON CHECKBOX ====================

    private void setTimeOnCheckbox(CheckBox checkbox, TextField textField,
                                   DateTimeFormatter formatter, String activity) {

        checkbox.setOnAction(e -> {

            if (checkbox.isSelected()) {

                String time = LocalTime.now().format(formatter);
                textField.setText(time);

                System.out.println("⏰ " + activity + ": " + time);

            } else {

                textField.clear();

            }

        });

    }

    //==================== SETUP DAYCARE LISTENER ====================

    private void setupDaycareListener() {

        cmbDaycare.setOnAction(e -> cmbDaycareOnAction());

    }

    //==================== VALIDASI INPUT ====================

    private boolean validasiInput() {

        if (cmbDaycare.getSelectionModel().isEmpty()) {

            showAlert(Alert.AlertType.WARNING,
                    "Peringatan",
                    "Silakan pilih transaksi daycare.");

            return false;

        }

        if (cmbActivity.getSelectionModel().isEmpty()) {

            showAlert(Alert.AlertType.WARNING,
                    "Peringatan",
                    "Silakan pilih activity schedule.");

            return false;

        }

        if (txtAktivitasHarian.getText().trim().isEmpty()) {

            showAlert(Alert.AlertType.WARNING,
                    "Peringatan",
                    "Aktivitas harian belum diisi.");

            return false;

        }

        return true;

    }

    //==================== BUTTON SIMPAN ====================

    @FXML
    private void btnSimpanOnAction() {

        System.out.println("💾 Simpan button clicked");

        if (validasiInput()) {

            simpanData();

        }

    }

    //==================== BUTTON RESET ====================

    @FXML
    private void btnResetOnAction() {

        System.out.println("🔄 Reset button clicked");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText(null);
        alert.setContentText("Apakah Anda yakin ingin mereset form?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            clearForm();
        }

    }

    //==================== ALERT ====================

    private void showAlert(Alert.AlertType type,
                           String title,
                           String message) {

        Alert alert = new Alert(type);

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();

    }

    //==================== SIMPAN DATA ====================

    private void simpanData() {

        try {

            System.out.println("💾 Saving Daily Activity data...");

            String idDailyActivity = generateID();

            CallableStatement cs = db.conn.prepareCall(
                    "{call sp_InsertDailyActivity(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

            // ✅ PERBAIKAN: Tambah null checks untuk safety
            cs.setString(1, idDailyActivity);
            cs.setString(2, cmbDaycare.getValue());
            cs.setString(3, cmbActivity.getValue());
            cs.setString(4, txtCaregiver.getText());

            cs.setDate(5, Date.valueOf(dpTanggal.getValue()));

            // Set time parameters dengan null check
            cs.setTime(6, setTimeOrNull(txtCheckIn.getText()));
            cs.setTime(7, setTimeOrNull(txtCheckOut.getText()));
            cs.setTime(8, setTimeOrNull(txtSnackPagi.getText()));
            cs.setTime(9, setTimeOrNull(txtMakanSiang.getText()));
            cs.setTime(10, setTimeOrNull(txtSnackSore.getText()));
            cs.setTime(11, setTimeOrNull(txtMandi.getText()));
            cs.setTime(12, setTimeOrNull(txtTidur.getText()));

            cs.setString(13, txtAktivitasHarian.getText());
            cs.setString(14, txtCatatan.getText().isEmpty() ? "" : txtCatatan.getText());

            cs.executeUpdate();

            System.out.println("✅ Data saved successfully with ID: " + idDailyActivity);

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "✅ Berhasil",
                    "Data Daily Activity berhasil disimpan!\n\n" +
                            "ID: " + idDailyActivity
            );

            clearForm();

            cs.close();

        } catch (SQLException sqlException) {

            System.out.println("❌ SQL Error: " + sqlException.getMessage());

            showAlert(
                    Alert.AlertType.ERROR,
                    "❌ Database Error",
                    "Gagal menyimpan data:\n" + sqlException.getMessage()
            );

            sqlException.printStackTrace();

        } catch (Exception e) {

            System.out.println("❌ Error: " + e.getMessage());

            showAlert(
                    Alert.AlertType.ERROR,
                    "❌ Error",
                    "Terjadi kesalahan:\n" + e.getMessage()
            );

            e.printStackTrace();

        }

    }

    //==================== SET TIME OR NULL ====================

    private Time setTimeOrNull(String timeString) {

        if (timeString == null || timeString.isEmpty()) {
            return null;
        }

        try {
            return Time.valueOf(timeString + ":00");
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ Invalid time format: " + timeString);
            return null;
        }

    }

    //==================== LOAD DATA ANAK BERDASARKAN DAYCARE ====================

    private void loadDataAnak() {

        if (cmbDaycare.getValue() == null) {

            System.out.println("⚠️ No Daycare selected");

            lblIDAnak.setText("-");
            lblNamaAnak.setText("-");
            lblStatusAnak.setText("-");

            return;

        }

        try {

            System.out.println("📂 Loading Anak data for Daycare: " + cmbDaycare.getValue());

            String sql =
                    "SELECT A.ID_Anak, A.Nama_Anak, TD.Status_Daycare " +
                            "FROM Transaksi_Daycare TD " +
                            "JOIN Anak A ON TD.ID_Anak = A.ID_Anak " +
                            "WHERE TD.ID_Daycare = ?";

            PreparedStatement ps = db.conn.prepareStatement(sql);
            ps.setString(1, cmbDaycare.getValue());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                lblIDAnak.setText(rs.getString("ID_Anak"));
                lblNamaAnak.setText(rs.getString("Nama_Anak"));
                lblStatusAnak.setText(rs.getString("Status_Daycare"));

                System.out.println("✅ Anak data loaded: " + rs.getString("Nama_Anak"));

            } else {

                lblIDAnak.setText("-");
                lblNamaAnak.setText("-");
                lblStatusAnak.setText("-");

                System.out.println("⚠️ No Anak found for selected Daycare");

            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            System.out.println("❌ Error loading Anak data: " + e.getMessage());
            e.printStackTrace();

        }

    }

    //==================== EVENT COMBOBOX DAYCARE ====================

    @FXML
    private void cmbDaycareOnAction() {

        System.out.println("🔄 Daycare ComboBox changed");

        loadDataAnak();

    }

    //==================== BUTTON BACK ====================

    @FXML
    private void btnBackOnAction() {

        try {

            System.out.println("🔄 Navigating back to Dashboard Caregiver...");

            String fxmlPath = "/DashboardCaregiver/DashboardCaregiver.fxml";

            var url = getClass().getResource(fxmlPath);

            if (url == null) {
                throw new IOException("File tidak ditemukan: " + fxmlPath);
            }

            System.out.println("✅ File ditemukan: " + url);

            FXMLLoader loader = new FXMLLoader(url);

            Parent root = loader.load();

            Stage stage = (Stage) btnBack.getScene().getWindow();

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Dashboard Caregiver - KiddieLand");
            stage.centerOnScreen();
            stage.show();

            System.out.println("✅ Berhasil navigate ke Dashboard Caregiver");

        } catch (IOException ioException) {

            ioException.printStackTrace();

            System.out.println("❌ IO Error: " + ioException.getMessage());

            showAlert(
                    Alert.AlertType.ERROR,
                    "❌ Error File Not Found",
                    "File DashboardCaregiver.fxml tidak ditemukan!\n\n" +
                            "Error: " + ioException.getMessage()
            );

        } catch (Exception e) {

            e.printStackTrace();

            System.out.println("❌ Error: " + e.getMessage());

            showAlert(
                    Alert.AlertType.ERROR,
                    "❌ Error",
                    "Terjadi kesalahan: " + e.getMessage()
            );

        }

    }

    //==================== BUTTON LOGOUT ====================

    @FXML
    private void btnLogoutOnAction() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Logout");

        alert.setHeaderText(null);

        alert.setContentText("Apakah Anda yakin ingin logout?");

        if (alert.showAndWait().get() == ButtonType.OK) {

            try {

                System.out.println("🚪 Logging out...");

                String fxmlPath = "/Login/Login.fxml";

                var url = getClass().getResource(fxmlPath);

                if (url == null) {
                    throw new IOException("File tidak ditemukan: " + fxmlPath);
                }

                FXMLLoader loader = new FXMLLoader(url);

                Parent root = loader.load();

                Stage stage = (Stage) btnLogout.getScene().getWindow();

                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.setTitle("Login - KiddieLand");
                stage.centerOnScreen();
                stage.show();

                System.out.println("✅ Logout berhasil, navigasi ke Login");

            } catch (IOException ioException) {

                ioException.printStackTrace();

                System.out.println("❌ IO Error: " + ioException.getMessage());

                showAlert(
                        Alert.AlertType.ERROR,
                        "❌ Error",
                        "Gagal logout: " + ioException.getMessage()
                );

            } catch (Exception e) {

                e.printStackTrace();

                System.out.println("❌ Error: " + e.getMessage());

                showAlert(
                        Alert.AlertType.ERROR,
                        "❌ Error",
                        "Terjadi kesalahan: " + e.getMessage()
                );

            }

        }

    }

}
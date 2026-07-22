package com.example.kidsdaycareactivitycenter.Login;

import com.example.kidsdaycareactivitycenter.DashboardAdmin.DashboardAdminController;
import com.example.kidsdaycareactivitycenter.DashboardCaregiver.DashboardCaregiverController;
import com.example.kidsdaycareactivitycenter.DashboardManager.DashboardManagerController;
import com.example.kidsdaycareactivitycenter.Database.DBConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.CallableStatement;
import java.sql.ResultSet;

public class LoginController {

    private DBConnect db = new DBConnect();

    @FXML
    public void initialize() {
        if (db.conn == null) {
            System.out.println("❌ CONNECTION NULL");
        } else {
            System.out.println("✅ CONNECTED");
        }
    }

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogout;

    //==================================================
    // LOGIN
    //==================================================

    @FXML
    private void login(ActionEvent event) {

        if (!validation()) {
            return;
        }

        try {
            CallableStatement cs = db.conn.prepareCall("{CALL sp_Login(?,?)}");

            cs.setString(1, txtUsername.getText().trim());
            cs.setString(2, txtPassword.getText().trim());

            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                String idKaryawan = rs.getString("ID_Karyawan");
                String nama = rs.getString("Nama_Karyawan");
                String role = rs.getString("Role");
                String status = rs.getString("Status");

                // ✅ DEBUG: Tampilkan data yang diterima dari database
                System.out.println("📊 ========== LOGIN INFO ==========");
                System.out.println("👤 ID: " + idKaryawan);
                System.out.println("👤 Nama: " + nama);
                System.out.println("🔐 Role (Raw): '" + role + "'");
                System.out.println("🔐 Role (Length): " + (role != null ? role.length() : "NULL"));
                System.out.println("📊 Status: " + status);
                System.out.println("================================");

                // ✅ TRIM dan UPPERCASE untuk keamanan
                if (role == null || role.trim().isEmpty()) {
                    alertError("Role tidak ditemukan di database!");
                    rs.close();
                    cs.close();
                    return;
                }

                role = role.trim().toUpperCase();
                System.out.println("🔐 Role (Processed): '" + role + "'");

                // ✅ ROUTING BERDASARKAN ROLE
                if (role.equals("ADMIN")) {
                    System.out.println("✅ Login sebagai ADMIN");
                    alertInformation("Selamat Datang, Admin: " + nama);
                    openDashboardAdmin(idKaryawan, nama, "Admin");

                } else if (role.equals("CAREGIVER")) {
                    System.out.println("✅ Login sebagai CAREGIVER");
                    alertInformation("Selamat Datang, Caregiver: " + nama);
                    openDashboardCaregiver(idKaryawan, nama, "Caregiver");

                } else if (role.equals("MANAGER")) {
                    System.out.println("✅ Login sebagai MANAGER");
                    alertInformation("Selamat Datang, Manager: " + nama);
                    openDashboardManager(idKaryawan, nama, "Manager");

                } else {
                    // ❌ ROLE TIDAK DIKENALI
                    System.out.println("❌ Role tidak dikenali: '" + role + "'");
                    alertError("Role tidak dikenali: " + role + "\n\nRole yang valid adalah: ADMIN, MANAGER, atau CAREGIVER");
                }

            } else {
                System.out.println("❌ Username atau Password salah!");
                alertError("Username atau Password salah!");
            }

            rs.close();
            cs.close();

        } catch (Exception e) {
            System.out.println("❌ Login Error: " + e.getMessage());
            e.printStackTrace();
            alertError("Terjadi kesalahan: " + e.getMessage());
        }
    }

    //==================================================
    // LOGOUT
    //==================================================

    @FXML
    private void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Login/Login.fxml"));

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) btnLogout.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Login");
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            System.out.println("❌ Logout Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //==================================================
    // VALIDATION
    //==================================================

    private boolean validation() {

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

        return true;
    }

    //==================================================
    // DASHBOARD ADMIN
    //==================================================

    private void openDashboardAdmin(String idKaryawan, String nama, String role) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/DashboardAdmin/DashboardAdmin.fxml"));

            Scene scene = new Scene(loader.load());

            DashboardAdminController controller = loader.getController();

            controller.setUserLogin(nama, role);

            Stage stage = (Stage) txtUsername.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Dashboard Admin - " + nama);
            stage.centerOnScreen();
            stage.show();

            System.out.println("✅ Dashboard Admin berhasil dibuka");

        } catch (Exception e) {
            System.out.println("❌ Dashboard Admin Error: " + e.getMessage());
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Dashboard Admin Error");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    //==================================================
    // DASHBOARD CAREGIVER
    //==================================================

    private void openDashboardCaregiver(String idKaryawan, String nama, String role) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/DashboardCaregiver/DashboardCaregiver.fxml"));

            Scene scene = new Scene(loader.load());

            DashboardCaregiverController controller = loader.getController();

            controller.setUserLogin(nama, role);

            Stage stage = (Stage) txtUsername.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Dashboard Caregiver - " + nama);
            stage.centerOnScreen();
            stage.show();

            System.out.println("✅ Dashboard Caregiver berhasil dibuka");

        } catch (Exception e) {
            System.out.println("❌ Dashboard Caregiver Error: " + e.getMessage());
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Dashboard Caregiver Error");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    //==================================================
    // DASHBOARD MANAGER
    //==================================================

    private void openDashboardManager(String idKaryawan, String nama, String role) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/DashboardManager/DashboardManager.fxml"));

            Scene scene = new Scene(loader.load());

            DashboardManagerController controller = loader.getController();

            controller.setUserLogin(nama, role);

            Stage stage = (Stage) txtUsername.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Dashboard Manager - " + nama);
            stage.centerOnScreen();
            stage.show();

            System.out.println("✅ Dashboard Manager berhasil dibuka");

        } catch (Exception e) {
            System.out.println("❌ Dashboard Manager Error: " + e.getMessage());
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Dashboard Manager Error");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    //==================================================
    // EXIT
    //==================================================

    @FXML
    private void exit(ActionEvent event) {
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }

    //==================================================
    // ALERT INFORMATION
    //==================================================

    private void alertInformation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //==================================================
    // ALERT WARNING
    //==================================================

    private void alertWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Peringatan");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //==================================================
    // ALERT ERROR
    //==================================================

    private void alertError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
package com.example.kidsdaycareactivitycenter.Login;

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

                String nama = rs.getString("Nama_Karyawan");
                String role = rs.getString("Role");

                alertInformation("Selamat Datang, " + nama);

                if (role.equalsIgnoreCase("Admin")) {

                    openDashboardAdmin();

                } else if (role.equalsIgnoreCase("Caregiver")) {

                    openDashboardCaregiver();

                } else {

                    alertError("Role tidak dikenali.");

                }

            } else {

                alertError("Username atau Password salah.");

            }

            rs.close();
            cs.close();

        } catch (Exception e) {

            e.printStackTrace();
            alertError("Terjadi kesalahan : " + e.getMessage());

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

    private void openDashboardAdmin() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/DashboardAdmin/DashboardAdmin.fxml"));

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) txtUsername.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Dashboard Admin");
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Dashboard Admin Error");
            alert.setContentText(e.toString());
            alert.showAndWait();

        }

    }

    //==================================================
    // DASHBOARD CAREGIVER
    //==================================================

    private void openDashboardCaregiver() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/DashboardCaregiver/DashboardCaregiver.fxml"));

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) txtUsername.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Dashboard Caregiver");
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Dashboard Caregiver Error");
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

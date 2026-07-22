package com.example.kidsdaycareactivitycenter.DashboardCaregiver;

import com.example.kidsdaycareactivitycenter.Database.DBConnect;
import com.example.kidsdaycareactivitycenter.Model.MasterAnak;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class DashboardCaregiverController {

    private final DBConnect db = new DBConnect();


    @FXML
    private Button btnCheckIn;

    @FXML
    private Button btnDailyActivity;

    @FXML
    private Button btnCheckOut;

    @FXML
    private Button btnCari;

    @FXML
    private Button btnLogout;


    @FXML
    private Label lblTotalAnak;

    @FXML
    private Label lblCheckIn;

    @FXML
    private Label lblBelumCheckIn;

    @FXML
    private Label lblCheckOut;

    @FXML
    private Label lblCaregiver;

    @FXML
    private Label lblStatus;


    @FXML
    private TextField txtCari;


    @FXML
    private TableView<MasterAnak> tblAnak;

    @FXML
    private TableColumn<MasterAnak,String> colID;

    @FXML
    private TableColumn<MasterAnak,String> colNama;

    @FXML
    private TableColumn<MasterAnak,String> colCheckIn;

    @FXML
    private TableColumn<MasterAnak,String> colStatus;

    @FXML
    private TableColumn<MasterAnak,Void> colAksi;


    private final ObservableList<MasterAnak> daftarAnak =
            FXCollections.observableArrayList();


    @FXML
    public void initialize(){

        setupTable();

        loadDataAnak();

        updateStatistik();

        lblCaregiver.setText("Caregiver");

        lblStatus.setText("Status : Online");

    }


    private void setupTable(){

        colID.setCellValueFactory(
                new PropertyValueFactory<>("idAnak"));

        colNama.setCellValueFactory(
                new PropertyValueFactory<>("namaAnak"));


        colCheckIn.setVisible(false);

        colStatus.setVisible(false);

        tambahKolomAksi();

        tblAnak.setItems(daftarAnak);

    }


    private void tambahKolomAksi() {

        colAksi.setCellFactory(param -> new TableCell<>() {

            private final Button btnDetail = new Button("Detail");

            {

                btnDetail.setStyle(
                        "-fx-background-color:#2563EB;" +
                                "-fx-text-fill:white;" +
                                "-fx-background-radius:6;");

                btnDetail.setOnAction(event -> {

                    MasterAnak anak =
                            getTableView().getItems().get(getIndex());

                    tampilkanDetail(anak);

                });

            }

            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);

                if (empty) {

                    setGraphic(null);

                } else {

                    setGraphic(btnDetail);

                }

            }

        });

    }


    private void tampilkanDetail(MasterAnak anak){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Detail Anak");

        alert.setHeaderText(anak.getNamaAnak());

        alert.setContentText(

                "ID Anak        : " + anak.getIdAnak() + "\n" +
                        "Nama Anak      : " + anak.getNamaAnak() + "\n" +
                        "ID Orang Tua   : " + anak.getIdOrangTua() + "\n" +
                        "Tanggal Lahir  : " + anak.getTanggalLahir() + "\n" +
                        "Jenis Kelamin  : " + anak.getJenisKelamin() + "\n" +
                        "Catatan Khusus : " + anak.getCatatanKhusus()

        );

        alert.showAndWait();

    }


    private void loadDataAnak(){

        daftarAnak.clear();

        String sql =
                "SELECT ID_Anak, " +
                        "ID_OrangTua, " +
                        "Nama_Anak, " +
                        "Tanggal_Lahir, " +
                        "Jenis_Kelamin, " +
                        "Catatan_Khusus " +
                        "FROM Anak ";

        try{

            PreparedStatement ps =
                    db.conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()){

                MasterAnak anak =
                        new MasterAnak();

                anak.setIdAnak(
                        rs.getString("ID_Anak"));

                anak.setIdOrangTua(
                        rs.getString("ID_OrangTua"));

                anak.setNamaAnak(
                        rs.getString("Nama_Anak"));

                anak.setTanggalLahir(
                        rs.getDate("Tanggal_Lahir"));

                anak.setJenisKelamin(
                        rs.getString("Jenis_Kelamin").charAt(0));

                anak.setCatatanKhusus(
                        rs.getString("Catatan_Khusus"));

                daftarAnak.add(anak);

            }

            rs.close();
            ps.close();

        }
        catch(SQLException e){

            tampilkanError(e.getMessage());

        }

    }


    private void updateStatistik(){

        int total = daftarAnak.size();

        lblTotalAnak.setText(
                String.valueOf(total));

        // sementara masih dummy

        lblCheckIn.setText("0");

        lblBelumCheckIn.setText(
                String.valueOf(total));

        lblCheckOut.setText("0");

    }


    @FXML
    private void checkIn() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Check In");
        alert.setHeaderText(null);
        alert.setContentText("Menu Check In belum dibuat.");
        alert.showAndWait();

    }


    @FXML
    private void dailyActivity() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Daily Activity");
        alert.setHeaderText(null);
        alert.setContentText("Menu Daily Activity belum dibuat.");
        alert.showAndWait();

    }


    @FXML
    private void checkOut() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Check Out");
        alert.setHeaderText(null);
        alert.setContentText("Menu Check Out belum dibuat.");
        alert.showAndWait();

    }


    @FXML
    private void cari() {

        String keyword = txtCari.getText().trim().toLowerCase();

        if (keyword.isEmpty()) {

            tblAnak.setItems(daftarAnak);
            return;

        }

        ObservableList<MasterAnak> hasil =
                FXCollections.observableArrayList();

        for (MasterAnak anak : daftarAnak) {

            if (anak.getIdAnak().toLowerCase().contains(keyword)
                    || anak.getNamaAnak().toLowerCase().contains(keyword)) {

                hasil.add(anak);

            }

        }

        tblAnak.setItems(hasil);

    }

    @FXML
    private void logout(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/kidsdaycareactivitycenter/Login/Login.fxml"));

            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) btnLogout.getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Login");
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {

            e.printStackTrace();

            tampilkanError(e.getMessage());

        }

    }

    private void tampilkanError(String pesan){

        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");

        alert.setHeaderText(null);

        alert.setContentText(pesan);

        alert.showAndWait();

    }

    public void setUserLogin(String nama, String role) {
    }
}
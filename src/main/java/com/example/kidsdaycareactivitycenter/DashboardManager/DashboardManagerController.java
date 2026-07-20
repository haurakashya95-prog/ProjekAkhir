
package com.example.kidsdaycareactivitycenter.DashboardManager;

import com.example.kidsdaycareactivitycenter.Database.DBConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class DashboardManagerController {

    private final DBConnect db = new DBConnect();
    private PreparedStatement ps;
    private ResultSet rs;

    @FXML private Label lblManager;
    @FXML private Label lblTanggal;

    @FXML private Button cardHarian;
    @FXML private Button cardBulanan;
    @FXML private Button cardTahunan;
    @FXML private Button btnLogout;

    @FXML private VBox paneHarian;
    @FXML private VBox paneBulanan;
    @FXML private VBox paneTahunan;

    @FXML private DatePicker dpTanggal;
    @FXML private ComboBox<String> cmbBulan;
    @FXML private TextField txtTahunBulanan;
    @FXML private TextField txtTahunTahunan;

    @FXML private Label lblPendapatanHarian;
    @FXML private Label lblPendapatanBulanan;
    @FXML private Label lblPendapatanTahunan;

    @FXML private Label lblAnakHarian;
    @FXML private Label lblAnakBulanan;
    @FXML private Label lblAnakTahunan;

    @FXML private TableView<?> tblKategoriHarian;
    @FXML private TableView<?> tblKategoriBulanan;
    @FXML private TableView<?> tblKategoriTahunan;

    @FXML
    public void initialize() {
        lblTanggal.setText(LocalDate.now().toString());
        dpTanggal.setValue(LocalDate.now());

        cmbBulan.getItems().addAll(
                "Januari","Februari","Maret","April","Mei","Juni",
                "Juli","Agustus","September","Oktober","November","Desember");

        txtTahunBulanan.setText(String.valueOf(LocalDate.now().getYear()));
        txtTahunTahunan.setText(String.valueOf(LocalDate.now().getYear()));

        showHarian(null);
    }

    @FXML
    private void showHarian(ActionEvent e){
        paneHarian.setVisible(true);
        paneHarian.setManaged(true);

        paneBulanan.setVisible(false);
        paneBulanan.setManaged(false);

        paneTahunan.setVisible(false);
        paneTahunan.setManaged(false);
    }

    @FXML
    private void showBulanan(ActionEvent e){
        paneBulanan.setVisible(true);
        paneBulanan.setManaged(true);

        paneHarian.setVisible(false);
        paneHarian.setManaged(false);

        paneTahunan.setVisible(false);
        paneTahunan.setManaged(false);
    }

    @FXML
    private void showTahunan(ActionEvent e){
        paneTahunan.setVisible(true);
        paneTahunan.setManaged(true);

        paneHarian.setVisible(false);
        paneHarian.setManaged(false);

        paneBulanan.setVisible(false);
        paneBulanan.setManaged(false);
    }

    @FXML
    private void loadHarian(ActionEvent e){
        try{
            ps=db.conn.prepareStatement("EXEC sp_DashboardPendapatanHarian ?");
            ps.setDate(1, java.sql.Date.valueOf(dpTanggal.getValue()));
            rs=ps.executeQuery();
            if(rs.next()){
                lblPendapatanHarian.setText(rupiah(rs.getDouble(1)));
            }
            rs.close();
            ps.close();

            ps=db.conn.prepareStatement("EXEC sp_DashboardJumlahAnakHarian ?");
            ps.setDate(1, java.sql.Date.valueOf(dpTanggal.getValue()));
            rs=ps.executeQuery();
            if(rs.next()){
                lblAnakHarian.setText(rs.getString(1)+" Anak");
            }
            rs.close();
            ps.close();

            // TODO: isi tblKategoriHarian dari sp_DashboardKategoriHarian

        }catch(Exception ex){
            error(ex.getMessage());
        }
    }

    @FXML
    private void loadBulanan(ActionEvent e){
        try{
            int bulan=cmbBulan.getSelectionModel().getSelectedIndex()+1;
            int tahun=Integer.parseInt(txtTahunBulanan.getText());

            ps=db.conn.prepareStatement("EXEC sp_DashboardPendapatanBulanan ?,?");
            ps.setInt(1,bulan);
            ps.setInt(2,tahun);
            rs=ps.executeQuery();
            if(rs.next()){
                lblPendapatanBulanan.setText(rupiah(rs.getDouble(1)));
            }
            rs.close();
            ps.close();

            ps=db.conn.prepareStatement("EXEC sp_DashboardJumlahAnakBulanan ?,?");
            ps.setInt(1,bulan);
            ps.setInt(2,tahun);
            rs=ps.executeQuery();
            if(rs.next()){
                lblAnakBulanan.setText(rs.getString(1)+" Anak");
            }
            rs.close();
            ps.close();

            // TODO: isi tblKategoriBulanan

        }catch(Exception ex){
            error(ex.getMessage());
        }
    }

    @FXML
    private void loadTahunan(ActionEvent e){
        try{
            int tahun=Integer.parseInt(txtTahunTahunan.getText());

            ps=db.conn.prepareStatement("EXEC sp_DashboardPendapatanTahunan ?");
            ps.setInt(1,tahun);
            rs=ps.executeQuery();
            if(rs.next()){
                lblPendapatanTahunan.setText(rupiah(rs.getDouble(1)));
            }
            rs.close();
            ps.close();

            ps=db.conn.prepareStatement("EXEC sp_DashboardJumlahAnakTahunan ?");
            ps.setInt(1,tahun);
            rs=ps.executeQuery();
            if(rs.next()){
                lblAnakTahunan.setText(rs.getString(1)+" Anak");
            }
            rs.close();
            ps.close();

            // TODO: isi tblKategoriTahunan

        }catch(Exception ex){
            error(ex.getMessage());
        }
    }

    @FXML
    private void logout(ActionEvent e){
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/Login/Login.fxml"));
            Parent root=loader.load();
            Stage stage=(Stage)btnLogout.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        }catch(Exception ex){
            error(ex.getMessage());
        }
    }

    private String rupiah(double nilai){
        NumberFormat nf=NumberFormat.getCurrencyInstance(new Locale("id","ID"));
        return nf.format(nilai);
    }

    private void error(String msg){
        Alert a=new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("Error");
        a.setContentText(msg);
        a.showAndWait();
    }
}

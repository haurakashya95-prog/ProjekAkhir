package com.example.kidsdaycareactivitycenter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

//    @Override
//    public void start(Stage stage) throws IOException {
//
//        FXMLLoader fxmlLoader = new FXMLLoader(
//                Main.class.getResource("/MasterPaketDaycare/MasterPaketDaycare.fxml"));
//
//        Scene scene = new Scene(fxmlLoader.load());
//
//        stage.setTitle("Kids Daycare Activity Center - Paket Daycare");
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();
//    }

//    @Override
//    public void start(Stage stage) throws IOException {
//
//        FXMLLoader fxmlLoader = new FXMLLoader(
//                Main.class.getResource("/MasterActivitySchedule/MasterActivitySchedule.fxml"));
//
//        Scene scene = new Scene(fxmlLoader.load());
//
//        stage.setTitle("Kids Daycare Activity Center - Master Activity Schedule");
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();
//    }

//    @Override
//    public void start(Stage stage) throws IOException {
//
//        FXMLLoader fxmlLoader = new FXMLLoader(
//                Main.class.getResource("/TransaksiDaycare/TransaksiDaycare.fxml"));
//
//        Scene scene = new Scene(fxmlLoader.load());
//
//        stage.setTitle("Kids Daycare Activity Center - Master Activity Schedule");
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();
//    }

//    @Override
//    public void start(Stage stage) throws IOException {
//
//        FXMLLoader fxmlLoader = new FXMLLoader(
//                Main.class.getResource("/MasterAnak/MasterAnak.fxml"));
//
//        Scene scene = new Scene(fxmlLoader.load());
//
//        stage.setTitle("Kids Daycare Activity Center - Master Anak");
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();
//    }

//    @Override
//    public void start(Stage stage) throws IOException {
//
//        FXMLLoader fxmlLoader = new FXMLLoader(
//                Main.class.getResource("/MasterOrangTua/MasterOrangTua.fxml"));
//
//        Scene scene = new Scene(fxmlLoader.load());
//
//        stage.setTitle("Kids Daycare Activity Center - Master Orang Tua");
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();
//    }

//    @Override
//    public void start(Stage stage) throws IOException {
//
//        FXMLLoader fxmlLoader = new FXMLLoader(
//                Main.class.getResource("/Login/Login.fxml"));
//
//        Scene scene = new Scene(fxmlLoader.load());
//
//        stage.setTitle("Kids Daycare Activity Center - Login");
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();
//    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(
                Main.class.getResource("/MasterKaryawan/MasterKaryawan.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Kids Daycare Activity Center - Master Karyawan");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

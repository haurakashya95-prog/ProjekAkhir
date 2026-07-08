package com.example.kidsdaycareactivitycenter.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnect {


    public Connection conn;
    public Statement stat;
    public PreparedStatement pstat;
    public ResultSet result;

    public DBConnect() {

        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url =
                    "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;" +
                            "databaseName=KiddieLandDaycare02;" +
                            "user=sa;" +
                            "password=211206;" +
                            "encrypt=true;" +
                            "trustServerCertificate=true;";

            conn = DriverManager.getConnection(url);

            stat = conn.createStatement();

            System.out.println("Database Connected.");

        }

        catch (Exception e) {

            System.out.println("Database Failed : " + e.getMessage());
            e.printStackTrace();

        }

    }


    public static void main(String[] args) {

        new DBConnect();

    }

}
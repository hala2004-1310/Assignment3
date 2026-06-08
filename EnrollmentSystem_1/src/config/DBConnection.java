package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    static String url      = "jdbc:mysql://localhost:3306/enrollment_db";
    static String user     = "root";
    static String password = "";   

    public static Connection getConnection() {

        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            System.out.println("Connection Error: " + e.getMessage());
        }

        return con;
    }
}
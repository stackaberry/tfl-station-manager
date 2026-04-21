package utils;

import db.DatabaseSetup;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    public static Connection getConnection() throws SQLException {
        // Absolute path to your specific DB folder
        //String url = "jdbc:derby:C:/Users/Matt/Desktop/New folder/tube_db";
        String dbPath = System.getProperty("user.home") + "/tube_db";
        String url = "jdbc:derby:" + dbPath + ";create=true";
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = DriverManager.getConnection(url);
        DatabaseSetup.initialiseIfNeeded(connection);
        return connection;
        //return DriverManager.getConnection(url);
    }
}

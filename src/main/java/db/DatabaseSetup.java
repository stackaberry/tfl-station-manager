package db;

import java.sql.*;

public class DatabaseSetup {

    public static void initialiseIfNeeded(Connection conn) throws SQLException {
        if (tableExists(conn, "STATIONS")) {
            return; // already set up, nothing to do
        }

    //public static void main(String[] args) {
        // "create=true" tells Derby to build a new folder if it's missing
        //String url = "jdbc:derby:C:/Users/Matt/Desktop/New folder/tube_db;create=true";

        try (Statement stmt = conn.createStatement()) {
        //try (Connection conn = DriverManager.getConnection(url);
             //Statement stmt = conn.createStatement()) {

            System.out.println("Building new database structure...");

            // 1. Create Tables (Order matters because of Foreign Keys!)
            stmt.executeUpdate("CREATE TABLE lines (" +
                    "id INT PRIMARY KEY, " +
                    "name VARCHAR(50))");

            stmt.executeUpdate("CREATE TABLE stations (" +
                    "id INT PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "zone INT, " +
                    "line_id INT, " +
                    "FOREIGN KEY (line_id) REFERENCES lines(id))");

            stmt.executeUpdate("CREATE TABLE users (" +
                    "username VARCHAR(50) PRIMARY KEY, " +
                    "password VARCHAR(50))");

            System.out.println("Tables created successfully.");

            // 2. Insert Lines
            String[] lines = {
                    "INSERT INTO lines VALUES (1, 'Bakerloo')",
                    "INSERT INTO lines VALUES (2, 'Central')",
                    "INSERT INTO lines VALUES (3, 'Jubilee')",
                    "INSERT INTO lines VALUES (4, 'Northern')",
                    "INSERT INTO lines VALUES (5, 'Piccadilly')",
                    "INSERT INTO lines VALUES (6, 'Victoria')",
                    "INSERT INTO lines VALUES (7, 'Elizabeth Line')"
            };
            for (String sql : lines) stmt.executeUpdate(sql);

            // 3. Insert Stations
            String[] stations = {
                    "INSERT INTO stations VALUES (101, 'Oxford Circus', 1, 2)",
                    "INSERT INTO stations VALUES (102, 'Waterloo', 1, 4)",
                    "INSERT INTO stations VALUES (103, 'Stratford', 2, 3)",
                    "INSERT INTO stations VALUES (104, 'Brixton', 2, 6)",
                    "INSERT INTO stations VALUES (105, 'Heathrow T5', 6, 5)",
                    "INSERT INTO stations VALUES (106, 'Ealing Broadway', 3, 2)",
                    "INSERT INTO stations VALUES (107, 'Canary Wharf', 2, 3)",
                    "INSERT INTO stations VALUES (108, 'Angel', 1, 4)",
                    "INSERT INTO stations VALUES (109, 'Wembley Park', 4, 3)",
                    "INSERT INTO stations VALUES (110, 'Paddington', 1, 7)"
            };
            for (String sql : stations) stmt.executeUpdate(sql);

            // 4. Create Admin User
            stmt.executeUpdate("INSERT INTO users VALUES ('admin', 'password123')");

            System.out.println("DONE: Fresh database created and populated!");

        //} catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    private static boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        try (ResultSet rs = meta.getTables(null, null, tableName, null)) {
            return rs.next();
        }
    }
}

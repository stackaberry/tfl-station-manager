package dao;
import models.User;
import utils.DatabaseConfig;
import java.sql.*;

public class UserDAO {
    public User validate(String user, String password) {
        // Adding TRIM() helps if there are hidden spaces in the DB columns
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("username"), rs.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

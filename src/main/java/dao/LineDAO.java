package dao;

import models.Line;
import utils.DatabaseConfig;

import java.sql.*;
import java.util.*;

public class LineDAO {

    public List<Line> getAllLines() {
        List<Line> list = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM lines")) {

            while (rs.next()) {
                list.add(new Line(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Line getLineById(int id) {
        Line line = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM lines WHERE id = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                line = new Line(
                        rs.getInt("id"),
                        rs.getString("name")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return line;
    }

    public void addLine(Line l) {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO lines (id, name) VALUES (?, ?)")) {

            ps.setInt(1, l.getId());
            ps.setString(2, l.getName());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateLine(Line l) {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE lines SET name=? WHERE id=?")) {

            ps.setString(1, l.getName());
            ps.setInt(2, l.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteLine(int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            // First, set the line_id to NULL or delete stations belonging to this line
            try (PreparedStatement ps1 = conn.prepareStatement("DELETE FROM stations WHERE line_id = ?")) {
                ps1.setInt(1, id);
                ps1.executeUpdate();
            }
            // Then, delete the line itself
            try (PreparedStatement ps2 = conn.prepareStatement("DELETE FROM lines WHERE id = ?")) {
                ps2.setInt(1, id);
                ps2.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

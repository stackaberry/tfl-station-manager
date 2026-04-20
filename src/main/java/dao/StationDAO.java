package dao;

import models.Station;
import utils.DatabaseConfig;
import java.sql.*;
import java.util.*;

public class StationDAO {

    public List<Station> getAllStations() {
        List<Station> list = new ArrayList<>();

        String sql = "SELECT s.*, l.name as lineName FROM stations s JOIN lines l ON s.line_id = l.id";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Station s = new Station(rs.getInt("id"), rs.getString("name"),
                        rs.getInt("zone"), rs.getInt("line_id"));
                s.setLineName(rs.getString("lineName"));
                list.add(s);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<Station> searchStations(String query) {
        List<Station> list = new ArrayList<>();
        String sql = "SELECT s.*, l.name as lineName FROM stations s JOIN lines l ON s.line_id = l.id " +
                "WHERE LOWER(s.name) LIKE ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + query.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Station s = new Station(rs.getInt("id"), rs.getString("name"),
                        rs.getInt("zone"), rs.getInt("line_id"));
                s.setLineName(rs.getString("lineName"));
                list.add(s);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public void addStation(Station s) {
        String sql = "INSERT INTO stations (id, name, zone, line_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, s.getId());
            ps.setString(2, s.getName());
            ps.setInt(3, s.getZone());
            ps.setInt(4, s.getLineId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void updateStation(Station s) {
        String sql = "UPDATE stations SET name=?, zone=?, line_id=? WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setInt(2, s.getZone());
            ps.setInt(3, s.getLineId());
            ps.setInt(4, s.getId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void deleteStation(int id) {
        String sql = "DELETE FROM stations WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
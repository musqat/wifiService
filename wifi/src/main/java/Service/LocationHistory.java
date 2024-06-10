package Service;

import dao.LocationDao;
import Connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationHistory {

    public void saveLocationHistory(double lat, double lng, String searchedAt) {
        String insertSQL = "INSERT INTO LocationHistory (latitude, longitude, searched_at) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setDouble(1, lat);
            preparedStatement.setDouble(2, lng);
            preparedStatement.setString(3, searchedAt);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<LocationDao> getLocationHistory() {
        List<LocationDao> locationHistories = new ArrayList<>();
        String sql = "SELECT * FROM LocationHistory ORDER BY searched_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                LocationDao history = new LocationDao(
                    rs.getInt("id"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude"),
                    rs.getString("searched_at")
                );
                locationHistories.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return locationHistories;
    }
}

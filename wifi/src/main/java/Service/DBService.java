package Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.WifiDao;
import Connection.DBConnection;

public class DBService {

    public List<WifiDao> getNearbyWifi(double lat, double lng, int limit) {
        List<WifiDao> wifiInfos = new ArrayList<>();
        String sql = "SELECT *, "
                + "(6371 * acos(cos(radians(?)) * cos(radians(LAT)) * cos(radians(LNT) - radians(?)) + sin(radians(?)) * sin(radians(LAT)))) AS distance "
                + "FROM WifiInfo " + "ORDER BY distance " + "LIMIT ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, lat);
            pstmt.setDouble(2, lng);
            pstmt.setDouble(3, lat);
            pstmt.setInt(4, limit);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                WifiDao info = new WifiDao(rs.getString("X_SWIFI_MGR_NO"), rs.getString("X_SWIFI_WRDOFC"),
                        rs.getString("X_SWIFI_MAIN_NM"), rs.getString("X_SWIFI_ADRES1"), rs.getString("X_SWIFI_ADRES2"),
                        rs.getString("X_SWIFI_INSTL_FLOOR"), rs.getString("X_SWIFI_INSTL_TY"),
                        rs.getString("X_SWIFI_INSTL_MBY"), rs.getString("X_SWIFI_SVC_SE"),
                        rs.getString("X_SWIFI_CMCWR"), rs.getString("X_SWIFI_CNSTC_YEAR"),
                        rs.getString("X_SWIFI_INOUT_DOOR"), rs.getString("X_SWIFI_REMARS3"), rs.getString("LAT"),
                        rs.getString("LNT"), rs.getString("WORK_DTTM"));
                info.setDistance(rs.getDouble("distance"));
                wifiInfos.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wifiInfos;
    }

    public WifiDao getWifiInfoByManagerNo(String managerNo) {
        String sql = "SELECT * FROM WifiInfo WHERE X_SWIFI_MGR_NO = ?";
        WifiDao wifiInfo = null;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, managerNo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                wifiInfo = new WifiDao(rs.getString("X_SWIFI_MGR_NO"), rs.getString("X_SWIFI_WRDOFC"),
                        rs.getString("X_SWIFI_MAIN_NM"), rs.getString("X_SWIFI_ADRES1"), rs.getString("X_SWIFI_ADRES2"),
                        rs.getString("X_SWIFI_INSTL_FLOOR"), rs.getString("X_SWIFI_INSTL_TY"),
                        rs.getString("X_SWIFI_INSTL_MBY"), rs.getString("X_SWIFI_SVC_SE"),
                        rs.getString("X_SWIFI_CMCWR"), rs.getString("X_SWIFI_CNSTC_YEAR"),
                        rs.getString("X_SWIFI_INOUT_DOOR"), rs.getString("X_SWIFI_REMARS3"), rs.getString("LAT"),
                        rs.getString("LNT"), rs.getString("WORK_DTTM"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wifiInfo;
    }
}

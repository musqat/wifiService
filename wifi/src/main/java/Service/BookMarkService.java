package Service;

import dao.BookMarkDao;
import Connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookMarkService {

    public void addBookmark(String wifiId, int groupId) {
        String sql = "INSERT INTO Bookmark (wifi_id, group_id, reg_date) VALUES (?, ?, NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, wifiId);
            pstmt.setInt(2, groupId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBookmark(int id) {
        String sql = "DELETE FROM Bookmark WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<BookMarkDao> getBookmarksByWifiMainNm(String wifiMainNm) {
        List<BookMarkDao> bookmarks = new ArrayList<>();
        String sql = "SELECT * FROM Bookmark WHERE wifi_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, wifiMainNm);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                BookMarkDao bookmark = new BookMarkDao();
                bookmark.setId(rs.getInt("id"));
                bookmark.setWifiId(rs.getString("wifi_id"));
                bookmark.setGroupId(rs.getString("group_id"));
                bookmark.setRegDate(rs.getTimestamp("reg_date"));
                bookmarks.add(bookmark);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookmarks;
    }

    public List<BookMarkDao> getAllBookmarks() {
        List<BookMarkDao> bookmarks = new ArrayList<>();
        String sql = "SELECT b.id, b.wifi_id, w.X_SWIFI_MAIN_NM, bg.group_name, b.reg_date "
                + "FROM Bookmark b "
                + "JOIN WifiInfo w ON b.wifi_id = w.X_SWIFI_MGR_NO "
                + "JOIN BookmarkGroup bg ON b.group_id = bg.id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                BookMarkDao bookmark = new BookMarkDao();
                bookmark.setId(rs.getInt("id"));
                bookmark.setWifiId(rs.getString("X_SWIFI_MAIN_NM")); 
                bookmark.setGroupName(rs.getString("group_name"));
                bookmark.setRegDate(rs.getTimestamp("reg_date"));
                bookmarks.add(bookmark);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookmarks;
    }

    public BookMarkDao getBookmarkById(int id) {
        BookMarkDao bookmark = null;
        String sql = "SELECT b.id, b.wifi_id, w.X_SWIFI_MAIN_NM, bg.group_name, b.reg_date "
                + "FROM Bookmark b "
                + "JOIN WifiInfo w ON b.wifi_id = w.X_SWIFI_MGR_NO "
                + "JOIN BookmarkGroup bg ON b.group_id = bg.id "
                + "WHERE b.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                bookmark = new BookMarkDao();
                bookmark.setId(rs.getInt("id"));
                bookmark.setWifiId(rs.getString("X_SWIFI_MAIN_NM")); // Displaying the Wi-Fi name
                bookmark.setGroupName(rs.getString("group_name"));
                bookmark.setRegDate(rs.getTimestamp("reg_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookmark;
    }
}

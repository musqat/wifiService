package Service;

import dao.BookMarkGroupDao;
import Connection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookMarkGroupService {

    public void addGroup(String groupName, int displayOrder) {
        String sql = "INSERT INTO BookmarkGroup (group_name, display_order) VALUES (?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            System.out.println("Adding group with name: " + groupName + ", display order: " + displayOrder);

            pstmt.setString(1, groupName);
            pstmt.setInt(2, displayOrder);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGroup(int id, String groupName, int displayOrder) {
        String sql = "UPDATE BookmarkGroup SET group_name = ?, display_order = ?, mod_date = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, groupName);
            pstmt.setInt(2, displayOrder);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGroup(int id) {
        String deleteBookmarksSql = "DELETE FROM Bookmark WHERE group_id = ?";
        String deleteGroupSql = "DELETE FROM BookmarkGroup WHERE id = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement deleteBookmarksStmt = connection.prepareStatement(deleteBookmarksSql);
             PreparedStatement deleteGroupStmt = connection.prepareStatement(deleteGroupSql)) {
            
            connection.setAutoCommit(false);
            
            try {
                deleteBookmarksStmt.setInt(1, id);
                deleteBookmarksStmt.executeUpdate();
                
                deleteGroupStmt.setInt(1, id);
                deleteGroupStmt.executeUpdate();
                
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<BookMarkGroupDao> getAllGroups() {
        String sql = "SELECT * FROM BookmarkGroup";
        List<BookMarkGroupDao> groups = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                BookMarkGroupDao group = new BookMarkGroupDao(
                    rs.getInt("id"),
                    rs.getString("group_name"),
                    rs.getInt("display_order"),
                    rs.getTimestamp("reg_date"),
                    rs.getTimestamp("mod_date")
                );
                groups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    public BookMarkGroupDao getGroupById(int id) {
        String sql = "SELECT * FROM BookmarkGroup WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new BookMarkGroupDao(
                        rs.getInt("id"),
                        rs.getString("group_name"),
                        rs.getInt("display_order"),
                        rs.getTimestamp("reg_date"),
                        rs.getTimestamp("mod_date")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class FriendMeetingCRUD {
    private Connection connection;
    private static final String URL = "jdbc:postgresql://localhost:5432/baza";

    public FriendMeetingCRUD(Connection connection) {
        this.connection = connection;
    }
    public List<FriendMeetingPOJO> findAllFromDate(long fromDateTime) throws SQLException {
        String sql = "SELECT id_slot, date_time, is_confirmed, comment, name FROM meeting_slot, friends WHERE date_time >= ? AND meeting_slot.id_friend = friends.id_friend";
        List<FriendMeetingPOJO> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, fromDateTime);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new FriendMeetingPOJO(
                            rs.getInt("id_slot"),
                            rs.getLong("date_time"),
                            rs.getBoolean("is_confirmed"),
                            rs.getString("comment"),
                            rs.getString("name")
                    ));
                }
            }
        }

        return list;
    }
    public boolean confirmMeetingById(int meetingId) throws SQLException{
        String sql = "UPDATE meeting_slot SET is_confirmed = true WHERE id_slot = ? AND is_confirmed = false";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, meetingId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}

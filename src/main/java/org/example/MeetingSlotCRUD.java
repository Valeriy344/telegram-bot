package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MeetingSlotCRUD {
    private Connection connection;

    public MeetingSlotCRUD(Connection connection) {
        this.connection = connection;
    }

    public boolean isSlotTaken(long dateTimeStart, long dateTimeFinish) throws SQLException {
        String sql = "SELECT COUNT(*) FROM meeting_slot WHERE date_time >= ? AND date_time <= ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, dateTimeStart);
            statement.setLong(2, dateTimeFinish);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean addMeetingSlot(MeetingSlotPOJO slot) throws SQLException {
        String sql = "INSERT INTO meeting_slot (id_friend, date_time, comment) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, slot.getIdFriend());
            statement.setLong(2, slot.getDateTime());
            statement.setString(3, slot.getComment());
            return statement.executeUpdate() > 0;
        }
    }
    public List<MeetingSlotPOJO> findMeeting(long idFriend, long fromDateTime) throws SQLException {
        String sql = "SELECT * FROM meeting_slot WHERE id_friend = ? AND date_time >= ?";
        List<MeetingSlotPOJO> meetings = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, idFriend);
            statement.setLong(2, fromDateTime);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    meetings.add(new MeetingSlotPOJO(
                            resultSet.getInt("id_slot"),
                            resultSet.getLong("id_friend"),
                            resultSet.getLong("date_time"),
                            resultSet.getBoolean("is_confirmed"),
                            resultSet.getString("comment")
                    ));
                }
            }
        }
        return meetings;
    }
    public boolean deleteMeeting(int slotId, long idFriend) throws SQLException {
        String sql = "DELETE FROM meeting_slot WHERE id_friend = ? AND id_slot = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, idFriend);
            statement.setInt(2, slotId);
            return statement.executeUpdate() > 0;
        }
    }
}
package org.example;

import java.sql.*;

public class FriendCRUD {
    private Connection connection;

    public FriendCRUD(Connection connection) {
        this.connection = connection;
    }
    public boolean findById (long idFriend) throws SQLException {
        String sql = "SELECT * FROM friends WHERE id_friend = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, idFriend);
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    return true;
                }
                return false;
            }
        }


    }
    public boolean addFriend(FriendPOJO friend) throws SQLException {
        String sql = "INSERT INTO friends (id_friend, name) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, friend.getIdFriend());
            statement.setString(2, friend.getName());
            return statement.executeUpdate() > 0;
        }
    }
}

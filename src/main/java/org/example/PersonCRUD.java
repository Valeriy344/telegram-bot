package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonCRUD {
    private Connection connection;
    public PersonCRUD(Connection connection) {
        this.connection = connection;
    }

    public List<PersonPOJO> findAll() throws SQLException {
        String sql = "SELECT * FROM person";
        List<PersonPOJO> list;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            list = new ArrayList<>();
            while (resultSet.next()) {
                int idPerson = resultSet.getInt(1);
                String namePerson = resultSet.getString(2);
                PersonPOJO personPOJO = new PersonPOJO(idPerson, namePerson);
                list.add(personPOJO);
            }
        }
        return list;
    }

    public void addPerson(PersonPOJO personPOJO) throws SQLException {
        String sql = "INSERT INTO person VALUES(?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, personPOJO.getIdPerson());
            preparedStatement.setString(2, personPOJO.getNamePerson());
            preparedStatement.executeUpdate();
        }
    }
}

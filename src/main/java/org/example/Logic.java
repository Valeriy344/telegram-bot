package org.example;

import java.sql.SQLException;
import java.util.List;

public class Logic {
    private PersonCRUD personCRUD;

    public Logic(PersonCRUD personCRUD) {
        this.personCRUD = personCRUD;
    }

    public StringBuilder findAll() throws SQLException {
        StringBuilder str = new StringBuilder();
        List<PersonPOJO> list = personCRUD.findAll();
        for (PersonPOJO p : list) {
            str.append(p.getIdPerson()).append("; ").append(p.getNamePerson()).append("\n");
        }
        return str;
    }

    public void addPerson(PersonPOJO personPOJO) throws SQLException {
        personCRUD.addPerson(personPOJO);
    }
}

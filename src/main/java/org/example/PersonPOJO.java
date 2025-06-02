package org.example;

public class PersonPOJO {
    private int idPerson;
    private String namePerson;

    public PersonPOJO(int idPerson, String namePerson) {
        this.idPerson = idPerson;
        this.namePerson = namePerson;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }
}

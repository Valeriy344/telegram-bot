package org.example;

public class FriendPOJO {
    private long idFriend;
    private String name;

    public FriendPOJO() {
    }

    public FriendPOJO(long idFriend, String name) {
        this.idFriend = idFriend;
        this.name = name;
    }

    public long getIdFriend() {
        return idFriend;
    }

    public void setIdFriend(long idFriend) {
        this.idFriend = idFriend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


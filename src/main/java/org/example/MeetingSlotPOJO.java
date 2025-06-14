package org.example;

public class MeetingSlotPOJO {
    private int idSlot;
    private long idFriend;
    private long dateTime;
    private boolean isConfirmed;
    private String comment;

    public MeetingSlotPOJO(int idSlot, long idFriend, long dateTime, boolean isConfirmed, String comment) {
        this.idSlot = idSlot;
        this.idFriend = idFriend;
        this.dateTime = dateTime;
        this.isConfirmed = isConfirmed;
        this.comment = comment;
    }
    public MeetingSlotPOJO(long idFriend, long dateTime, String comment) {
        this.idFriend = idFriend;
        this.dateTime = dateTime;
        this.comment = comment;
    }

    public int getIdSlot() {
        return idSlot;
    }

    public void setIdSlot(int idSlot) {
        this.idSlot = idSlot;
    }

    public long getIdFriend() {
        return idFriend;
    }

    public void setIdFriend(long idFriend) {
        this.idFriend = idFriend;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}


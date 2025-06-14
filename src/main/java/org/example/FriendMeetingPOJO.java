package org.example;

public class FriendMeetingPOJO {
    public FriendMeetingPOJO(int idSlot, long dateTime, boolean isConfirmed, String comment, String name) {
        this.idSlot = idSlot;
        this.dateTime = dateTime;
        this.isConfirmed = isConfirmed;
        this.comment = comment;
        this.name = name;
    }
    private int idSlot;
    private long dateTime;
    private boolean isConfirmed;
    private String comment;
    private String name;

    public int getIdSlot() {
        return idSlot;
    }

    public void setIdSlot(int idSlot) {
        this.idSlot = idSlot;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

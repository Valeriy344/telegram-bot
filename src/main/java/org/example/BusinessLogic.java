package org.example;

import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.List;

public class BusinessLogic {
    public BusinessLogic(MeetingSlotCRUD meetingSlotCRUD, FriendCRUD friendCRUD, FriendMeetingCRUD friendMeetingCRUD) {
        this.meetingSlotCRUD = meetingSlotCRUD;
        this.friendCRUD = friendCRUD;
        this.friendMeetingCRUD = friendMeetingCRUD;
    }
    public BusinessLogic (FriendMeetingCRUD friendMeetingCRUD){
        this.friendMeetingCRUD = friendMeetingCRUD;
    }

    private MeetingSlotCRUD meetingSlotCRUD;
    private FriendCRUD friendCRUD;
    private FriendMeetingCRUD friendMeetingCRUD;

    public boolean addMeeting (String info, long id, String name) throws SQLException {
        String [] data = info.split("\n");
        String dateTime = data[0] + " " + data[1];
        long res;
        try {
            res = DateTimeConvertor.convertStringToMillis(dateTime);

        } catch (DateTimeParseException e) {
            return false;
        }
        String currentDateTime = DateTimeConvertor.convertMillisToString(DateTimeConvertor.currentDateTime());
        if (!DateTimeConvertor.isRight(dateTime, currentDateTime)){
            return false;
        }
        String dateTimeStartDate = data[0] + " " + "00:01";
        String dateTimeFinishDate = data[0] + " " + "23:59";
        long dateTimeStrt = DateTimeConvertor.convertStringToMillis(dateTimeStartDate);
        long dateTimeFin = DateTimeConvertor.convertStringToMillis(dateTimeFinishDate);
        if(meetingSlotCRUD.isSlotTaken(dateTimeStrt, dateTimeFin)){
            return false;
        }
        if(!friendCRUD.findById(id)){
            FriendPOJO friendPOJO = new FriendPOJO(id, name);
            friendCRUD.addFriend(friendPOJO);

        }
        MeetingSlotPOJO meetingSlotPOJO = new MeetingSlotPOJO(id, res, data[2] );
        meetingSlotCRUD.addMeetingSlot(meetingSlotPOJO);
        return true;
    }
    public boolean removeMeeting(int slotId, long idFriend) throws SQLException {
        return meetingSlotCRUD.deleteMeeting(slotId, idFriend);
    }
    public String getAllUpcomingMeetings() throws SQLException {
        long now = DateTimeConvertor.currentDateTime();
        List<FriendMeetingPOJO> meetings = friendMeetingCRUD.findAllFromDate(now);

        if (meetings.isEmpty()) {
            return "Нет предстоящих встреч.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("ID | Пользователь | Время | Комментарий | Подтверждено\n");

        for (FriendMeetingPOJO meeting : meetings) {
            String dateTimeStr = DateTimeConvertor.convertMillisToString(meeting.getDateTime());
            sb.append(meeting.getIdSlot()).append(" | ")
                    .append(meeting.getName()).append(" | ")
                    .append(dateTimeStr).append(" | ")
                    .append(meeting.getComment()).append(" | ")
                    .append(meeting.isConfirmed()).append("\n");
        }



        return sb.toString();
    }

    public boolean confirmMeetingById(int meetingId) throws SQLException{
        return friendMeetingCRUD.confirmMeetingById(meetingId);
    }

}


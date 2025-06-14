package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Bot extends TelegramLongPollingBot {
    private static final String URL = "jdbc:postgresql://dpg-d0tdcce3jp1c73efk110-a.oregon-postgres.render.com:5432/name_lfb2";
    private static final String USER = "name_lfb2_user";
    private static final String PASSWORD = "q4tdCfeB4rToya0ZMVXiauz429cYo2eU";
    /*private static final String URL = "jdbc:postgresql://localhost:5432/baza";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1";*/



    @Override
    public String getBotUsername() {
        return "tic_tac_toe_botbotbot";
    }

    @Override
    public String getBotToken() {
        return "8128898379:AAEOpdFEyJWTGp_deX2_yIgePKVXQUt1FJI";
    }


    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override

    public void onUpdateReceived(Update update) {
        if(update.getMessage().getText().equals("Admin1234")){
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                FriendCRUD friendCRUD = new FriendCRUD(connection);
                MeetingSlotCRUD meetingSlotCRUD = new MeetingSlotCRUD(connection);
                FriendMeetingCRUD friendMeetingCRUD = new FriendMeetingCRUD(connection);
                BusinessLogic logic = new BusinessLogic(meetingSlotCRUD, friendCRUD, friendMeetingCRUD);
                sendText(update.getMessage().getFrom().getId(), logic.getAllUpcomingMeetings());

            } catch (SQLException e) {
                sendText(update.getMessage().getFrom().getId(),"Ошибка при получении встреч: " + e.getMessage());
            }
            System.out.println("All Meetings");
            return;
        }
        if (update.getMessage().getText().split(" ").length == 2) {
            String[] str = update.getMessage().getText().split(" ");
            if (str[0].equals("обновить")) {
                try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                    FriendMeetingCRUD friendMeetingCRUD = new FriendMeetingCRUD(connection);
                    BusinessLogic businessLogic = new BusinessLogic(friendMeetingCRUD);
                    int idMeeting = Integer.parseInt(str[1]);
                    System.out.println("обновляем");

                    boolean updated = businessLogic.confirmMeetingById(idMeeting);
                    if (updated) {
                        sendText(update.getMessage().getFrom().getId(), "Встреча с ID " + idMeeting + " подтверждена.");
                    } else {
                        sendText(update.getMessage().getFrom().getId(), "Встреча с ID " + idMeeting + " не найдена или уже подтверждена.");
                    }
                } catch (SQLException e) {
                    sendText(update.getMessage().getFrom().getId(), "Ошибка SQL: " + e.getMessage());
                }
            }
            return;
        }

        if (update.getMessage().getText().equals("/start") || update.getMessage().getText().equals("menu")) {
            sendText(update.getMessage().getFrom().getId(), "A. Забронировать встречу" + "\n" +
                    "B. Посмотреть встречи" + "\n" +
                    "C. Отменить встречу" + "\n" +
                    "D. Помощь");
            return;
        }
        if (update.getMessage().getText().equals("A")) {
            sendText(update.getMessage().getFrom().getId(), "Введите параметры встречи");
            return;
        }
        if (update.getMessage().getText().equals("B")){
            long viewId = update.getMessage().getFrom().getId();
            long currentMillis = DateTimeConvertor.currentDateTime();

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
                MeetingSlotCRUD meetingSlotCRUD = new MeetingSlotCRUD(connection);
                var meetings = meetingSlotCRUD.findMeeting(viewId, currentMillis);
                if (meetings.isEmpty()) {
                    sendText(update.getMessage().getFrom().getId(), "У вас нет запланированных встреч.");
                } else {
                    String str = "ID встречи:  | Время:  | Комментарий: " + "\n";
                    for (MeetingSlotPOJO meeting : meetings) {
                        str = str + meeting.getIdSlot() + "  |  ";
                        str = str + DateTimeConvertor.convertMillisToString(meeting.getDateTime()) + "  |  ";
                        str = str + meeting.isConfirmed() + "  |  ";
                        str = str + meeting.getComment() + "\n";
                    }
                    sendText(update.getMessage().getFrom().getId(), str);
                }
            } catch (SQLException e) {
                sendText(update.getMessage().getFrom().getId(),"Ошибка при получении встреч: " + e.getMessage());
            }
            return;
        }
        if (update.getMessage().getText().equals("C")){
            sendText(update.getMessage().getFrom().getId(), "Введите айди встречи, которую хотите отменить");
            return;
        }
        if (update.getMessage().getText().equals("D")) {
            sendText(update.getMessage().getFrom().getId(),
                    " -Помощь-\n\n" +
                            "A — dd-MM-yyyy HH:mm Комментарий — забронировать встречу\n" +
                            "B — посмотреть ваши встречи\n" +
                            "C — отменить встречу (введите ID встречи после этого)\n" +
                            "Пример для пункта меню А:\n" +
                            "22-05-2025\n" +
                            "18:00\n" +
                            "Обсудить проект\n\n" +
                            "По поводу вопросов, пишите пользователю @alittle_man"
            );
            return;
        }
        if (update.getMessage().getText().split("\n").length == 3){
            String[] parts = update.getMessage().getText().split("\n", 3);

            String userName = update.getMessage().getFrom().getUserName();
            /*System.out.println(userName);
            System.out.println(update.getMessage().getFrom().getFirstName());
            System.out.println(update.getMessage().getFrom().getLastName())*/
            String date = parts[0];            // Дата встречи
            String time = parts[1];            // Время встречи
            String comment = parts[2];         // Комментарий

            String info = date + "\n" + time + "\n" + comment;
            long id = update.getMessage().getFrom().getId();

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                FriendCRUD friendCRUD = new FriendCRUD(connection);
                MeetingSlotCRUD meetingSlotCRUD = new MeetingSlotCRUD(connection);
                BusinessLogic logic = new BusinessLogic(meetingSlotCRUD, friendCRUD, null);
                boolean added = logic.addMeeting(info, id, userName);
                if (added) {
                    sendText(id, "✅ Встреча успешно забронирована.");
                } else {
                    sendText(id, "❌ Ошибка: дата вне 7 дней, слот занят или формат неверный.");
                }
            } catch (SQLException e) {
                sendText(id, "Ошибка SQL: " + e.getMessage());
            }
            return;
        }
        if(Integer.parseInt(update.getMessage().getText()) > 0){
            long delId = update.getMessage().getFrom().getId();
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
                MeetingSlotCRUD meetingSlotCRUD = new MeetingSlotCRUD(connection);
                var meetingsToDelete = meetingSlotCRUD.findMeeting(delId, 0);

                if (meetingsToDelete.isEmpty()) {
                    sendText(update.getMessage().getFrom().getId(), "У вас нет встреч для удаления.");
                    return;
                }

            /*for (MeetingSlotPOJO meeting : meetingsToDelete) {
                System.out.printf("ID встречи: %d | Время: %s | Комментарий: %s%n",
                        meeting.getIdSlot(),
                        DateTimeConvertor.convertMillisToString(meeting.getDateTime()),
                        meeting.getComment());
            }*/

                int slotId = Integer.parseInt(update.getMessage().getText());
                FriendCRUD friendCRUD = new FriendCRUD(connection);
                BusinessLogic logic = new BusinessLogic(meetingSlotCRUD, friendCRUD, null);

                if (logic.removeMeeting(slotId, delId)) {
                    sendText(update.getMessage().getFrom().getId(), "Встреча успешно отменена.");
                } else {
                    sendText(update.getMessage().getFrom().getId(), "Ошибка при удалении встречи.");
                }
            } catch (SQLException e) {
                sendText(update.getMessage().getFrom().getId(),"Ошибка при получении встреч: " + e.getMessage());
            }
        }
    }
}
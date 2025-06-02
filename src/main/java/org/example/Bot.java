package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Bot extends TelegramLongPollingBot {

    private static final String URL = "jdbc:postgresql://dpg-d0spa1ali9vc73d841v0-a.oregon-postgres.render.com:5432/telegram_bot_x5en";
    private static final String USER = "telegram_bot_x5en_user";
    private static final String PASSWORD = "PgzQh8ObwsNxHBuPMm9pVg2RzFN2bdTc";

    @Override
    public String getBotUsername() {
        return "javaBeginingbot";
    }

    @Override
    public String getBotToken() {
        return "8116745257:AAGTjrT0f1FWp8ZogR2C_7_ilyvhKrALiyQ";
    }

    public void sendText(Long who, String what){
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
        if (update.getMessage().getText().split(" ").length == 2) {
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String[] str = update.getMessage().getText().split(" ");
                PersonCRUD personCRUD = new PersonCRUD(connection);
                Logic logic = new Logic(personCRUD);

                logic.addPerson(new PersonPOJO(Integer.parseInt(str[0]), str[1]));
                sendText(update.getMessage().getFrom().getId(), "Data was added");
            } catch (SQLException ex) {
                sendText(update.getMessage().getFrom().getId(), ex.getMessage());
            }
        }
        if (update.getMessage().getText().equals("show")) {
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                PersonCRUD personCRUD = new PersonCRUD(connection);
                Logic logic = new Logic(personCRUD);

                sendText(update.getMessage().getFrom().getId(), logic.findAll().toString());
            } catch (SQLException ex) {
                sendText(update.getMessage().getFrom().getId(), ex.getMessage());
            }
        }
    }
}


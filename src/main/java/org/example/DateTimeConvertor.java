package org.example;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeConvertor {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static long convertStringToMillis(String dateTimeString)throws DateTimeParseException {
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = dateTime.atZone(zoneId);
        Instant instant = zonedDateTime.toInstant();
        return instant.toEpochMilli();
    }
    public static boolean isRight(String dateTimeMeeting, String currentDateTime) {
        long meetingMillis = convertStringToMillis(dateTimeMeeting);
        long currentMillis = convertStringToMillis(currentDateTime);
        long sevenDaysInMillis = 7L * 24 * 60 * 60 * 1000;

        return meetingMillis >= currentMillis && meetingMillis <= currentMillis + sevenDaysInMillis;
    }
    public static long currentDateTime (){
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault(); // или указать явный: ZoneId.of("Europe/Moscow")

        Instant instant = localDateTime.atZone(zoneId).toInstant();
        long millis = instant.toEpochMilli();

        return millis;
    }
    public static String convertMillisToString(long millis) {
        Instant instant = Instant.ofEpochMilli(millis);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);
        LocalDateTime dateTime = zonedDateTime.toLocalDateTime();
        return dateTime.format(formatter);
    }

}

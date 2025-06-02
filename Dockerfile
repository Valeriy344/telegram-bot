# Используем официальный образ с OpenJDK 17
FROM openjdk:23-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный jar в контейнер
COPY target/bot-1.0-SNAPSHOT-jar-with-dependencies.jar /app/bot.jar

# Добавим bash-обертку, которая запускает и бот, и сервер-заглушку
COPY start.sh /app/start.sh
RUN chmod +x /app/start.sh

CMD ["./start.sh"]
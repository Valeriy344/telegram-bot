FROM openjdk:23-jdk-slim

# Устанавливаем netcat
RUN apt-get update && apt-get install -y netcat && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Копируем fat JAR и скрипт запуска
COPY target/bot-1.0-SNAPSHOT-jar-with-dependencies.jar /app/bot.jar
COPY start.sh /app/start.sh

RUN chmod +x /app/start.sh

CMD ["./start.sh"]
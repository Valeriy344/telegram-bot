FROM openjdk:23-jdk-slim

RUN apt-get update && apt-get install -y netcat-openbsd && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY target/bot-1.0-SNAPSHOT-jar-with-dependencies.jar /app/bot.jar
COPY start.sh /app/start.sh

RUN chmod +x /app/start.sh

CMD ["./start.sh"]
#!/bin/bash

# Запускаем бот в фоне
java -jar /app/bot.jar &

# Заглушка: простой HTTP-сервер на 8080
while true; do echo -e "HTTP/1.1 200 OK\r\n\r\nBot is running" | nc -l -p 8080; done
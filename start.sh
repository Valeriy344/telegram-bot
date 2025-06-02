#!/bin/bash

# Запускаем бота в фоне
java -jar /app/bot.jar &

# HTTP-заглушка, чтобы Render видел открытый порт
while true; do echo -e "HTTP/1.1 200 OK\r\n\r\nBot is running" | nc -l -p 8080; done
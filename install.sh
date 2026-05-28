#!/bin/bash

echo "Обновление списка пакетов..."
sudo apt update

echo "Установка git..."
sudo apt install -y git

echo "Установка Docker..."
sudo apt install -y docker.io

echo "Установка Docker Compose plugin..."
sudo apt install -y docker-compose-plugin

echo "Запуск и добавление Docker в автозагрузку..."
sudo systemctl enable docker
sudo systemctl start docker

echo "Сборка и запуск контейнеров в фоновом режиме..."
sudo docker compose up --build -d

echo "Инфраструктура запущена."

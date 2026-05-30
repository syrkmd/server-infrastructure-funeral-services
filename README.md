# Funeral Services Automation

Учебный проект курсовой работы: конфигурация серверной инфраструктуры для автоматизации процессов компании ритуальных услуг.

## Архитектура проекта

```text
Client
  ↓
Nginx
  ↓
Spring Boot API
  ↓
PostgreSQL

Backup.py / Restore.py
  ↓
PostgreSQL

Ansible
  ↓
Docker Compose
```

## Используемые технологии

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Bean Validation
- Springdoc OpenAPI / Swagger UI
- PostgreSQL 16
- Docker
- Docker Compose
- Nginx
- Bash scripts
- Python scripts
- Ansible

## Структура проекта

```text
src/main/java/ru/vladovich/funeralservices
├── controller     REST controllers
├── dto            DTO для запросов
├── entity         JPA сущности
├── exception      обработка ошибок
├── repository     Spring Data repositories
└── service        бизнес-логика

ansible/           Ansible inventory, config и playbook
backup.py          резервное копирование PostgreSQL
restore.py         восстановление PostgreSQL
demo.http          demo-сценарий для защиты
docker-compose.yml Docker-инфраструктура
nginx.conf         reverse proxy
```

## Запуск через Docker Compose

Сборка и запуск всей инфраструктуры:

```bash
docker compose up --build
```

Запуск в фоновом режиме:

```bash
docker compose up --build -d
```

Будут запущены сервисы:

- `postgres` - PostgreSQL 16, контейнер `course-postgres`
- `backend` - Spring Boot приложение
- `nginx` - reverse proxy на backend

REST API доступен через Nginx:

```text
http://localhost/api/clients
```

Swagger UI:

```text
http://localhost/swagger-ui.html
```

Данные PostgreSQL сохраняются в Docker volume `postgres_data`.

Проверка состояния PostgreSQL healthcheck:

```bash
docker inspect --format='{{json .State.Health}}' course-postgres
```

## Локальный запуск через Maven

Перед локальным запуском нужна база данных PostgreSQL:

```sql
CREATE DATABASE funeral_services;
```

Запуск приложения:

```bash
mvn spring-boot:run
```

Локально приложение будет доступно на `http://localhost:8080`.

## Bash-скрипты для инфраструктуры

Перед запуском выдайте права на выполнение:

```bash
chmod +x install.sh start.sh stop.sh logs.sh
```

Первичная установка и запуск на Ubuntu/Debian:

```bash
./install.sh
```

Запуск контейнеров:

```bash
./start.sh
```

Остановка контейнеров:

```bash
./stop.sh
```

Просмотр логов:

```bash
./logs.sh
```

## Backup и Restore

Скрипты используют стандартную библиотеку Python и выполняют `pg_dump`/`psql` внутри Docker-контейнера `course-postgres`.

Создание backup:

```bash
python3 backup.py
```

Backup-файлы сохраняются в папку `backups`. Имя файла содержит дату и время:

```text
backups/funeral_services_2026-05-28_15-30-00.sql
```

Восстановление из backup:

```bash
python3 restore.py backups/funeral_services_2026-05-28_15-30-00.sql
```

Ручная проверка доступности PostgreSQL в контейнере:

```bash
docker exec -e PGPASSWORD=postgres course-postgres pg_isready -U postgres -d funeral_services
```

## Ansible deployment

Ansible-конфигурация находится в папке `ansible` и предназначена для Fedora Linux.

Playbook выполняет:

- обновление пакетов через `dnf`
- подключение официального Docker RPM repository для Fedora 43
- установку `docker-ce`, `docker-ce-cli`, `containerd.io`, `docker-buildx-plugin`, `docker-compose-plugin`, `git`
- запуск и включение Docker service
- проверку команды `docker compose version`
- запуск `docker compose up --build -d`

Важно: для Fedora 43 пакет `docker-compose-plugin` устанавливается из официального репозитория Docker, а не из стандартных репозиториев Fedora. После установки используется современная команда `docker compose`, без дефиса.
Playbook рассчитан на чистую Fedora Server VM и не выполняет агрессивное удаление системных Docker/SELinux-пакетов через `dnf remove`.

Запуск из корня проекта:

```bash
ansible-playbook -K -i ansible/inventory.ini ansible/playbook.yml
```

## Demo сценарий

Для быстрой демонстрации API подготовлен файл `demo.http`.

Сценарий показывает:

- создание клиента
- создание сотрудника
- создание заявки
- создание расписания церемонии
- создание уведомления

После запуска Docker Compose выполните запросы из `demo.http` в IDE, которая поддерживает HTTP Client.

## Основные endpoints

- `GET /api/clients`
- `POST /api/clients`
- `GET /api/employees`
- `POST /api/employees`
- `GET /api/orders`
- `GET /api/orders?clientId=1`
- `POST /api/orders`
- `PUT /api/orders/{orderId}/assign-employee/{employeeId}`
- `GET /api/schedules`
- `GET /api/schedules?employeeId=1`
- `POST /api/schedules`
- `GET /api/notifications`
- `POST /api/notifications`

Для всех сущностей доступны CRUD операции через `GET`, `POST`, `PUT`, `DELETE`.

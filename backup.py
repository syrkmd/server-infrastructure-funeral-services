import os
import subprocess
from datetime import datetime

# Простые настройки базы данных для учебного проекта.
DATABASE_NAME = "funeral_services"
USERNAME = "postgres"
PASSWORD = "postgres"
POSTGRES_CONTAINER = "course-postgres"
BACKUP_DIR = "backups"


def create_backup():
    # Создаем папку backups, если ее еще нет.
    os.makedirs(BACKUP_DIR, exist_ok=True)

    # Добавляем дату и время в имя файла, чтобы backup-файлы не перезаписывались.
    current_time = datetime.now().strftime("%Y-%m-%d_%H-%M-%S")
    backup_file = os.path.join(BACKUP_DIR, f"{DATABASE_NAME}_{current_time}.sql")

    print("Создание резервной копии PostgreSQL...")
    print(f"База данных: {DATABASE_NAME}")
    print(f"Файл backup: {backup_file}")

    command = [
        "docker",
        "exec",
        "-e",
        f"PGPASSWORD={PASSWORD}",
        POSTGRES_CONTAINER,
        "pg_dump",
        "-U",
        USERNAME,
        "-d",
        DATABASE_NAME,
    ]

    try:
        # pg_dump пишет SQL в stdout, поэтому сохраняем результат в файл.
        with open(backup_file, "w", encoding="utf-8") as file:
            subprocess.run(command, stdout=file, check=True)
    except subprocess.CalledProcessError:
        print("Ошибка: не удалось создать backup PostgreSQL.")
        print("Проверьте, что Docker запущен и контейнер course-postgres работает.")
        raise SystemExit(1)
    except FileNotFoundError:
        print("Ошибка: команда docker не найдена.")
        print("Установите Docker или запустите скрипт на сервере с Docker.")
        raise SystemExit(1)

    print("Backup успешно создан.")


if __name__ == "__main__":
    create_backup()

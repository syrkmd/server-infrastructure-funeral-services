import os
import subprocess
import sys

# Простые настройки базы данных для учебного проекта.
DATABASE_NAME = "funeral_services"
USERNAME = "postgres"
PASSWORD = "postgres"
POSTGRES_CONTAINER = "course-postgres"


def restore_backup(backup_file):
    # Проверяем, что указанный backup-файл существует.
    if not os.path.exists(backup_file):
        print(f"Файл не найден: {backup_file}")
        sys.exit(1)

    print("Восстановление PostgreSQL из backup-файла...")
    print(f"База данных: {DATABASE_NAME}")
    print(f"Файл backup: {backup_file}")

    cleanup_command = [
        "docker",
        "exec",
        "-e",
        f"PGPASSWORD={PASSWORD}",
        POSTGRES_CONTAINER,
        "psql",
        "-U",
        USERNAME,
        "-d",
        DATABASE_NAME,
        "-c",
        "DROP SCHEMA public CASCADE; CREATE SCHEMA public;",
    ]

    try:
        print("Очистка текущей схемы public...")
        subprocess.run(cleanup_command, check=True)
        print("Схема public создана заново.")
    except subprocess.CalledProcessError:
        print("Ошибка: не удалось очистить схему public.")
        print("Проверьте, что Docker запущен и контейнер course-postgres работает.")
        raise SystemExit(1)
    except FileNotFoundError:
        print("Ошибка: команда docker не найдена.")
        print("Установите Docker или запустите скрипт на сервере с Docker.")
        raise SystemExit(1)

    command = [
        "docker",
        "exec",
        "-i",
        "-e",
        f"PGPASSWORD={PASSWORD}",
        POSTGRES_CONTAINER,
        "psql",
        "-U",
        USERNAME,
        "-d",
        DATABASE_NAME,
    ]

    # Передаем содержимое SQL-файла в stdin команды psql.
    try:
        print("Восстановление данных из backup-файла...")
        with open(backup_file, "r", encoding="utf-8") as file:
            subprocess.run(command, stdin=file, check=True)
    except subprocess.CalledProcessError:
        print("Ошибка: не удалось восстановить базу данных из backup-файла.")
        print("Проверьте SQL-файл и доступность контейнера course-postgres.")
        raise SystemExit(1)
    except FileNotFoundError:
        print("Ошибка: команда docker не найдена.")
        print("Установите Docker или запустите скрипт на сервере с Docker.")
        raise SystemExit(1)

    print("Восстановление успешно завершено.")


if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Использование: python restore.py backups/file.sql")
        sys.exit(1)

    restore_backup(sys.argv[1])

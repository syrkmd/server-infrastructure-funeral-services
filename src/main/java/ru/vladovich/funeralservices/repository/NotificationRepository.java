package ru.vladovich.funeralservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vladovich.funeralservices.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}

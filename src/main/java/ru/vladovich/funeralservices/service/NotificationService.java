package ru.vladovich.funeralservices.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vladovich.funeralservices.entity.Notification;
import ru.vladovich.funeralservices.exception.ResourceNotFoundException;
import ru.vladovich.funeralservices.repository.NotificationRepository;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Notification findById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
    }

    public Notification create(Notification notification) {
        if (notification.getSentAt() == null) {
            notification.setSentAt(LocalDateTime.now());
        }
        return notificationRepository.save(notification);
    }

    public Notification update(Long id, Notification notification) {
        Notification existingNotification = findById(id);
        existingNotification.setMessage(notification.getMessage());
        existingNotification.setRecipient(notification.getRecipient());
        existingNotification.setSentAt(notification.getSentAt());
        existingNotification.setType(notification.getType());
        return notificationRepository.save(existingNotification);
    }

    public void delete(Long id) {
        Notification notification = findById(id);
        notificationRepository.delete(notification);
    }
}

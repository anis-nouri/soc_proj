package traffic.trafficrestsoapapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import traffic.trafficrestsoapapi.entity.Event;
import traffic.trafficrestsoapapi.entity.Notification;
import traffic.trafficrestsoapapi.entity.User;
import traffic.trafficrestsoapapi.repo.NotificationRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Create a new notification
    public Notification createNotification(User user, Event event, String message, Date createdAt) {
        Notification notification = new Notification(user, event, message, createdAt);
        return notificationRepository.save(notification);
    }

    // Get all notifications
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // Get notification by ID
    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public List<Notification> getNotificationByUser(User user) {
        return notificationRepository.findByUser(user);
    }

    // Update notification
    public Notification updateNotification(Notification updatedNotification) {
        return notificationRepository.save(updatedNotification);
    }

    // Delete notification by ID
    public void deleteNotificationById(Long id) {
        notificationRepository.deleteById(id);
    }

}

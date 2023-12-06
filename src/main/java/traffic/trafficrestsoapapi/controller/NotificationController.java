package traffic.trafficrestsoapapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import traffic.trafficrestsoapapi.entity.Notification;
import traffic.trafficrestsoapapi.entity.User;
import traffic.trafficrestsoapapi.service.NotificationService;
import traffic.trafficrestsoapapi.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    private UserService userService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Other notification-related endpoints...

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Notification>> getNotificationsByUser(@PathVariable String username) {
        Optional<User> userOptional = userService.getUserById(username);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        List<Notification> notifications = notificationService.getNotificationByUser(user);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
}

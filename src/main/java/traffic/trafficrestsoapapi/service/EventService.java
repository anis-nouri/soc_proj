package traffic.trafficrestsoapapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import traffic.trafficrestsoapapi.entity.Event;
import traffic.trafficrestsoapapi.entity.Notification;
import traffic.trafficrestsoapapi.entity.User;

import org.springframework.stereotype.Service;
import traffic.trafficrestsoapapi.repo.EventRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    public Event createEvent(Event event) {
        Event createdEvent = eventRepository.save(event);
        List<User> allUsers = userService.getUsersByPosition(event.getAddress());

        for (User user : allUsers) {
            // Customize the message, createdAt, etc. based on your requirements
            notificationService.createNotification(user, createdEvent, createdEvent.getTitle(), new Date());
        }
    return createdEvent;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            updatedEvent.setId(id);
            return eventRepository.save(updatedEvent);
        }
        return null; // Or handle accordingly - throw an exception, return a default value, etc.
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
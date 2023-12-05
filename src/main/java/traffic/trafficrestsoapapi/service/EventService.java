package traffic.trafficrestsoapapi.service;

import traffic.trafficrestsoapapi.entity.Event;
import org.springframework.stereotype.Service;
import traffic.trafficrestsoapapi.repo.EventRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
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
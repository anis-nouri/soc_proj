package traffic.trafficrestsoapapi.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import traffic.trafficrestsoapapi.DTO.EventDTO;
import traffic.trafficrestsoapapi.DTO.UserDTO;
import traffic.trafficrestsoapapi.entity.Event;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import traffic.trafficrestsoapapi.entity.User;
import traffic.trafficrestsoapapi.service.EventService;
import traffic.trafficrestsoapapi.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    private UserService userService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();

        // Convert the list of events to a list of EventDTO
        List<EventDTO> eventDTOs = events.stream()
                .map(this::convertEventToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(eventDTOs, HttpStatus.OK);
    }

    private EventDTO convertEventToDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setDate(event.getDate());
        eventDTO.setLongitude(event.getLongitude());
        eventDTO.setLatitude(event.getLatitude());
        eventDTO.setAddress(event.getAddress());

        // Convert user to UserDTO without password
        UserDTO userDTO = convertUserToDTOWithoutPassword(event.getUser());
        eventDTO.setUser(userDTO);

        return eventDTO;
    }

    private UserDTO convertUserToDTOWithoutPassword(User user) {
        if (user == null) {
            return null; // or create and return a default UserDTO if needed
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setNom(user.getNom());
        userDTO.setPrenom(user.getPrenom());
        userDTO.setLieu(user.getLieu());
        userDTO.setPosition(user.getPosition());

        return userDTO;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event, @RequestParam String username) {
        Optional<User> userOptional = userService.getUserById(username);

        if (userOptional.isPresent()) {
            System.out.println("----------------");
            User authenticatedUser = userOptional.get();
            event.setUser(authenticatedUser);
            Event createdEvent = eventService.createEvent(event);
            return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
        } else {
            // Handle the case where the user with the given username is not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        Event updatedEvent = eventService.updateEvent(id, event);
        if (updatedEvent != null) {
            return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}


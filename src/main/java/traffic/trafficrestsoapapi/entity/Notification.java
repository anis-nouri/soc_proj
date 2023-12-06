package traffic.trafficrestsoapapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_username", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    private String message;

    private Date createdAt;

    private boolean isRead;

    public Notification(User user, Event event, String message, Date createdAt) {
        this.user = user;
        this.event = event;
        this.message = message;
        this.createdAt = createdAt;
        this.isRead = false;
    }
}
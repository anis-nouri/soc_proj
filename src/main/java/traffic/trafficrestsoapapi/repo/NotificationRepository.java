package traffic.trafficrestsoapapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import traffic.trafficrestsoapapi.entity.Event;
import traffic.trafficrestsoapapi.entity.Notification;
import traffic.trafficrestsoapapi.entity.User;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUser(User user);

    List<Notification> findByEvent(Event event);

    List<Notification> findByUserAndEvent(User user, Event event);

    // You can add more custom query methods as needed
}

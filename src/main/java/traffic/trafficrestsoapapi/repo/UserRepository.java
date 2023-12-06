package traffic.trafficrestsoapapi.repo;


import traffic.trafficrestsoapapi.entity.Event;
import traffic.trafficrestsoapapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByPosition(String position);

    // other methods...
}
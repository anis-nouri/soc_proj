package traffic.trafficrestsoapapi.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import traffic.trafficrestsoapapi.entity.User;
import traffic.trafficrestsoapapi.repo.UserRepository;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String username) {
        return userRepository.findById(username);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(String username, User updatedUser) {
        if (userRepository.existsById(username)) {
            updatedUser.setUsername(username); // Ensure the ID is set to the existing user's ID
            return userRepository.save(updatedUser);
        } else {
            // Handle case where the user with the given ID is not found
            return null;
        }
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public List<User> getUsersByPosition(String position) {
        return userRepository.findByPosition(position);
    }

}

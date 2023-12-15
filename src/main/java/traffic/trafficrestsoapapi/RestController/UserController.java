package traffic.trafficrestsoapapi.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import traffic.trafficrestsoapapi.DTO.UserDTO;
import traffic.trafficrestsoapapi.entity.User;
import traffic.trafficrestsoapapi.service.UserService;


import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{username}")

    public ResponseEntity<User> getUserById(@PathVariable String username) {
        Optional<User> user = userService.getUserById(username);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        String encodedpassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedpassword);
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User updatedUser) {
        User user = userService.updateUser(username, updatedUser);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/login")
public ResponseEntity<UserDTO> login(@RequestBody User loginUser) {
        // Retrieve user by username
        Optional<User> user = userService.getUserById(loginUser.getUsername());

        if (user.isPresent() && passwordEncoder.matches(loginUser.getPassword(), user.get().getPassword())) {
            // User is authenticated
            User authenticatedUser = user.get();

            // Create a UserDTO object with the necessary fields
            UserDTO response = new UserDTO(
                    authenticatedUser.getUsername(),
                    authenticatedUser.getNom(),
                    authenticatedUser.getPrenom(),
                    authenticatedUser.getLieu(),
                    authenticatedUser.getPosition());
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            UserDTO errorResponse = new UserDTO(null, null, null, null, null);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        }
        }
}

package app.chat.service;

import app.chat.model.User;
import app.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // GET

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private User getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    private String getUsernameByUserId(Integer id) {
        return getUserById(id).getUsername();
    }

    // POST

    public User saveUser(String username, String password) {
        User user = new User(username, password);
        return userRepository.save(user);
    }


    public User authenticateUser(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && !user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }


    public String removeUser(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null)
            return null;

        userRepository.deleteById(user.getId());
        return username;
    }


}

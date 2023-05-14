package app.chat.service;

import app.chat.model.User;
import app.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByName(String name) {
        return userRepository.getReferenceById(name);
    }

}

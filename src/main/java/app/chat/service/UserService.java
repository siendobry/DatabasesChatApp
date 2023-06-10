package app.chat.service;

import app.chat.model.User;
import app.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByName(String name) {
        return userRepository.getReferenceById(name);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}

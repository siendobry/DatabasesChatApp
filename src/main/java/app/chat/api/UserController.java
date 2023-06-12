package app.chat.api;

import app.chat.model.User;
import app.chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    // GET
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return createResponse(user);
    }

    // POST


    // TODO move to separate class
    record UserRequest(
            String username,
            String password
    ) {
    }


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRequest request) {
        User user = userService.saveUser(request.username(), request.password());
        return createResponse(user);
    }


    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserRequest request) {
        User user = userService.authenticateUser(request.username(), request.password());
        return createResponse(user);
    }


    // DELETE User knows their id which is returned in User object
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(@RequestBody User request) {

        String deletedUser = userService.removeUser(request.getUsername());
        if (deletedUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }

    // TODO future feature
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(@RequestBody UserRequest request){
//
//    }



    private ResponseEntity<User> createResponse(User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}

package app.chat.api;

import app.chat.model.User;
import app.chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    // GET
    @GetMapping
    public List<User.UserResponse> getAllUsers() {
        return userService.getAllUsers().stream().map(User::convertToResponse).collect(Collectors.toList());
    }


    @GetMapping("/{username}")
    public ResponseEntity<User.UserResponse> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return createResponse(null);
        }
        return createResponse(user.convertToResponse());
    }

    // POST


    // TODO move to separate class
    record UserRequest(
            String username,
            String password
    ) {
    }


    @PostMapping("/register")
    public ResponseEntity<User.UserResponse> register(@RequestBody UserRequest request) {
        User user = userService.saveUser(request.username(), request.password());
        return createResponse(user.convertToResponse());
    }


    @PostMapping("/login")
    public ResponseEntity<User.UserResponse> login(@RequestBody UserRequest request) {
        User user = userService.authenticateUser(request.username(), request.password());
        if (user == null) {
            return createResponse(null);
        }
        return createResponse(user.convertToResponse());
    }


    // DELETE User knows their id which is returned in User object
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(@RequestBody User.UserResponse request) {

        String deletedUser = userService.removeUser(request.username());
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



    private ResponseEntity<User.UserResponse> createResponse(User.UserResponse userResponse) {
        if (userResponse == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

}

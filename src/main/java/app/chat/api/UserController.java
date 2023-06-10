package app.chat.api;

import app.chat.model.User;
import app.chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")

@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    // TODO move to separate class
    record NewUserRequest(
            String username,
            String password
    ){}


    @PostMapping
    public ResponseEntity<String> createNewUser(@RequestBody NewUserRequest request) {
        User user = new User(request.username(), request.password());
        try {
            userService.saveUser(user);
            return ResponseEntity.ok("User created successfully.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }
    }




    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }






//    @DeleteMapping("{customerId}")
//    public void deleteCustomer(@PathVariable("customerId") Integer id){
//
//    }

}

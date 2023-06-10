package app.chat.api;

import app.chat.model.User;
import app.chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")

@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();

    }


//    @DeleteMapping("{customerId}")
//    public void deleteCustomer(@PathVariable("customerId") Integer id){
//
//    }

}

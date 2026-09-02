package job.controller;


import job.domain.User;
import job.dto.CreateUserRequest;
import job.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request.name(), request.skills(), request.experience());
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

}

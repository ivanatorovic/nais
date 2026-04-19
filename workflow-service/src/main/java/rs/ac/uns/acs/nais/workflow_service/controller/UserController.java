package rs.ac.uns.acs.nais.workflow_service.controller;

import rs.ac.uns.acs.nais.workflow_service.dto.UserDTO;
import rs.ac.uns.acs.nais.workflow_service.model.User;
import rs.ac.uns.acs.nais.workflow_service.service.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/creates")
    public User createCreatesRelationship(@RequestParam Long userId,
                                          @RequestParam Long workflowId,
                                          @RequestParam String createdAt) {

        return userService.createCreatesRelationship(userId, workflowId, createdAt);
    }
}
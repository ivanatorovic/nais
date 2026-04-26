package rs.ac.uns.acs.nais.workflow_service.controller;

import rs.ac.uns.acs.nais.workflow_service.dto.*;
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

    @PatchMapping("/creates")
    public User updateCreatesRelationship(@RequestParam Long userId,
                                          @RequestParam Long workflowId,
                                          @RequestParam String createdAt) {

        return userService.updateCreatesRelationship(userId, workflowId, createdAt);
    }

    @GetMapping("/creates")
    public List<User> findAllCreatesRelationships() {
        return userService.findAllCreatesRelationships();
    }

    @GetMapping("/creates/one")
    public UserCreatesDTO findOneCreatesRelationship(@RequestParam Long userId,
                                                     @RequestParam Long workflowId) {
        return userService.findOneCreatesRelationship(userId, workflowId);
    }

    @DeleteMapping("/{userId}/workflows/{workflowId}")
    public void deleteCreatesRelationship(@PathVariable Long userId,
                                          @PathVariable Long workflowId) {
        userService.deleteCreatesRelationship(userId, workflowId);
    }

    @PostMapping("/{userId}/publishes/{arrangementId}")
    public User createPublishesRelationship(@PathVariable Long userId, @PathVariable Long arrangementId) {
        return userService.createPublishesRelationship(userId, arrangementId);
    }

    @GetMapping("/publishes/{userId}/{arrangementId}")
    public UserPublishesDTO findOnePublishesRelationship(@PathVariable Long userId,
                                                         @PathVariable Long arrangementId) {
        return userService.findOnePublishesRelationship(userId, arrangementId);
    }

    @GetMapping("/publishes")
    public List<UserPublishesDTO> findAllPublishesRelationships() {
        return userService.findAllPublishesRelationships();
    }
    @DeleteMapping("/{userId}/publishes/{arrangementId}")
    public void deletePublishesRelationship(@PathVariable Long userId, @PathVariable Long arrangementId) {
        userService.deletePublishesRelationship(userId, arrangementId);
    }

    @GetMapping("/workflow-stats")
    public List<UserWorkflowStatsDTO> getUserWorkflowStats() {
        return userService.getUserWorkflowStats();
    }

    @GetMapping("/director-workflow-stats")
    public List<DirectorWorkflowStatsDTO> getDirectorWorkflowStats() {
        return userService.getDirectorWorkflowStats();
    }

    @GetMapping("/administrator-publish-stats")
    public List<AdministratorPublishStatsDTO> getAdministratorPublishStats() {
        return userService.getAdministratorPublishStats();
    }
}
package rs.ac.uns.acs.nais.workflow_service.service.impl;

import rs.ac.uns.acs.nais.workflow_service.dto.UserDTO;
import rs.ac.uns.acs.nais.workflow_service.model.User;
import rs.ac.uns.acs.nais.workflow_service.repository.UserRepository;
import rs.ac.uns.acs.nais.workflow_service.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + id
                ));

        return mapToDTO(user);
    }

    @Override
    public UserDTO createUser(UserDTO dto) {
        Long maxId = userRepository.findMaxId();
        Long newId = maxId + 1;

        User user = mapToEntity(dto);
        user.setId(newId);

        return mapToDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + id
                ));

        if (userDTO.getId() != null && !userDTO.getId().equals(id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Changing user ID is not allowed."
            );
        }

        if (userDTO.getFirstName() != null) {
            existingUser.setFirstName(userDTO.getFirstName());
        }

        if (userDTO.getLastName() != null) {
            existingUser.setLastName(userDTO.getLastName());
        }

        if (userDTO.getRole() != null) {
            existingUser.setRole(userDTO.getRole());
        }

        User updatedUser = userRepository.save(existingUser);
        return mapToDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found with id: " + id
            );
        }

        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRole(user.getRole());
        return dto;
    }

    private User mapToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(dto.getRole());
        return user;
    }

    @Override
    public User createCreatesRelationship(Long userId, Long workflowId, String createdAt) {
        return userRepository.createCreatesRelationship(userId, workflowId, createdAt);
    }

    @Override
    public User updateCreatesRelationship(Long userId, Long workflowId, String createdAt) {
        return userRepository.updateCreatesRelationship(userId, workflowId, createdAt);
    }

    @Override
    public User findOneCreatesRelationship(Long userId, Long workflowId) {
        User user = userRepository.findOneCreatesRelationship(userId, workflowId);

        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "CREATES relationship not found for userId: " + userId + " and workflowId: " + workflowId
            );
        }

        return user;
    }

    @Override
    public List<User> findAllCreatesRelationships() {
        return userRepository.findAllCreatesRelationships();
    }

    @Override
    public void deleteCreatesRelationship(Long userId, Long workflowId) {
        userRepository.deleteCreatesRelationship(userId, workflowId);
    }
}
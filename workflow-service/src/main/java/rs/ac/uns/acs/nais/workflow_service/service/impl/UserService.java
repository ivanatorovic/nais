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
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsById(userDTO.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User with id " + userDTO.getId() + " already exists."
            );
        }

        User user = mapToEntity(userDTO);
        User savedUser = userRepository.save(user);

        return mapToDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + id
                ));

        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setRole(userDTO.getRole());

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
}
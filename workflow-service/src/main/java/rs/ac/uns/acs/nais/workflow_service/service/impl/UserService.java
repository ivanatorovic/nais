package rs.ac.uns.acs.nais.workflow_service.service.impl;

import rs.ac.uns.acs.nais.workflow_service.dto.*;
import rs.ac.uns.acs.nais.workflow_service.model.Role;
import rs.ac.uns.acs.nais.workflow_service.model.User;
import rs.ac.uns.acs.nais.workflow_service.repository.UserRepository;
import rs.ac.uns.acs.nais.workflow_service.repository.WorkflowRepository;
import rs.ac.uns.acs.nais.workflow_service.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final WorkflowRepository workflowRepository;

    public UserService(UserRepository userRepository, WorkflowRepository workflowRepository) {
        this.userRepository = userRepository;
        this.workflowRepository = workflowRepository;
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

        userRepository.deleteUserByCustomId(id);
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + userId
                ));

        if (user.getRole() != Role.ADMINISTRATOR) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only ADMINISTRATOR can create workflow relationships."
            );
        }

        if (!workflowRepository.existsById(workflowId))  {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Workflow not found with id: " + workflowId
            );
        }

        if (userRepository.existsCreatorForWorkflow(workflowId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Workflow already has a creator."
            );
        }

        return userRepository.createCreatesRelationship(userId, workflowId, createdAt);
    }

    @Override
    public User updateCreatesRelationship(Long userId, Long workflowId, String createdAt) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + userId
                ));

        if (user.getRole() != Role.ADMINISTRATOR) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only ADMINISTRATOR can update workflow relationships."
            );
        }

        return userRepository.updateCreatesRelationship(userId, workflowId, createdAt);
    }

    @Override
    public UserCreatesDTO findOneCreatesRelationship(Long userId, Long workflowId) {
        User user = userRepository.findOneCreatesRelationship(userId, workflowId);

        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "CREATES relationship not found"
            );
        }

        return mapToCreatesDTO(user);
    }

    @Override
    public List<User> findAllCreatesRelationships() {
        return userRepository.findAllCreatesRelationships();
    }

    @Override
    public void deleteCreatesRelationship(Long userId, Long workflowId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + userId
                ));

        if (user.getRole() != Role.ADMINISTRATOR) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only ADMINISTRATOR can delete workflow relationships."
            );
        }

        userRepository.deleteCreatesRelationship(userId, workflowId);
    }

    @Override
    public User createPublishesRelationship(Long userId, Long arrangementId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + userId
                ));

        if (user.getRole() != Role.DIREKTOR) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only DIRECTOR can publish arrangements."
            );
        }

        if (userRepository.existsPublishesRelationshipForArrangement(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Arrangement is already published by another director."
            );
        }

        User result = userRepository.createPublishesRelationship(userId, arrangementId);

        if (result == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User or Arrangement not found."
            );
        }

        return result;
    }

    @Override
    public UserPublishesDTO findOnePublishesRelationship(Long userId, Long arrangementId) {
        User user = userRepository.findOnePublishesRelationship(userId, arrangementId);

        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "PUBLISHES relationship not found for userId: " + userId + " and arrangementId: " + arrangementId
            );
        }

        return mapToPublishesDTO(user);
    }

    @Override
    public List <UserPublishesDTO> findAllPublishesRelationships() {
        return userRepository.findAllPublishesRelationships()
                .stream()
                .map(this::mapToPublishesDTO)
                .toList();
    }

    @Override
    public void deletePublishesRelationship(Long userId, Long arrangementId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + userId
                ));

        if (user.getRole() != Role.DIREKTOR) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only DIRECTOR can delete publishes relationships."
            );
        }

        User existing = userRepository.findOnePublishesRelationship(userId, arrangementId);
        if (existing == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "PUBLISHES relationship not found for userId: " + userId + " and arrangementId: " + arrangementId
            );
        }

        userRepository.deletePublishesRelationship(userId, arrangementId);
    }

    @Override
    public List<UserWorkflowStatsDTO> getUserWorkflowStats() {
        return userRepository.getUserWorkflowStats();
    }

    @Override
    public List<DirectorWorkflowStatsDTO> getDirectorWorkflowStats() {
        return userRepository.getDirectorWorkflowStats();
    }

    @Override
    public List<AdministratorPublishStatsDTO> getAdministratorPublishStats() {
        return userRepository.getAdministratorPublishStats();
    }

    private UserPublishesDTO mapToPublishesDTO(User user) {
        List<PublishedArrangementDTO> arrangements = user.getPublishedArrangements()
                .stream()
                .map(a -> new PublishedArrangementDTO(
                        a.getId(),
                        a.getName(),
                        a.getDestination(),
                        a.getDescription(),
                        a.getStartDate(),
                        a.getEndDate()
                ))
                .toList();

        return new UserPublishesDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().toString(),
                arrangements
        );
    }

    private UserCreatesDTO mapToCreatesDTO(User user) {
        List<CreatedWorkflowDTO> workflows = user.getCreatedWorkflows()
                .stream()
                .map(c -> new CreatedWorkflowDTO(
                        c.getWorkflow().getId(),
                        c.getWorkflow().getName(),
                        c.getCreatedAt()
                ))
                .toList();

        return new UserCreatesDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().toString(),
                workflows
        );
    }
}
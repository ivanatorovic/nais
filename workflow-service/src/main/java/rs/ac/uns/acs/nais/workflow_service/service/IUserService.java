package rs.ac.uns.acs.nais.workflow_service.service;

import rs.ac.uns.acs.nais.workflow_service.dto.*;
import rs.ac.uns.acs.nais.workflow_service.model.User;

import java.util.List;

public interface IUserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);

    User createCreatesRelationship(Long userId, Long workflowId, String createdAt);

    User updateCreatesRelationship(Long userId, Long workflowId, String createdAt);

    UserCreatesDTO findOneCreatesRelationship(Long userId, Long workflowId);

    List<User> findAllCreatesRelationships();

    void deleteCreatesRelationship(Long userId, Long workflowId);

    User createPublishesRelationship(Long userId, Long arrangementId);

    UserPublishesDTO findOnePublishesRelationship(Long userId, Long arrangementId);

    List<UserPublishesDTO> findAllPublishesRelationships();

    void deletePublishesRelationship(Long userId, Long arrangementId);

    List<UserWorkflowStatsDTO> getUserWorkflowStats();

    List<DirectorWorkflowStatsDTO> getDirectorWorkflowStats();

    List<AdministratorPublishStatsDTO> getAdministratorPublishStats();
}
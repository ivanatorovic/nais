package rs.ac.uns.acs.nais.workflow_service.service;

import rs.ac.uns.acs.nais.workflow_service.dto.AdministratorPublishStatsDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.DirectorWorkflowStatsDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.UserDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.UserWorkflowStatsDTO;
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

    User findOneCreatesRelationship(Long userId, Long workflowId);

    List<User> findAllCreatesRelationships();

    void deleteCreatesRelationship(Long userId, Long workflowId);

    User createPublishesRelationship(Long userId, Long arrangementId);

    User findOnePublishesRelationship(Long userId, Long arrangementId);

    List<User> findAllPublishesRelationships();

    void deletePublishesRelationship(Long userId, Long arrangementId);

    List<UserWorkflowStatsDTO> getUserWorkflowStats();

    List<DirectorWorkflowStatsDTO> getDirectorWorkflowStats();

    List<AdministratorPublishStatsDTO> getAdministratorPublishStats();
}
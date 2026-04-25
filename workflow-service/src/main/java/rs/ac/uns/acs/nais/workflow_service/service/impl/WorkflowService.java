package rs.ac.uns.acs.nais.workflow_service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.acs.nais.workflow_service.dto.WorkflowDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.WorkflowOfferStatsDTO;
import rs.ac.uns.acs.nais.workflow_service.model.Role;
import rs.ac.uns.acs.nais.workflow_service.model.User;
import rs.ac.uns.acs.nais.workflow_service.model.Workflow;
import rs.ac.uns.acs.nais.workflow_service.repository.UserRepository;
import rs.ac.uns.acs.nais.workflow_service.repository.WorkflowRepository;
import rs.ac.uns.acs.nais.workflow_service.service.IWorkflowService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkflowService implements IWorkflowService {

    private final WorkflowRepository workflowRepository;
    private final UserRepository userRepository;

    public WorkflowService(WorkflowRepository workflowRepository, UserRepository userRepository) {
        this.workflowRepository = workflowRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<WorkflowDTO> getAllWorkflows() {
        return workflowRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WorkflowDTO getWorkflowById(Long id) {
        Workflow workflow = workflowRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Workflow not found with id: " + id
                ));

        return mapToDTO(workflow);
    }

    @Override
    public WorkflowDTO createWorkflow(Long userId, WorkflowDTO workflowDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + userId
                ));

        if (user.getRole() != Role.ADMINISTRATOR) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only ADMINISTRATOR can create workflows."
            );
        }

        Long maxId = workflowRepository.findMaxId();
        Long newId = maxId + 1;

        Workflow workflow = mapToEntity(workflowDTO);
        workflow.setId(newId);

        Workflow savedWorkflow = workflowRepository.save(workflow);

        userRepository.createCreatesRelationship(userId, savedWorkflow.getId(), java.time.LocalDate.now().toString());

        return mapToDTO(savedWorkflow);
    }

    @Override
    public WorkflowDTO updateWorkflow(Long id, WorkflowDTO workflowDTO) {
        Workflow existing = workflowRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Workflow not found with id: " + id
                ));

        if (workflowDTO.getId() != null && !workflowDTO.getId().equals(id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Changing workflow ID is not allowed."
            );
        }

        if (workflowDTO.getName() != null) {
            existing.setName(workflowDTO.getName());
        }

        Workflow updatedWorkflow = workflowRepository.save(existing);
        return mapToDTO(updatedWorkflow);
    }

    @Override
    public void deleteWorkflow(Long id) {
        if (!workflowRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Workflow not found with id: " + id
            );
        }

        workflowRepository.deleteWorkflowByCustomId(id);
    }

    private WorkflowDTO mapToDTO(Workflow workflow) {
        WorkflowDTO dto = new WorkflowDTO();
        dto.setId(workflow.getId());
        dto.setName(workflow.getName());
        return dto;
    }

    private Workflow mapToEntity(WorkflowDTO dto) {
        Workflow workflow = new Workflow();
        workflow.setId(dto.getId());
        workflow.setName(dto.getName());
        return workflow;
    }

    @Override
    public List<WorkflowOfferStatsDTO> getWorkflowOfferStats() {
        return workflowRepository.getWorkflowOfferStats();
    }
}
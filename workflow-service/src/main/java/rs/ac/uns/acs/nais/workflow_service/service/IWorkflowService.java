package rs.ac.uns.acs.nais.workflow_service.service;

import rs.ac.uns.acs.nais.workflow_service.dto.WorkflowDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.WorkflowOfferStatsDTO;

import java.util.List;

public interface IWorkflowService {

    List<WorkflowDTO> getAllWorkflows();

    WorkflowDTO getWorkflowById(Long id);

    WorkflowDTO createWorkflow(Long userId, WorkflowDTO workflowDTO);

    WorkflowDTO updateWorkflow(Long id, WorkflowDTO workflowDTO);

    void deleteWorkflow(Long id);

    List<WorkflowOfferStatsDTO> getWorkflowOfferStats();
}
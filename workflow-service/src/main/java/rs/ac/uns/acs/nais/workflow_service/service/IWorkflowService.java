package rs.ac.uns.acs.nais.workflow_service.service;

import rs.ac.uns.acs.nais.workflow_service.dto.WorkflowDTO;

import java.util.List;

public interface IWorkflowService {

    List<WorkflowDTO> getAllWorkflows();

    WorkflowDTO getWorkflowById(Long id);

    WorkflowDTO createWorkflow(WorkflowDTO workflowDTO);

    WorkflowDTO updateWorkflow(Long id, WorkflowDTO workflowDTO);

    void deleteWorkflow(Long id);
}
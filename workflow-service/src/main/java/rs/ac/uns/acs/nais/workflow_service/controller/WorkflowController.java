package rs.ac.uns.acs.nais.workflow_service.controller;

import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.workflow_service.dto.WorkflowDTO;
import rs.ac.uns.acs.nais.workflow_service.service.IWorkflowService;

import java.util.List;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {

    private final IWorkflowService workflowService;

    public WorkflowController(IWorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @GetMapping
    public List<WorkflowDTO> getAllWorkflows() {
        return workflowService.getAllWorkflows();
    }

    @GetMapping("/{id}")
    public WorkflowDTO getWorkflowById(@PathVariable Long id) {
        return workflowService.getWorkflowById(id);
    }

    @PostMapping
    public WorkflowDTO createWorkflow(@RequestBody WorkflowDTO workflowDTO) {
        return workflowService.createWorkflow(workflowDTO);
    }

    @PatchMapping("/{id}")
    public WorkflowDTO updateWorkflow(@PathVariable Long id, @RequestBody WorkflowDTO workflowDTO) {
        return workflowService.updateWorkflow(id, workflowDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkflow(@PathVariable Long id) {
        workflowService.deleteWorkflow(id);
    }
}
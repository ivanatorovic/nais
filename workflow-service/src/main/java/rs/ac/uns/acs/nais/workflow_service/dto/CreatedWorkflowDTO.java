package rs.ac.uns.acs.nais.workflow_service.dto;

public class CreatedWorkflowDTO {
    private Long workflowId;
    private String workflowName;
    private String createdAt;

    public CreatedWorkflowDTO() {}

    public CreatedWorkflowDTO(Long workflowId, String workflowName, String createdAt) {
        this.workflowId = workflowId;
        this.workflowName = workflowName;
        this.createdAt = createdAt;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

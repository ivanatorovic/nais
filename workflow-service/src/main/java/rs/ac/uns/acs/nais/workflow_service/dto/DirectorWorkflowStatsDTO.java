package rs.ac.uns.acs.nais.workflow_service.dto;

public class DirectorWorkflowStatsDTO {
    private Long direktorId;
    private String firstName;
    private String lastName;
    private Long workflowId;
    private String workflowName;
    private Long brojAranzmana;

    public DirectorWorkflowStatsDTO() {}

    public Long getDirektorId() {
        return direktorId;
    }

    public void setDirektorId(Long direktorId) {
        this.direktorId = direktorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Long getBrojAranzmana() {
        return brojAranzmana;
    }

    public void setBrojAranzmana(Long brojAranzmana) {
        this.brojAranzmana = brojAranzmana;
    }
}

package rs.ac.uns.acs.nais.workflow_service.dto;

import java.util.List;

public class UserCreatesDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String role;
    private List<CreatedWorkflowDTO> createdWorkflows;

    public UserCreatesDTO() {}

    public UserCreatesDTO(Long id, String firstName, String lastName,
                          String role, List<CreatedWorkflowDTO> createdWorkflows) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.createdWorkflows = createdWorkflows;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public List<CreatedWorkflowDTO> getCreatedWorkflows() {
        return createdWorkflows;
    }
}

package rs.ac.uns.acs.nais.workflow_service.dto;

public class WorkflowDTO {

    private Long id;
    private String name;

    public WorkflowDTO() {
    }

    public WorkflowDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
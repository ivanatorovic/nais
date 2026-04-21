package rs.ac.uns.acs.nais.workflow_service.model;

import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
public class Creates {
    @RelationshipId
    private Long id;

    @TargetNode
    private Workflow workflow;

    private String createdAt;

    public Creates() {}

    public Creates(Workflow workflow, String createdAt) {
        this.workflow = workflow;
        this.createdAt = createdAt;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
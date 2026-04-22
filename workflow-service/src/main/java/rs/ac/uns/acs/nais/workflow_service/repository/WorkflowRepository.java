package rs.ac.uns.acs.nais.workflow_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.acs.nais.workflow_service.model.Workflow;

public interface WorkflowRepository extends Neo4jRepository<Workflow, Long> {
    @Query("MATCH (w:Workflow) RETURN coalesce(max(w.id), 0)")
    Long findMaxId();
}
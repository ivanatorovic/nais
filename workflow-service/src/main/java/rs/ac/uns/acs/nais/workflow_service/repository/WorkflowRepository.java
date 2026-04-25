package rs.ac.uns.acs.nais.workflow_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.acs.nais.workflow_service.dto.WorkflowOfferStatsDTO;
import rs.ac.uns.acs.nais.workflow_service.model.Workflow;

import java.util.List;

public interface WorkflowRepository extends Neo4jRepository<Workflow, Long> {
    @Query("MATCH (w:Workflow) RETURN coalesce(max(w.id), 0)")
    Long findMaxId();

    @Query("""
    MATCH (w:Workflow {id: $id})
    DETACH DELETE w
    """)
    void deleteWorkflowByCustomId(Long id);

    @Query("""
    MATCH (w:Workflow)<-[:BASED_ON]-(a:Arrangement)-[:HAS_OFFER]->(o:Offer)
    WITH w,
         count(DISTINCT a) AS brojAranzmana,
         count(o) AS ukupanBrojPonuda
    WHERE brojAranzmana >= 1
    RETURN w.id AS workflowId,
           w.name AS workflowName,
           brojAranzmana,
           ukupanBrojPonuda,
           round(1.0 * ukupanBrojPonuda / brojAranzmana, 2) AS prosecnoPonudaPoAranzmanu
    ORDER BY prosecnoPonudaPoAranzmanu DESC
    """)
    List<WorkflowOfferStatsDTO> getWorkflowOfferStats();
}
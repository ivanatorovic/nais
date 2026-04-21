package rs.ac.uns.acs.nais.workflow_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.acs.nais.workflow_service.model.Arrangement;

public interface ArrangementRepository extends Neo4jRepository<Arrangement, Long> {
    @Query("""
           MATCH (a:Arrangement {id: $arrangementId}), (w:Workflow {id: $workflowId})
           MERGE (a)-[:BASED_ON]->(w)
           RETURN a
           """)
    Arrangement createBasedOnRelationship(Long arrangementId, Long workflowId);

    @Query("""
           MATCH (a:Arrangement {id: $arrangementId})-[r:BASED_ON]->(w:Workflow)
           DELETE r
           """)
    void deleteBasedOnRelationship(Long arrangementId);

    @Query("""
           MATCH (a:Arrangement {id: $arrangementId})-[:BASED_ON]->(w:Workflow)
           RETURN a
           """)
    Arrangement findArrangementWithWorkflow(Long arrangementId);

    @Query("""
           MATCH (a:Arrangement {id: $arrangementId}), (o:Offer {id: $offerId})
           MERGE (a)-[:HAS_OFFER]->(o)
           RETURN a
           """)
    Arrangement addOfferToArrangement(Long arrangementId, Long offerId);

    @Query("""
           MATCH (a:Arrangement {id: $arrangementId})-[r:HAS_OFFER]->(o:Offer {id: $offerId})
           DELETE r
           """)
    void deleteOfferFromArrangement(Long arrangementId, Long offerId);

    @Query("""
           MATCH (a:Arrangement {id: $arrangementId})-[:HAS_OFFER]->(o:Offer)
           RETURN a
           """)
    Arrangement findArrangementWithOffers(Long arrangementId);
}
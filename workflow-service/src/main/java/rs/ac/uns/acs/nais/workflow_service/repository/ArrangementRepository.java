package rs.ac.uns.acs.nais.workflow_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.acs.nais.workflow_service.model.Arrangement;

public interface ArrangementRepository extends Neo4jRepository<Arrangement, Long> {
    @Query("MATCH (a:Arrangement) RETURN coalesce(max(a.id), 0)")
    Long findMaxId();

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
        MATCH (a:Arrangement {id: $arrangementId}), (o:Offer {id: $offerId})
        WHERE NOT EXISTS {
            MATCH (:Arrangement)-[:HAS_OFFER]->(o)
        }
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
    MATCH (:Arrangement)-[r:HAS_OFFER]->(o:Offer {id: $offerId})
    RETURN count(r) > 0
    """)
    boolean existsHasOfferRelationship(Long offerId);

    @Query("""
    MATCH (a:Arrangement {id: $arrangementId})
    OPTIONAL MATCH (a)-[r:HAS_OFFER]->(o:Offer)
    RETURN a, collect(r), collect(o)
    """)
    Arrangement findArrangementWithOffers(Long arrangementId);

    @Query("""
    MATCH (a:Arrangement {id: $arrangementId})-[r:BASED_ON]->(w:Workflow)
    RETURN a, r, w
    """)
    Arrangement findArrangementWithWorkflow(Long arrangementId);
}
package rs.ac.uns.acs.nais.workflow_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.acs.nais.workflow_service.dto.ArrangementPriceDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.ArrangementRatingDTO;
import rs.ac.uns.acs.nais.workflow_service.model.Arrangement;

import java.util.List;

public interface ArrangementRepository extends Neo4jRepository<Arrangement, Long> {
    @Query("MATCH (a:Arrangement) RETURN coalesce(max(a.id), 0)")
    Long findMaxId();

    @Query("""
    MATCH (a:Arrangement {id: $id})
    DETACH DELETE a
    """)
    void deleteArrangementByCustomId(Long id);

    @Query("""
    MATCH (a:Arrangement {id: $arrangementId})-[r:BASED_ON]->(:Workflow)
    RETURN count(r) > 0
    """)
    boolean existsBasedOnRelationship(Long arrangementId);

    @Query("""
    MATCH (a:Arrangement {id: $arrangementId})-[r:HAS_OFFER]->(o:Offer {id: $offerId})
    RETURN count(r) > 0
    """)
    boolean existsHasOfferRelationshipForArrangement(Long arrangementId, Long offerId);

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

    @Query("""
    MATCH (a:Arrangement)-[:HAS_OFFER]->(o:Offer)
    WHERE o.adultsPrice IS NOT NULL AND o.kidsPrice IS NOT NULL
    WITH a,
         count(o) AS brojPonuda,
         sum(o.adultsPrice) AS ukupnaCenaZaOdrasle,
         sum(o.kidsPrice) AS ukupnaCenaZaDecu
    WHERE brojPonuda >= 1
    RETURN a.id AS arrangementId,
           a.name AS arrangementName,
           a.destination AS destination,
           brojPonuda,
           ukupnaCenaZaOdrasle,
           ukupnaCenaZaDecu
    ORDER BY ukupnaCenaZaOdrasle DESC, ukupnaCenaZaDecu DESC
    """)
    List<ArrangementPriceDTO> getArrangementPriceAnalysis();

    @Query("""
    MATCH (a:Arrangement)-[:HAS_OFFER]->(o:Offer)
    WHERE o.rating IS NOT NULL
    WITH a,
         count(o) AS brojPonuda,
         avg(o.rating) AS prosecnaOcena
    WHERE brojPonuda >= 1
    RETURN a.id AS arrangementId,
           a.name AS arrangementName,
           a.destination AS destination,
           brojPonuda,
           round(prosecnaOcena,3)
    ORDER BY prosecnaOcena DESC, brojPonuda DESC
    """)
    List<ArrangementRatingDTO> getArrangementRatings();


}
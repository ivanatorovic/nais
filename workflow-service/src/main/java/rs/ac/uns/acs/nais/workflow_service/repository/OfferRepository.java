package rs.ac.uns.acs.nais.workflow_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import rs.ac.uns.acs.nais.workflow_service.model.Offer;

public interface OfferRepository extends Neo4jRepository<Offer, Long> {
}
package rs.ac.uns.acs.nais.workflow_service.repository;

import rs.ac.uns.acs.nais.workflow_service.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.List;
import java.util.Map;

public interface UserRepository extends Neo4jRepository<User, Long> {

}
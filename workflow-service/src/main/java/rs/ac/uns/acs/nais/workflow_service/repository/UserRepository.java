package rs.ac.uns.acs.nais.workflow_service.repository;

import rs.ac.uns.acs.nais.workflow_service.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.List;
import java.util.Map;

public interface UserRepository extends Neo4jRepository<User, Long> {
    @Query("MATCH (u:User) RETURN coalesce(max(u.id), 0)")
    Long findMaxId();

    @Query("""
        MATCH (u:User {id: $userId}), (w:Workflow {id: $workflowId})
        MERGE (u)-[r:CREATES]->(w)
        SET r.createdAt = $createdAt
        RETURN u
        """)
    User createCreatesRelationship(Long userId, Long workflowId, String createdAt);

    @Query("""
       MATCH (u:User {id: $userId})-[r:CREATES]->(w:Workflow {id: $workflowId})
       SET r.createdAt = $createdAt
       RETURN u
       """)
    User updateCreatesRelationship(Long userId, Long workflowId, String createdAt);

    @Query("""
       MATCH (u:User {id: $userId})-[r:CREATES]->(w:Workflow {id: $workflowId})
       RETURN u, r, w
    """)
    User findOneCreatesRelationship(Long userId, Long workflowId);

    @Query("""
    MATCH (u:User)-[r:CREATES]->(w:Workflow)
    RETURN u, collect(r), collect(w)
    """)
    List<User> findAllCreatesRelationships();

    @Query("""
        MATCH (u:User {id: $userId})-[r:CREATES]->(w:Workflow {id: $workflowId})
        DELETE r
        """)
    void deleteCreatesRelationship(Long userId, Long workflowId);

}
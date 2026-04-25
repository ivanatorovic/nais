package rs.ac.uns.acs.nais.workflow_service.repository;

import rs.ac.uns.acs.nais.workflow_service.dto.AdministratorPublishStatsDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.DirectorWorkflowStatsDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.UserWorkflowStatsDTO;
import rs.ac.uns.acs.nais.workflow_service.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.List;
import java.util.Map;

public interface UserRepository extends Neo4jRepository<User, Long> {
    @Query("MATCH (u:User) RETURN coalesce(max(u.id), 0)")
    Long findMaxId();

    @Query("""
    MATCH (u:User {id: $id})
    DETACH DELETE u
    """)
    void deleteUserByCustomId(Long id);

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

    @Query("""
    MATCH (u:User {id: $userId}), (a:Arrangement {id: $arrangementId})
    WHERE NOT EXISTS {
        MATCH (:User)-[:PUBLISHES]->(a)
    }
    MERGE (u)-[:PUBLISHES]->(a)
    RETURN u
    """)
    User createPublishesRelationship(Long userId, Long arrangementId);

    @Query("""
    MATCH (u:User {id: $userId})-[r:PUBLISHES]->(a:Arrangement {id: $arrangementId})
    RETURN u, r, a
    """)
    User findOnePublishesRelationship(Long userId, Long arrangementId);

    @Query("""
    MATCH (u:User)-[r:PUBLISHES]->(a:Arrangement)
    RETURN u, collect(r), collect(a)
    """)
    List<User> findAllPublishesRelationships();

    @Query("""
    MATCH (u:User {id: $userId})-[r:PUBLISHES]->(a:Arrangement {id: $arrangementId})
    DELETE r
    """)
    void deletePublishesRelationship(Long userId, Long arrangementId);

    @Query("""
    MATCH (:User)-[r:PUBLISHES]->(a:Arrangement {id: $arrangementId})
    RETURN count(r) > 0
    """)
    boolean existsPublishesRelationshipForArrangement(Long arrangementId);

    @Query("""
    MATCH (u:User)-[:CREATES]->(w:Workflow)
    OPTIONAL MATCH (w)<-[:BASED_ON]-(a:Arrangement)
    WITH u,
         count(DISTINCT w) AS brojWorkflowa,
         count(DISTINCT a) AS brojAranzmana
    WHERE brojWorkflowa >= 1
    RETURN u.id AS userId,
           u.firstName AS firstName,
           u.lastName AS lastName,
           brojWorkflowa,
           brojAranzmana
    ORDER BY brojAranzmana DESC, brojWorkflowa DESC
    """)
    List<UserWorkflowStatsDTO> getUserWorkflowStats();

    @Query("""
    MATCH (u:User)-[:PUBLISHES]->(a:Arrangement)-[:BASED_ON]->(w:Workflow)
    WHERE u.role = "DIREKTOR"
    WITH u, w, count(a) AS brojAranzmana
    WHERE brojAranzmana >= 1
    RETURN u.id AS direktorId,
           u.firstName AS firstName,
           u.lastName AS lastName,
           w.id AS workflowId,
           w.name AS workflowName,
           brojAranzmana
    ORDER BY direktorId, brojAranzmana DESC, workflowName
    """)
    List<DirectorWorkflowStatsDTO> getDirectorWorkflowStats();

    @Query("""
    MATCH (u:User)-[:CREATES]->(w:Workflow)<-[:BASED_ON]-(a:Arrangement)
    WHERE u.role = "ADMINISTRATOR"
    WITH u, count(DISTINCT a) AS ukupanBrojAranzmana
    
    OPTIONAL MATCH (u)-[:CREATES]->(:Workflow)<-[:BASED_ON]-(objavljen:Arrangement)
    WHERE EXISTS { MATCH (:User)-[:PUBLISHES]->(objavljen) }
    WITH u, ukupanBrojAranzmana, count(DISTINCT objavljen) AS brojObjavljenih
    
    WHERE ukupanBrojAranzmana > 0
    RETURN u.id AS administratorId,
           u.firstName AS firstName,
           u.lastName AS lastName,
           ukupanBrojAranzmana,
           brojObjavljenih,
           round(100.0 * brojObjavljenih / ukupanBrojAranzmana, 2) AS procenatObjavljenih
    ORDER BY procenatObjavljenih DESC
    """)
    List<AdministratorPublishStatsDTO> getAdministratorPublishStats();
}
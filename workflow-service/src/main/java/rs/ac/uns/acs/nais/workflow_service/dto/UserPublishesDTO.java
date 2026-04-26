package rs.ac.uns.acs.nais.workflow_service.dto;

import java.util.List;

public class UserPublishesDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String role;
    private List<PublishedArrangementDTO> publishedArrangements;

    public UserPublishesDTO() {
    }

    public UserPublishesDTO(Long id, String firstName, String lastName,
                            String role, List<PublishedArrangementDTO> publishedArrangements) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.publishedArrangements = publishedArrangements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<PublishedArrangementDTO> getPublishedArrangements() {
        return publishedArrangements;
    }

    public void setPublishedArrangements(List<PublishedArrangementDTO> publishedArrangements) {
        this.publishedArrangements = publishedArrangements;
    }
}
package rs.ac.uns.acs.nais.workflow_service.dto;

public class UserWorkflowStatsDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private Long brojWorkflowa;
    private Long brojAranzmana;

    public UserWorkflowStatsDTO() {
    }

    public UserWorkflowStatsDTO(Long userId, String firstName, String lastName,
                                Long brojWorkflowa, Long brojAranzmana) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.brojWorkflowa = brojWorkflowa;
        this.brojAranzmana = brojAranzmana;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getBrojWorkflowa() {
        return brojWorkflowa;
    }

    public void setBrojWorkflowa(Long brojWorkflowa) {
        this.brojWorkflowa = brojWorkflowa;
    }

    public Long getBrojAranzmana() {
        return brojAranzmana;
    }

    public void setBrojAranzmana(Long brojAranzmana) {
        this.brojAranzmana = brojAranzmana;
    }
}

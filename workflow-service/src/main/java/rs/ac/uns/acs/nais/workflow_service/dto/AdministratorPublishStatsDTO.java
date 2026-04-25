package rs.ac.uns.acs.nais.workflow_service.dto;

public class AdministratorPublishStatsDTO {

    private Long administratorId;
    private String firstName;
    private String lastName;
    private Long ukupanBrojAranzmana;
    private Long brojObjavljenih;
    private Double procenatObjavljenih;

    public AdministratorPublishStatsDTO() {}

    public Long getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(Long administratorId) {
        this.administratorId = administratorId;
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

    public Long getUkupanBrojAranzmana() {
        return ukupanBrojAranzmana;
    }

    public void setUkupanBrojAranzmana(Long ukupanBrojAranzmana) {
        this.ukupanBrojAranzmana = ukupanBrojAranzmana;
    }

    public Long getBrojObjavljenih() {
        return brojObjavljenih;
    }

    public void setBrojObjavljenih(Long brojObjavljenih) {
        this.brojObjavljenih = brojObjavljenih;
    }

    public Double getProcenatObjavljenih() {
        return procenatObjavljenih;
    }

    public void setProcenatObjavljenih(Double procenatObjavljenih) {
        this.procenatObjavljenih = procenatObjavljenih;
    }
}

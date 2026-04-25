package rs.ac.uns.acs.nais.workflow_service.dto;

public class ArrangementRatingDTO {
    private Long arrangementId;
    private String arrangementName;
    private String destination;
    private Long brojPonuda;
    private Double prosecnaOcena;

    public ArrangementRatingDTO() {}

    public ArrangementRatingDTO(Long arrangementId, String arrangementName, String destination,
                                Long brojPonuda, Double prosecnaOcena) {
        this.arrangementId = arrangementId;
        this.arrangementName = arrangementName;
        this.destination = destination;
        this.brojPonuda = brojPonuda;
        this.prosecnaOcena = prosecnaOcena;
    }

    public Long getArrangementId() {
        return arrangementId;
    }

    public String getArrangementName() {
        return arrangementName;
    }

    public String getDestination() {
        return destination;
    }

    public Long getBrojPonuda() {
        return brojPonuda;
    }

    public Double getProsecnaOcena() {
        return prosecnaOcena;
    }
}

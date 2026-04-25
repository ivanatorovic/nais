package rs.ac.uns.acs.nais.workflow_service.dto;

public class ArrangementPriceDTO {
    private Long arrangementId;
    private String arrangementName;
    private String destination;
    private Long brojPonuda;
    private Double ukupnaCenaZaOdrasle;
    private Double ukupnaCenaZaDecu;

    public ArrangementPriceDTO(Long arrangementId, String arrangementName, String destination,
                               Long brojPonuda, Double ukupnaCenaZaOdrasle, Double ukupnaCenaZaDecu) {
        this.arrangementId = arrangementId;
        this.arrangementName = arrangementName;
        this.destination = destination;
        this.brojPonuda = brojPonuda;
        this.ukupnaCenaZaOdrasle = ukupnaCenaZaOdrasle;
        this.ukupnaCenaZaDecu = ukupnaCenaZaDecu;
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

    public Double getUkupnaCenaZaOdrasle() {
        return ukupnaCenaZaOdrasle;
    }

    public Double getUkupnaCenaZaDecu() {
        return ukupnaCenaZaDecu;
    }
}

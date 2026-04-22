package rs.ac.uns.acs.nais.workflow_service.dto;

public class OfferDTO {

    private Long id;
    private String type;
    private String value;
    private String name;
    private Double rating;
    private Double adultsPrice;
    private Double kidsPrice;

    public OfferDTO() {
    }

    public OfferDTO(Long id, String type, String value, String name,
                    Double rating, Double adultsPrice, Double kidsPrice) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.name = name;
        this.rating = rating;
        this.adultsPrice = adultsPrice;
        this.kidsPrice = kidsPrice;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Double getRating() {
        return rating;
    }

    public Double getAdultsPrice() {
        return adultsPrice;
    }

    public Double getKidsPrice() {
        return kidsPrice;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setAdultsPrice(Double adultsPrice) {
        this.adultsPrice = adultsPrice;
    }

    public void setKidsPrice(Double kidsPrice) {
        this.kidsPrice = kidsPrice;
    }
}
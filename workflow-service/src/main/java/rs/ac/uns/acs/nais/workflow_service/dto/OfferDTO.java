package rs.ac.uns.acs.nais.workflow_service.dto;

public class OfferDTO {

    private Long id;
    private String type;
    private String value;
    private String name;
    private Double rating;

    public OfferDTO() {
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
}
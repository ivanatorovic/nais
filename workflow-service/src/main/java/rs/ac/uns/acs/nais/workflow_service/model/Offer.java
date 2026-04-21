package rs.ac.uns.acs.nais.workflow_service.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Offer")
public class Offer {

    @Id
    private Long id;

    private OfferType type;
    private String value;
    private String name;
    private Double rating;

    public Offer() {
    }

    public Offer(Long id, OfferType type, String value, String name, Double rating) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.name = name;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public OfferType getType() {
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

    public void setType(OfferType type) {
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
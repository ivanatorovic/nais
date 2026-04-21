package rs.ac.uns.acs.nais.workflow_service.controller;

import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.workflow_service.dto.OfferDTO;
import rs.ac.uns.acs.nais.workflow_service.service.IOfferService;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final IOfferService offerService;

    public OfferController(IOfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public List<OfferDTO> getAllOffers() {
        return offerService.getAllOffers();
    }

    @GetMapping("/{id}")
    public OfferDTO getOfferById(@PathVariable Long id) {
        return offerService.getOfferById(id);
    }

    @PostMapping
    public OfferDTO createOffer(@RequestBody OfferDTO offerDTO) {
        return offerService.createOffer(offerDTO);
    }

    @PutMapping("/{id}")
    public OfferDTO updateOffer(@PathVariable Long id, @RequestBody OfferDTO offerDTO) {
        return offerService.updateOffer(id, offerDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
    }
}
package rs.ac.uns.acs.nais.workflow_service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.acs.nais.workflow_service.dto.OfferDTO;
import rs.ac.uns.acs.nais.workflow_service.model.Offer;
import rs.ac.uns.acs.nais.workflow_service.model.OfferType;
import rs.ac.uns.acs.nais.workflow_service.repository.OfferRepository;
import rs.ac.uns.acs.nais.workflow_service.service.IOfferService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService implements IOfferService {

    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public List<OfferDTO> getAllOffers() {
        return offerRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OfferDTO getOfferById(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Offer not found with id: " + id
                ));

        return mapToDTO(offer);
    }

    @Override
    public OfferDTO createOffer(OfferDTO offerDTO) {
        if (offerRepository.existsById(offerDTO.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Offer with id " + offerDTO.getId() + " already exists."
            );
        }

        Offer offer = mapToEntity(offerDTO);
        return mapToDTO(offerRepository.save(offer));
    }

    @Override
    public OfferDTO updateOffer(Long id, OfferDTO offerDTO) {
        Offer existing = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Offer not found with id: " + id
                ));

        existing.setType(OfferType.valueOf(offerDTO.getType().toUpperCase()));
        existing.setValue(offerDTO.getValue());
        existing.setName(offerDTO.getName());
        existing.setRating(offerDTO.getRating());

        return mapToDTO(offerRepository.save(existing));
    }

    @Override
    public void deleteOffer(Long id) {
        if (!offerRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Offer not found with id: " + id
            );
        }

        offerRepository.deleteById(id);
    }

    private OfferDTO mapToDTO(Offer offer) {
        OfferDTO dto = new OfferDTO();
        dto.setId(offer.getId());
        dto.setType(offer.getType().name());
        dto.setValue(offer.getValue());
        dto.setName(offer.getName());
        dto.setRating(offer.getRating());
        return dto;
    }

    private Offer mapToEntity(OfferDTO dto) {
        Offer offer = new Offer();
        offer.setId(dto.getId());
        offer.setType(OfferType.valueOf(dto.getType().toUpperCase()));
        offer.setValue(dto.getValue());
        offer.setName(dto.getName());
        offer.setRating(dto.getRating());
        return offer;
    }
}
package rs.ac.uns.acs.nais.workflow_service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.acs.nais.workflow_service.dto.*;
import rs.ac.uns.acs.nais.workflow_service.model.Arrangement;
import rs.ac.uns.acs.nais.workflow_service.model.Offer;
import rs.ac.uns.acs.nais.workflow_service.repository.ArrangementRepository;
import rs.ac.uns.acs.nais.workflow_service.service.IArrangementService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArrangementService implements IArrangementService {

    private final ArrangementRepository arrangementRepository;

    public ArrangementService(ArrangementRepository arrangementRepository) {
        this.arrangementRepository = arrangementRepository;
    }

    @Override
    public List<ArrangementDTO> getAllArrangements() {
        return arrangementRepository.findAll()
                .stream()
                .map(this::mapToBasicDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ArrangementDTO getArrangementById(Long id) {
        Arrangement arrangement = arrangementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Arrangement not found with id: " + id
                ));

        return mapToBasicDTO(arrangement);
    }

    @Override
    public ArrangementDTO createArrangement(ArrangementDTO arrangementDTO) {
        Long maxId = arrangementRepository.findMaxId();
        Long newId = maxId + 1;

        Arrangement arrangement = mapToEntity(arrangementDTO);
        arrangement.setId(newId);

        Arrangement savedArrangement = arrangementRepository.save(arrangement);
        return mapToBasicDTO(savedArrangement);
    }


    @Override
    public ArrangementDTO updateArrangement(Long id, ArrangementDTO arrangementDTO) {
        Arrangement existingArrangement = arrangementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Arrangement not found with id: " + id
                ));

        if (arrangementDTO.getId() != null && !arrangementDTO.getId().equals(id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Changing arrangement ID is not allowed."
            );
        }

        if (arrangementDTO.getName() != null) {
            existingArrangement.setName(arrangementDTO.getName());
        }

        if (arrangementDTO.getDestination() != null) {
            existingArrangement.setDestination(arrangementDTO.getDestination());
        }

        if (arrangementDTO.getDescription() != null) {
            existingArrangement.setDescription(arrangementDTO.getDescription());
        }

        if (arrangementDTO.getStartDate() != null) {
            existingArrangement.setStartDate(arrangementDTO.getStartDate());
        }

        if (arrangementDTO.getEndDate() != null) {
            existingArrangement.setEndDate(arrangementDTO.getEndDate());
        }

        Arrangement updatedArrangement = arrangementRepository.save(existingArrangement);
        return mapToBasicDTO(updatedArrangement);
    }


    @Override
    public void deleteArrangement(Long id) {
        if (!arrangementRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + id
            );
        }

        arrangementRepository.deleteArrangementByCustomId(id);
    }


    private ArrangementDTO mapToBasicDTO(Arrangement arrangement) {
        ArrangementDTO dto = new ArrangementDTO();
        dto.setId(arrangement.getId());
        dto.setName(arrangement.getName());
        dto.setDestination(arrangement.getDestination());
        dto.setDescription(arrangement.getDescription());
        dto.setStartDate(arrangement.getStartDate());
        dto.setEndDate(arrangement.getEndDate());
        return dto;
    }



    private Arrangement mapToEntity(ArrangementDTO dto) {
        Arrangement arrangement = new Arrangement();
        arrangement.setId(dto.getId());
        arrangement.setName(dto.getName());
        arrangement.setDestination(dto.getDestination());
        arrangement.setDescription(dto.getDescription());
        arrangement.setStartDate(dto.getStartDate());
        arrangement.setEndDate(dto.getEndDate());
        return arrangement;
    }

    @Override
    public ArrangementDTO createBasedOnRelationship(Long arrangementId, Long workflowId) {
        Arrangement arrangement = arrangementRepository.createBasedOnRelationship(arrangementId, workflowId);

        if (arrangement == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement or Workflow not found."
            );
        }

        if (arrangementRepository.existsBasedOnRelationship(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Arrangement already has a workflow."
            );
        }

        return mapToBasicDTO(arrangement);
    }

    @Override
    public void deleteBasedOnRelationship(Long arrangementId) {
        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        if (!arrangementRepository.existsBasedOnRelationship(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "BASED_ON relationship not found for arrangement with id: " + arrangementId
            );
        }

        arrangementRepository.deleteBasedOnRelationship(arrangementId);
    }

    @Override
    public WorkflowDTO getWorkflowForArrangement(Long arrangementId) {
        Arrangement arrangement = arrangementRepository.findArrangementWithWorkflow(arrangementId);

        if (arrangement == null || arrangement.getWorkflow() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement or BASED_ON relationship not found."
            );
        }

        WorkflowDTO dto = new WorkflowDTO();
        dto.setId(arrangement.getWorkflow().getId());
        dto.setName(arrangement.getWorkflow().getName());
        return dto;
    }

    @Override
    public ArrangementDTO addOfferToArrangement(Long arrangementId, Long offerId) {
        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        if (arrangementRepository.existsOfferOfSameTypeForArrangement(arrangementId, offerId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Arrangement already has an offer of this type."
            );
        }

        Arrangement arrangement = arrangementRepository.addOfferToArrangement(arrangementId, offerId);

        if (arrangement == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement or Offer not found."
            );
        }

        return mapToBasicDTO(arrangement);
    }

    @Override
    public void deleteOfferFromArrangement(Long arrangementId, Long offerId) {
        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        if (!arrangementRepository.existsHasOfferRelationshipForArrangement(arrangementId, offerId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "HAS_OFFER relationship not found for arrangementId: "
                            + arrangementId + " and offerId: " + offerId
            );
        }

        arrangementRepository.deleteOfferFromArrangement(arrangementId, offerId);
    }

    @Override
    public List<OfferDTO> getOffersForArrangement(Long arrangementId) {
        Arrangement arrangement = arrangementRepository.findArrangementWithOffers(arrangementId);

        if (arrangement == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement or HAS_OFFER relationship not found."
            );
        }

        if (arrangement.getOffers() == null) {
            return List.of();
        }

        return arrangement.getOffers().stream()
                .map(this::mapOfferToDTO)
                .collect(Collectors.toList());
    }


    private OfferDTO mapOfferToDTO(Offer offer) {
        OfferDTO dto = new OfferDTO();
        dto.setId(offer.getId());
        dto.setName(offer.getName());
        dto.setType(offer.getType().name());
        dto.setValue(offer.getValue());
        dto.setRating(offer.getRating());
        dto.setAdultsPrice(offer.getAdultsPrice());
        dto.setKidsPrice(offer.getKidsPrice());
        return dto;
    }

    @Override
    public List<ArrangementPriceDTO> getArrangementPriceAnalysis() {
        return arrangementRepository.getArrangementPriceAnalysis();
    }

    @Override
    public List<ArrangementRatingDTO> getArrangementRatings() {
        return arrangementRepository.getArrangementRatings();
    }


}
package rs.ac.uns.acs.nais.workflow_service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.acs.nais.workflow_service.dto.ArrangementDTO;
import rs.ac.uns.acs.nais.workflow_service.model.Arrangement;
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
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ArrangementDTO getArrangementById(Long id) {
        Arrangement arrangement = arrangementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Arrangement not found with id: " + id
                ));

        return mapToDTO(arrangement);
    }

    @Override
    public ArrangementDTO createArrangement(ArrangementDTO arrangementDTO) {
        if (arrangementRepository.existsById(arrangementDTO.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Arrangement with id " + arrangementDTO.getId() + " already exists."
            );
        }

        Arrangement arrangement = mapToEntity(arrangementDTO);
        Arrangement savedArrangement = arrangementRepository.save(arrangement);

        return mapToDTO(savedArrangement);
    }

    @Override
    public ArrangementDTO updateArrangement(Long id, ArrangementDTO arrangementDTO) {
        Arrangement existingArrangement = arrangementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Arrangement not found with id: " + id
                ));

        existingArrangement.setName(arrangementDTO.getName());
        existingArrangement.setDestination(arrangementDTO.getDestination());
        existingArrangement.setDescription(arrangementDTO.getDescription());
        existingArrangement.setStartDate(arrangementDTO.getStartDate());
        existingArrangement.setEndDate(arrangementDTO.getEndDate());


        Arrangement updatedArrangement = arrangementRepository.save(existingArrangement);
        return mapToDTO(updatedArrangement);
    }

    @Override
    public void deleteArrangement(Long id) {
        if (!arrangementRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + id
            );
        }

        arrangementRepository.deleteById(id);
    }


    private ArrangementDTO mapToDTO(Arrangement arrangement) {
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

        return mapToDTO(arrangement);
    }

    @Override
    public void deleteBasedOnRelationship(Long arrangementId) {
        arrangementRepository.deleteBasedOnRelationship(arrangementId);
    }

    @Override
    public ArrangementDTO findArrangementWithWorkflow(Long arrangementId) {
        Arrangement arrangement = arrangementRepository.findArrangementWithWorkflow(arrangementId);

        if (arrangement == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement or relationship BASED_ON not found."
            );
        }

        return mapToDTO(arrangement);
    }

    @Override
    public ArrangementDTO addOfferToArrangement(Long arrangementId, Long offerId) {
        Arrangement arrangement = arrangementRepository.addOfferToArrangement(arrangementId, offerId);

        if (arrangement == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement or Offer not found."
            );
        }

        return mapToDTO(arrangement);
    }

    @Override
    public void deleteOfferFromArrangement(Long arrangementId, Long offerId) {
        arrangementRepository.deleteOfferFromArrangement(arrangementId, offerId);
    }

    @Override
    public ArrangementDTO findArrangementWithOffers(Long arrangementId) {
        Arrangement arrangement = arrangementRepository.findArrangementWithOffers(arrangementId);

        if (arrangement == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement or HAS_OFFER relationship not found."
            );
        }

        return mapToDTO(arrangement);
    }



}
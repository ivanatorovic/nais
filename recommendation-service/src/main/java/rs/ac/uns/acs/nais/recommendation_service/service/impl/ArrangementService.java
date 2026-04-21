package rs.ac.uns.acs.nais.recommendation_service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.acs.nais.recommendation_service.dto.ArrangementRecommendationDto;
import rs.ac.uns.acs.nais.recommendation_service.dto.UpdateArrangementRequest;
import rs.ac.uns.acs.nais.recommendation_service.model.Arrangement;
import rs.ac.uns.acs.nais.recommendation_service.repository.ArrangementRepository;
import rs.ac.uns.acs.nais.recommendation_service.service.IArrangementService;

import java.util.List;
import java.util.Optional;

@Service
public class ArrangementService implements IArrangementService {

    private final ArrangementRepository arrangementRepository;

    public ArrangementService(ArrangementRepository arrangementRepository) {
        this.arrangementRepository = arrangementRepository;
    }

    // ===================== CREATE =====================
    @Override
    public Arrangement save(Arrangement arrangement) {
        if (arrangementRepository.existsById(arrangement.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Arrangement with id " + arrangement.getId() + " already exists."
            );
        }
        return arrangementRepository.save(arrangement);
    }

    // ===================== READ =====================
    @Override
    public List<Arrangement> findAll() {
        return arrangementRepository.findAll();
    }

    @Override
    public Optional<Arrangement> findById(Long id) {
        return arrangementRepository.findById(id);
    }

    // ===================== UPDATE =====================
    @Override
    public Arrangement update(Long id, UpdateArrangementRequest arrangement) {
        Arrangement existing = arrangementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Arrangement not found with id: " + id
                ));

        existing.setName(arrangement.getName());
        existing.setDescription(arrangement.getDescription());
        existing.setPrice(arrangement.getPrice());
        existing.setDurationDays(arrangement.getDurationDays());

        return arrangementRepository.save(existing);
    }

    // ===================== DELETE =====================
    @Override
    public void delete(Long id) {
        if (!arrangementRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + id
            );
        }

        arrangementRepository.deleteArrangementById(id);
    }

    // ===================== TAG =====================
    @Override
    public Arrangement addTagToArrangement(Long arrangementId, Long tagId) {
        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        return arrangementRepository.addTagToArrangement(arrangementId, tagId);
    }

    @Override
    public void removeTagFromArrangement(Long arrangementId, Long tagId) {
        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        arrangementRepository.removeTagFromArrangement(arrangementId, tagId);
    }

    @Override
    public Arrangement getArrangementWithTags(Long arrangementId) {
        Arrangement result = arrangementRepository.getArrangementWithTags(arrangementId);

        if (result == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        return result;
    }

    // ===================== DESTINATION =====================
    @Override
    public Arrangement setArrangementLocation(Long arrangementId, Long destinationId) {
        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        return arrangementRepository.setArrangementLocation(arrangementId, destinationId);
    }

    @Override
    public void removeArrangementLocation(Long arrangementId) {
        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        arrangementRepository.removeArrangementLocation(arrangementId);
    }

    @Override
    public Arrangement getArrangementWithDestination(Long arrangementId) {
        Arrangement result = arrangementRepository.getArrangementWithDestination(arrangementId);

        if (result == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        return result;
    }

    // ===================== RECOMMENDATION =====================
    @Override
    public List<ArrangementRecommendationDto> findSimilarArrangements(Long arrangementId) {
        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        return arrangementRepository.findSimilarArrangements(arrangementId);
    }
}
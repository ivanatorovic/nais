package rs.ac.uns.acs.nais.recommendation_service.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.recommendation_service.dto.ArrangementRecommendationDto;
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

    @Override
    public Arrangement save(Arrangement arrangement) {
        return arrangementRepository.save(arrangement);
    }

    @Override
    public List<Arrangement> findAll() {
        return arrangementRepository.findAll();
    }

    @Override
    public Optional<Arrangement> findById(Long id) {
        return arrangementRepository.findById(id);
    }

    @Override
    public Arrangement update(Long id, Arrangement arrangement) {
        arrangement.setId(id);
        return arrangementRepository.save(arrangement);
    }

    @Override
    public void delete(Long id) {
        arrangementRepository.deleteArrangementById(id);
    }

    @Override
    public Arrangement addTagToArrangement(Long arrangementId, Long tagId) {
        return arrangementRepository.addTagToArrangement(arrangementId, tagId);
    }

    @Override
    public void removeTagFromArrangement(Long arrangementId, Long tagId) {
        arrangementRepository.removeTagFromArrangement(arrangementId, tagId);
    }

    @Override
    public Arrangement getArrangementWithTags(Long arrangementId) {
        return arrangementRepository.getArrangementWithTags(arrangementId);
    }

    @Override
    public Arrangement setArrangementLocation(Long arrangementId, Long destinationId) {
        return arrangementRepository.setArrangementLocation(arrangementId, destinationId);
    }

    @Override
    public void removeArrangementLocation(Long arrangementId) {
        arrangementRepository.removeArrangementLocation(arrangementId);
    }

    @Override
    public Arrangement getArrangementWithDestination(Long arrangementId) {
        return arrangementRepository.getArrangementWithDestination(arrangementId);
    }


    @Override
    public List<ArrangementRecommendationDto> findSimilarArrangements(Long arrangementId) {
        return arrangementRepository.findSimilarArrangements(arrangementId);
    }
}
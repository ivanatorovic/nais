package rs.ac.uns.acs.nais.recommendation_service.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.recommendation_service.model.Destination;
import rs.ac.uns.acs.nais.recommendation_service.repository.DestinationRepository;
import rs.ac.uns.acs.nais.recommendation_service.service.IDestinationService;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService implements IDestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @Override
    public Destination save(Destination destination) {
        return destinationRepository.save(destination);
    }

    @Override
    public List<Destination> findAll() {
        return destinationRepository.findAll();
    }

    @Override
    public Optional<Destination> findById(Long id) {
        return destinationRepository.findById(id);
    }

    @Override
    public Destination update(Long id, Destination destination) {
        destination.setId(id);
        return destinationRepository.save(destination);
    }

    @Override
    public void delete(Long id) {
        destinationRepository.deleteById(id);
    }
}
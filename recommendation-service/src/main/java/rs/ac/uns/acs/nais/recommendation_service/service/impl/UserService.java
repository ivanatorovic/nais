package rs.ac.uns.acs.nais.recommendation_service.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.recommendation_service.dto.ArrangementRecommendationDto;
import rs.ac.uns.acs.nais.recommendation_service.model.User;
import rs.ac.uns.acs.nais.recommendation_service.repository.UserRepository;
import rs.ac.uns.acs.nais.recommendation_service.service.IUserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User update(Long id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User addOrUpdateViewed(Long userId, Long arrangementId, String viewedAt) {
        return userRepository.addOrUpdateViewed(userId, arrangementId, viewedAt);
    }

    @Override
    public void deleteViewedRelationship(Long userId, Long arrangementId) {
        userRepository.deleteViewedRelationship(userId, arrangementId);
    }

    @Override
    public User findUserWithViewedRelationships(Long userId) {
        return userRepository.findUserWithViewedRelationships(userId);
    }

    @Override
    public User createBookedRelationship(Long userId, Long arrangementId, String bookingDate, Integer persons, Double totalPrice) {
        return userRepository.createBookedRelationship(userId, arrangementId, bookingDate, persons, totalPrice);
    }

    @Override
    public User updateBookedRelationship(Long userId, Long arrangementId, String bookingDate, Integer persons, Double totalPrice) {
        return userRepository.updateBookedRelationship(userId, arrangementId, bookingDate, persons, totalPrice);
    }

    @Override
    public void deleteBookedRelationship(Long userId, Long arrangementId) {
        userRepository.deleteBookedRelationship(userId, arrangementId);
    }

    @Override
    public User findUserWithBookedRelationships(Long userId) {
        return userRepository.findUserWithBookedRelationships(userId);
    }

    @Override
    public List<ArrangementRecommendationDto> recommendBasedOnViewed(Long userId) {
        return userRepository.recommendBasedOnViewed(userId);
    }

    @Override
    public List<ArrangementRecommendationDto> recommendBasedOnBooked(Long userId) {
        return userRepository.recommendBasedOnBooked(userId);
    }

}
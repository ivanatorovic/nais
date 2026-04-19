package rs.ac.uns.acs.nais.recommendation_service.service.impl;

import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.recommendation_service.model.Tag;
import rs.ac.uns.acs.nais.recommendation_service.repository.TagRepository;
import rs.ac.uns.acs.nais.recommendation_service.service.ITagService;

import java.util.List;
import java.util.Optional;

@Service
public class TagService implements ITagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }

    @Override
    public Tag update(Long id, Tag tag) {
        tag.setId(id);
        return tagRepository.save(tag);
    }

    @Override
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}
package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import at.ac.tuwien.sepm.groupphase.backend.service.validator.ArtistValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class ArtistServiceImpl implements ArtistService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public Page<Artist> getAllArtists(Pageable pageable) {
        LOGGER.debug("get all artists with page {}", pageable.getPageNumber());
        return artistRepository.findAll(pageable);
    }

    @Override
    public Page<Artist> searchArtists(Artist artist, Pageable pageable) {
        LOGGER.debug("search for artist {} with page {}", artist, pageable.getPageNumber());
        ExampleMatcher matcher = ExampleMatcher
            .matchingAll()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Artist> probe = Example.of(artist, matcher);

        return artistRepository.findAll(probe, pageable);
    }

    @Override
    public void createArtist(Artist artist) {
        new ArtistValidator().build(artist).validate();
        artistRepository.save(artist);
    }
}

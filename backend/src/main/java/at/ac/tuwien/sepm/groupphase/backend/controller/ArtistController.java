package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.api.ArtistApi;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.ArtistMapper;
import at.ac.tuwien.sepm.groupphase.backend.dto.ArtistDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.SearchArtistDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@RestController
public class ArtistController implements ArtistApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ArtistService artistService;
    private final ArtistMapper artistMapper;

    private static final int PAGE_SIZE = 25;

    public ArtistController(ArtistService artistService, ArtistMapper artistMapper) {
        this.artistService = artistService;
        this.artistMapper = artistMapper;
    }

    @Override
    public ResponseEntity<List<ArtistDTO>> getArtistList(@Valid Optional<Integer> page) {
        LOGGER.info("get all artists");
        Page<Artist> artists = artistService.getAllArtists(PageRequest.of(page.orElse(0), PAGE_SIZE));
        return ResponseEntity.ok()
            .header("X-Total-Count", String.valueOf(artists.getTotalElements()))
            .body(artistMapper.toDto(artists.getContent()));
    }

    @Override
    public ResponseEntity<List<ArtistDTO>> searchArtists(@Valid SearchArtistDTO searchArtistDTO, @Valid Optional<Integer> page) {
        LOGGER.info("search for artist");
        Page<Artist> artists = artistService.searchArtists(artistMapper.fromDto(searchArtistDTO), PageRequest.of(page.orElse(0), PAGE_SIZE));
        return ResponseEntity.ok()
            .header("X-Total-Count", String.valueOf(artists.getTotalElements()))
            .body(artistMapper.toDto(artists.getContent()));
    }

    @Override
    @Secured(AuthorizationRole.ADMIN_ROLE)
    public ResponseEntity<Void> createArtist(@Valid ArtistDTO artistDTO) {
        LOGGER.info("create artist");
        artistService.createArtist(artistMapper.fromDto(artistDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

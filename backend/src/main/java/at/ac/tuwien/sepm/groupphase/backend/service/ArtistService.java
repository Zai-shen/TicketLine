package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ArtistService {

    Page<Artist> getAllArtists(Pageable pageable);

    Page<Artist> searchArtists(Artist artist, Pageable pageable);
}
